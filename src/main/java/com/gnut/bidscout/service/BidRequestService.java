package com.gnut.bidscout.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    public String handleRequest(String bidder, String publisher, HttpServletRequest request, HttpServletResponse response) {
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

        try {
            bidRequest = objectMapper.readValue(stringifyPostData(request), BidRequest.class);
        } catch (IOException ex) {
            record.getBidRequestErrors().add(BidRequestError.PARSE_ERROR.value());
            auctionDao.save(record);
            return generateNoContentResponse(response);
        }

        record.setBidRequest(bidRequest);
        record.setBidRequestId(bidRequest.getId());

        if (bidRequestValidator.validateBidRequest(bidRequest, record)) {
            final Optional<EligibleCampaignData> data = campaignService.targetCampaign(bidder, publisher, bidRequest, request, record);

            if (data.isPresent() && !data.get().getCreatives().isEmpty()) {
                final Campaign campaign = data.get().getCampaign();
                final Creative creative = data.get().getCreatives().iterator().next();
                final double bp = Math.random() * (creative.getMaxBid() - creative.getMinBid()) + creative.getMinBid();
                final BigDecimal price = new BigDecimal(bp).setScale(2, RoundingMode.HALF_UP);

                record.setOwner(campaign.getOwner());
                record.setCampaign(campaign.getId());
                record.setCreative(creative.getId());

                Statistics campaignStats = campaign.getStatistics();
                if (campaignStats == null) {
                    campaignStats = new Statistics();
                }

                Statistics creativeStats = creative.getStatistics();
                if (creativeStats == null) {
                    creativeStats = new Statistics();
                }

                campaignStats.setRequests(campaignStats.getRequests() + 1);
                creativeStats.setRequests(creativeStats.getRequests() + 1);

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
                    if (Strings.isNullOrEmpty(selectedDeal)) {
                        validBid = false;
                    }
                }

                if (validBid) {
                    campaignStats.setBids(campaignStats.getBids() + 1);
                    campaignStats.setBidPriceTotal(campaignStats.getBidPriceTotal() + Float.valueOf(price.toString()) / 1000);
                    creativeStats.setBids(creativeStats.getBids() + 1);
                    creativeStats.setBidPriceTotal(creativeStats.getBidPriceTotal() + Float.valueOf(price.toString()) / 1000);

                    BidResponse bidResponse = bidResponseService.buildBidResponse(
                            price, bidRequest, bidRequest.getImp().get(0), data.get().getCampaign(), creative, selectedDeal
                    );

                    if (bidResponse.getNbr() != null) {
                        campaignStats.setNbr(campaignStats.getNbr() + 1);
                        creativeStats.setNbr(creativeStats.getNbr() + 1);
                    }

                    record.setResponseTimestamp(System.currentTimeMillis());
                    record.setBidResponse(bidResponse);
                    if (bidResponse.getSeatbid() != null) {
                        record.setMarkup(bidResponse.getSeatbid().get(0).getBid().get(0).getAdm());
                    }
                    auctionDao.save(record);
                    campaign.setStatistics(campaignStats);
                    campaignService.saveCampaign(campaign);
                    creative.setStatistics(creativeStats);
                    creativeService.saveCreative(creative);
                    try {
                        return objectMapper.writeValueAsString(bidResponse);
                    } catch (JsonProcessingException ex) {
                        //
                    }
                }
            }
        }
        auctionDao.save(record);
        return generateNoContentResponse(response);
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

    private String generateNoContentResponse(HttpServletResponse response) {
        response.setStatus(204);
        return "";
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
