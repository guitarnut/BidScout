package com.gnut.bidscout.service.auction;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gnut.bidscout.builder.BidResponseBuilder;
import com.gnut.bidscout.db.AuctionDao;
import com.gnut.bidscout.model.*;
import com.gnut.bidscout.rtb.BidRequestValidator;
import com.gnut.bidscout.service.inventory.CampaignService;
import com.gnut.bidscout.service.inventory.CreativeService;
import com.gnut.bidscout.service.inventory.DisplayAdService;
import com.gnut.bidscout.service.inventory.VideoAdService;
import com.gnut.bidscout.service.user.UserAccountStatisticsService;
import com.gnut.bidscout.values.BidRequestError;
import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.io.CharStreams;
import com.iab.openrtb.request.BidRequest;
import com.iab.openrtb.request.Deal;
import com.iab.openrtb.response.BidResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class BidRequestService {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final int BID_FREQUENCY_VALUE = 10;

    private final ExecutorService executorService = Executors.newFixedThreadPool(50);
    private final CampaignService campaignService;
    private final CreativeService creativeService;
    private final DisplayAdService displayAdService;
    private final VideoAdService videoAdService;
    private final BidResponseService bidResponseService;
    private final BidRequestValidator bidRequestValidator;
    private final AuctionDao auctionDao;
    private final UserAccountStatisticsService userStatsService;

    static {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Autowired
    public BidRequestService(
            CampaignService campaignService,
            CreativeService creativeService,
            DisplayAdService displayAdService,
            VideoAdService videoAdService,
            BidResponseService bidResponseService,
            BidRequestValidator bidRequestValidator,
            AuctionDao auctionDao,
            UserAccountStatisticsService userStatsService
    ) {
        this.campaignService = campaignService;
        this.creativeService = creativeService;
        this.displayAdService = displayAdService;
        this.videoAdService = videoAdService;
        this.bidResponseService = bidResponseService;
        this.bidRequestValidator = bidRequestValidator;
        this.auctionDao = auctionDao;
        this.userStatsService = userStatsService;
    }

    public BidResponse handleRequest(String bidder, String campaignId, HttpServletRequest request, HttpServletResponse response) {
        // daily limit met?
        if (bidder != null && !userStatsService.addBidRequest(bidder)) {
            return generateNoContentResponse(response);
        }

        BidRequest bidRequest = null;
        BidRequestError bidRequestError = null;

        final Campaign campaign = campaignService.getCampaign(bidder, campaignId);
        if (campaign != null) {
            campaign.getStatistics().setRequests(campaign.getStatistics().getRequests() + 1);
        }
        final AuctionRecord record = new AuctionRecord();
        record.setRequestTimestamp(System.currentTimeMillis());
        record.setIp(request.getRemoteAddr());
        record.setUserAgent(request.getHeader("User-Agent"));
        record.setCookies(request.getHeader("Cookie"));
        record.setxForwardedFor(request.getHeader("X-Forwarded-For"));
        record.setHost(request.getHeader("Host"));
        record.setCampaign(campaignId);
        record.setOwner(bidder);

        final String bidRequestString = stringifyPostData(request);

        try {
            if (!Strings.isNullOrEmpty(bidRequestString)) {
                bidRequest = objectMapper.readValue(bidRequestString, BidRequest.class);
                record.setBidRequest(bidRequest);
                record.setBidRequestId(bidRequest.getId());
            }
        } catch (IOException ex) {
            // noop
            record.setMarkup(bidRequestString);
            record.setBidRequestId("Error " + String.valueOf(System.currentTimeMillis()));
        }

        boolean saveAuctionRecord = true;

        if (campaign == null) {
            bidRequestError = BidRequestError.NO_CAMPAIGN;
        } else if (bidRequest == null) {
            bidRequestError = BidRequestError.PARSE_ERROR;
        } else if (!campaign.isEnabled()) {
            bidRequestError = BidRequestError.CAMPAIGN_NOT_ENABLED;
        } else if (Strings.isNullOrEmpty(bidRequest.getId())) {
            bidRequestError = BidRequestError.BID_REQUEST_ID_MISSING;
            record.setBidRequestId("Error " + String.valueOf(System.currentTimeMillis()));
        }
//        } else if (auctionDao.findFirstByBidRequestIdAndOwner(bidRequest.getId(), record.getOwner()) != null) {
//            bidRequestError = BidRequestError.BID_REQUEST_ID_NOT_UNIQUE;
//            saveAuctionRecord = false;
//        }

        if (bidRequestError != null) {
            executorService.submit(() -> {
                incrementCampaignNBR(campaign);
            });
            record.getBidRequestErrors().add(bidRequestError.value());
            final BidResponse bidResponse = bidResponseService.buildNBRBidResponse(
                    bidRequest, BidResponseBuilder.NBR.INVALID_REQUEST, bidRequestError.value()
            );
            record.setBidResponse(bidResponse);
            if (saveAuctionRecord) {
                executorService.submit(() -> {
                    if (userStatsService.addAuctionRecord(bidder)) {
                        auctionDao.save(record);
                    }
                });
            }
            return bidResponse;
        }

        BidResponse bidResponse = null;
        final Set<AuctionImp> auctionImps = new HashSet<>();

        if (bidRequestValidator.validateBidRequest(bidRequest, record)) {
            final Set<String> creativesUsedInBids = new HashSet<>();
            final Iterable<Creative> availableCampaignCreatives = creativeService.getCreatives(campaign.getCreatives());
            final BidRequest br = bidRequest;

            bidRequest.getImp().forEach(imp -> {
                final AuctionImp auctionImp = new AuctionImp();
                auctionImp.setBidRequest(br);
                auctionImp.setImpression(imp);

                final Optional<EligibleCampaignData> data = campaignService.targetCampaign(
                        campaign, auctionImp, request, record, availableCampaignCreatives
                );
                if (data.isPresent() && !data.get().getCreatives().isEmpty()) {
                    Creative creative = null;
                    final AtomicBoolean creativeFound = new AtomicBoolean(false);
                    for (Creative c : data.get().getCreatives()) {
                        if (!creativesUsedInBids.contains(c.getId())) {
                            creativesUsedInBids.add(c.getId());
                            creative = c;
                            creativeFound.getAndSet(true);
                            break;
                        }
                    }
                    if (creativeFound.get()) {
                        final double bp = Math.random() * (creative.getMaxBid() - creative.getMinBid()) + creative.getMinBid();
                        final BigDecimal price = new BigDecimal(bp).setScale(2, RoundingMode.HALF_UP);

                        auctionImp.setPrice(price);

                        record.setCampaign(campaign.getId());
                        record.setCreative(creative.getId());

                        creative.getStatistics().setRequests(creative.getStatistics().getRequests() + 1);

                        float bidPrice = Float.valueOf(price.toString());
                        boolean validBid = bidPrice >= data.get().getData().getBidfloor();
                        boolean creativeRequiresDeal = !creative.getRequirements().getDealIds().isEmpty();
                        boolean campaignRequiresDeal = !campaign.getRequirements().getDealIds().isEmpty();

                        String selectedDeal = "";
                        if (campaignRequiresDeal || creativeRequiresDeal) {
                            List<Deal> requestDeals = imp.getPmp() != null ?
                                    imp.getPmp().getDeals() : Collections.emptyList();
                            selectedDeal = getEligibleDeal(
                                    campaign, campaignRequiresDeal, creative, creativeRequiresDeal, requestDeals, bidPrice
                            );
                            auctionImp.setSelectedDeal(selectedDeal);
                        }

                        if (validBid && creativeWillBid(creative)) {
                            campaign.getStatistics().setBids(campaign.getStatistics().getBids() + 1);
                            campaign.getStatistics().setBidPriceTotal(campaign.getStatistics().getBidPriceTotal() + Float.valueOf(price.toString()) / 1000);
                            creative.getStatistics().setBids(creative.getStatistics().getBids() + 1);
                            creative.getStatistics().setBidPriceTotal(creative.getStatistics().getBidPriceTotal() + Float.valueOf(price.toString()) / 1000);

                            auctionImp.setCampaign(campaign);
                            auctionImp.setCreative(creative);
                            if (creative.getType() == Creative.Type.DISPLAY) {
                                Optional<DisplayAd> ad = displayAdService.getDisplayAd(creative.getId());
                                if (ad.isPresent()) {
                                    auctionImp.setDisplayAd(ad.get());
                                    auctionImps.add(auctionImp);
                                }
                            } else if (creative.getType() == Creative.Type.VAST) {
                                Optional<VideoAd> ad = videoAdService.getVideoAd(creative.getId());
                                if (ad.isPresent()) {
                                    auctionImp.setVideoAd(ad.get());
                                    auctionImps.add(auctionImp);
                                }
                            }
                        }

                        final Creative creativeToSave = creative;
                        executorService.submit(() -> {
                            creativeService.saveCreative(creativeToSave);
                        });
                    }
                }
            });

            bidResponse = bidResponseService.buildBidResponse(bidRequest, auctionImps);

            record.setResponseTimestamp(System.currentTimeMillis());
            record.setBidResponse(bidResponse);
            if (bidResponse != null) {
                bidResponse.getSeatbid().get(0).getBid().forEach(b -> {
                    final String markup = "Impression Id: " + b.getId() + "\n" + b.getAdm();
                    if (Strings.isNullOrEmpty(record.getMarkup())) {
                        record.setMarkup(markup);
                    } else {
                        record.setMarkup(record.getMarkup() + "\n\n" + markup);
                    }
                });
            }
        }

        executorService.submit(() -> {
            campaignService.saveCampaign(campaign);
            if (userStatsService.addAuctionRecord(bidder)) {
                auctionDao.save(record);
            }
        });

        if (bidResponse != null) {
            return bidResponse;
        } else {
            return generateNoContentResponse(response);
        }
    }

    private void incrementCampaignNBR(Campaign campaign) {
        campaign.getStatistics().setNbr(campaign.getStatistics().getNbr() + 1);
        campaignService.saveCampaign(campaign);
    }

    private boolean creativeWillBid(Creative creative) {
        return creative.getBidFrequency() >= 10 ||
                creative.getBidFrequency() != 0 &&
                        creative.getBidFrequency() > Math.floor(Math.random() * BID_FREQUENCY_VALUE);

    }

    private String getEligibleDeal(
            Campaign campaign,
            boolean campaignRequiresDeal,
            Creative creative,
            boolean creativeRequiresDeal,
            List<Deal> requestDeals,
            float bidPrice
    ) {
        String selectedDeal = "";
        Set<String> eligibleDealsOnCreative = new HashSet<>();
        Set<String> eligibleDealsOnCampaign = new HashSet<>();

        if (!requestDeals.isEmpty()) {
            // check for matching deals on campaign and creative
            for (Deal d : requestDeals) {
                if (creative.getRequirements().getDealIds().contains(d.getId()) && bidPrice >= d.getBidfloor()) {
                    eligibleDealsOnCreative.add(d.getId());
                }
                if (campaign.getRequirements().getDealIds().contains(d.getId()) && bidPrice >= d.getBidfloor()) {
                    eligibleDealsOnCampaign.add(d.getId());
                }
            }
        }

        if (eligibleDealsOnCreative.isEmpty() && creativeRequiresDeal) {
            return selectedDeal;
        } else if (eligibleDealsOnCampaign.isEmpty() && campaignRequiresDeal) {
            return selectedDeal;
        } else if (!eligibleDealsOnCreative.isEmpty()) {
            // all of these deals have a floor that was beat by the bid and are eligible
            selectedDeal = eligibleDealsOnCreative.iterator().next();
        } else if (!eligibleDealsOnCampaign.isEmpty()) {
            // all of these deals have a floor that was beat by the bid and are eligible
            selectedDeal = eligibleDealsOnCampaign.iterator().next();
        }

        return selectedDeal;
    }

    private BidResponse generateNoContentResponse(HttpServletResponse response) {
        response.setStatus(204);
        return null;
    }

    private String stringifyPostData(HttpServletRequest request) {
        try {
            return CharStreams.toString(new InputStreamReader(request.getInputStream(), Charsets.UTF_8));
        } catch (Exception ex) {
            // noop
            return "";
        }
    }
}
