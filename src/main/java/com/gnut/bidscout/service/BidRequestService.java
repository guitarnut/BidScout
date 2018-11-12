package com.gnut.bidscout.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gnut.bidscout.db.AuctionDao;
import com.gnut.bidscout.model.*;
import com.gnut.bidscout.rtb.BidRequestValidator;
import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.iab.openrtb.request.BidRequest;
import com.iab.openrtb.response.BidResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;
import java.util.Set;

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
        final BidRequest bidRequest;
        AuctionRecord record = new AuctionRecord();
        try {
            bidRequest = objectMapper.readValue(stringifyPostData(request), BidRequest.class);
            record.setRequestTimestamp(System.currentTimeMillis());
            record.setIp(request.getRemoteUser());
            record.setUserAgent(request.getHeader("User-Agent"));
            record.setCookies(request.getHeader("Cookie"));
            record.setxForwardedFor(request.getHeader("X-Forwarded-For"));
            record.setHost(request.getHeader("Host"));
            record.setBidRequest(bidRequest);
            record.setBidRequestId(bidRequest.getId());
        } catch (IOException ex) {
            return generateNoContentResponse(response);
        }

        Set<BidRequestValidator.Violation> violations = bidRequestValidator.validateBidRequest();

        if (violations.isEmpty()) {
            final Optional<EligibleCampaignData> data = campaignService.targetCampaign(bidder, publisher, bidRequest, request);

            if (data.isPresent() && !data.get().getCreatives().isEmpty()) {
                final Campaign campaign = data.get().getCampaign();
                final Creative creative = data.get().getCreatives().iterator().next();
                final double bp = Math.random() * (creative.getMaxBid() - creative.getMinBid()) + creative.getMinBid();
                final BigDecimal price = new BigDecimal(bp).setScale(2, RoundingMode.HALF_UP);

                record.setOwner(campaign.getOwner());
                record.setCampaign(campaign.getId());
                record.setCreative(creative.getId());

                Statistics campaignStats = campaign.getStatistics();
                if(campaignStats == null) {
                    campaignStats = new Statistics();
                }

                Statistics creativeStats = creative.getStatistics();
                if(creativeStats == null) {
                    creativeStats = new Statistics();
                }

                campaignStats.setRequests(campaignStats.getRequests() + 1);
                creativeStats.setRequests(creativeStats.getRequests() + 1);

                if (Float.valueOf(price.toString()) >= data.get().getData().getBidfloor()) {
                    campaignStats.setBids(campaignStats.getBids() + 1);
                    campaignStats.setBidPriceTotal(campaignStats.getBidPriceTotal() + Float.valueOf(price.toString())/1000);
                    creativeStats.setBids(creativeStats.getBids() + 1);
                    creativeStats.setBidPriceTotal(creativeStats.getBidPriceTotal() + Float.valueOf(price.toString())/1000);

                    BidResponse bidResponse = bidResponseService.buildBidResponse(
                            price, bidRequest, bidRequest.getImp().get(0), data.get().getCampaign(), creative
                    );

                    if(bidResponse.getNbr() != null) {
                        campaignStats.setNbr(campaignStats.getNbr() + 1);
                        creativeStats.setNbr(creativeStats.getNbr() + 1);
                    }

                    record.setResponseTimestamp(System.currentTimeMillis());
                    record.setBidResponse(bidResponse);
                    if(bidResponse.getSeatbid() != null) {
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

        return generateNoContentResponse(response);
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
