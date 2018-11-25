package com.gnut.bidscout.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gnut.bidscout.builder.BidResponseBuilder;
import com.gnut.bidscout.db.AuctionDao;
import com.gnut.bidscout.model.*;
import com.gnut.bidscout.rtb.BidRequestValidator;
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

@Component
public class BidRequestService {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final int BID_FREQUENCY_VALUE = 10;
    private final String PARSE_ERROR = "Unable to parse bid request";
    private final String BID_REQUEST_ID_MISSING = "Bid request ID missing";
    private final String BID_REQUEST_ID_NOT_UNIQUE = "Bid request ID already exists in database. " +
            "Delete existing auction data or change bid request ID";

    private final CampaignService campaignService;
    private final CreativeService creativeService;
    private final BidResponseService bidResponseService;
    private final BidRequestValidator bidRequestValidator;
    private final AuctionDao auctionDao;

    static {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Autowired
    public BidRequestService(
            CampaignService campaignService,
            CreativeService creativeService,
            BidResponseService bidResponseService,
            BidRequestValidator bidRequestValidator,
            AuctionDao auctionDao
    ) {
        this.campaignService = campaignService;
        this.creativeService = creativeService;
        this.bidResponseService = bidResponseService;
        this.bidRequestValidator = bidRequestValidator;
        this.auctionDao = auctionDao;
    }

    public BidResponse handleRequest(String bidder, String publisher, HttpServletRequest request, HttpServletResponse response) {
        if (Strings.isNullOrEmpty(publisher)) {
            publisher = "";
        }

        final BidRequest bidRequest;
        AuctionRecord record = new AuctionRecord();
        record.setTargetingFailures(new HashMap<>());
        record.setBidRequestErrors(new ArrayList<>());
        record.setRequestTimestamp(System.currentTimeMillis());
        record.setIp(request.getRemoteAddr());
        record.setUserAgent(request.getHeader("User-Agent"));
        record.setCookies(request.getHeader("Cookie"));
        record.setxForwardedFor(request.getHeader("X-Forwarded-For"));
        record.setHost(request.getHeader("Host"));
        record.setPublisher(publisher);
        record.setOwner(bidder);

        try {
            bidRequest = objectMapper.readValue(stringifyPostData(request), BidRequest.class);
        } catch (IOException ex) {
            record.getBidRequestErrors().add(BidRequestError.PARSE_ERROR.value());
            auctionDao.save(record);
            return bidResponseService.buildNBRBidResponse(
                    null, BidResponseBuilder.NBR.INVALID_REQUEST, PARSE_ERROR
            );
        }

        if (Strings.isNullOrEmpty(bidRequest.getId())) {
            return bidResponseService.buildNBRBidResponse(
                    bidRequest, BidResponseBuilder.NBR.INVALID_REQUEST, BID_REQUEST_ID_MISSING
            );
        } else if (auctionDao.findFirstByBidRequestIdAndOwner(bidRequest.getId(), record.getOwner()) != null) {
            return bidResponseService.buildNBRBidResponse(
                    bidRequest, BidResponseBuilder.NBR.INVALID_REQUEST, BID_REQUEST_ID_NOT_UNIQUE
            );
        }

        record.setBidRequest(bidRequest);
        record.setBidRequestId(bidRequest.getId());
        BidResponse bidResponse = null;

        if (bidRequestValidator.validateBidRequest(bidRequest, record)) {
            final Optional<EligibleCampaignData> data = campaignService.targetCampaign(bidder, publisher, bidRequest, request, record);

            if (data.isPresent() && !data.get().getCreatives().isEmpty()) {
                final Campaign campaign = data.get().getCampaign();
                final Creative creative = data.get().getCreatives().iterator().next();
                final double bp = Math.random() * (creative.getMaxBid() - creative.getMinBid()) + creative.getMinBid();
                final BigDecimal price = new BigDecimal(bp).setScale(2, RoundingMode.HALF_UP);

                record.setCampaign(campaign.getId());
                record.setCreative(creative.getId());

                if (campaign.getStatistics() == null) {
                    Statistics campaignStats = new Statistics();
                    campaign.setStatistics(campaignStats);
                }

                if (creative.getStatistics() == null) {
                    Statistics creativeStats = new Statistics();
                    creative.setStatistics(creativeStats);
                }

                campaign.getStatistics().setRequests(campaign.getStatistics().getRequests() + 1);
                creative.getStatistics().setRequests(creative.getStatistics().getRequests() + 1);

                float bidPrice = Float.valueOf(price.toString());
                boolean validBid = bidPrice >= data.get().getData().getBidfloor();
                boolean creativeRequiresDeal = !creative.getRequirements().getDealIds().isEmpty();
                boolean campaignRequiresDeal = !campaign.getRequirements().getDealIds().isEmpty();

                String selectedDeal = "";
                if (campaignRequiresDeal || creativeRequiresDeal) {
                    List<Deal> requestDeals = bidRequest.getImp().get(0).getPmp() != null ?
                            bidRequest.getImp().get(0).getPmp().getDeals() : Collections.emptyList();
                    selectedDeal = getEligibleDeal(
                            campaign, campaignRequiresDeal, creative, creativeRequiresDeal, requestDeals, bidPrice
                    );
                }

                if (validBid && creativeWillBid(creative)) {
                    campaign.getStatistics().setBids(campaign.getStatistics().getBids() + 1);
                    campaign.getStatistics().setBidPriceTotal(campaign.getStatistics().getBidPriceTotal() + Float.valueOf(price.toString()) / 1000);
                    creative.getStatistics().setBids(creative.getStatistics().getBids() + 1);
                    creative.getStatistics().setBidPriceTotal(creative.getStatistics().getBidPriceTotal() + Float.valueOf(price.toString()) / 1000);

                    bidResponse = bidResponseService.buildBidResponse(
                            price, bidRequest, bidRequest.getImp().get(0), data.get().getCampaign(), creative, selectedDeal
                    );

                    if (bidResponse.getNbr() != null) {
                        campaign.getStatistics().setNbr(campaign.getStatistics().getNbr() + 1);
                        creative.getStatistics().setNbr(creative.getStatistics().getNbr() + 1);
                    }

                    record.setResponseTimestamp(System.currentTimeMillis());
                    record.setBidResponse(bidResponse);
                    if (bidResponse.getSeatbid() != null) {
                        record.setMarkup(bidResponse.getSeatbid().get(0).getBid().get(0).getAdm());
                    }
                }
                saveCampaignAndCreative(creative, campaign);
            }
        }

        auctionDao.save(record);

        if (bidResponse != null) {
            return bidResponse;
        } else {
            return generateNoContentResponse(response);
        }
    }

    private void saveCampaignAndCreative(Creative creative, Campaign campaign) {
        campaignService.saveCampaign(campaign);
        creativeService.saveCreative(creative);
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
