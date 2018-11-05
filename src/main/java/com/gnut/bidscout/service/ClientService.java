package com.gnut.bidscout.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gnut.bidscout.db.AuctionDao;
import com.gnut.bidscout.model.AuctionRecord;
import com.gnut.bidscout.model.Campaign;
import com.gnut.bidscout.model.Creative;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ClientService {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final AuctionDao auctionDao;
    private final ImpressionService impressionService;
    private final ClickService clickService;
    private final CampaignService campaignService;
    private final CreativeService creativeService;

    static {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @Autowired
    public ClientService(
            AuctionDao auctionDao,
            ImpressionService impressionService,
            ClickService clickService,
            CampaignService campaignService,
            CreativeService creativeService
    ) {
        this.auctionDao = auctionDao;
        this.impressionService = impressionService;
        this.clickService = clickService;
        this.campaignService = campaignService;
        this.creativeService = creativeService;
    }

    public String saveCampaign(Campaign campaign) {
        Campaign savedCampaign = campaignService.saveCampaign(campaign);
        if (savedCampaign != null) {
            try {
                return objectMapper.writeValueAsString(savedCampaign);
            } catch (IOException ex) {
                //
            }
        }
        return "";
    }

    public String saveCreative(Creative creative) {
        Creative savedCreative = creativeService.saveCreative(creative);
        if (savedCreative != null) {
            try {
                return objectMapper.writeValueAsString(savedCreative);
            } catch (IOException ex) {
                //
            }
        }
        return "";
    }

    public String getBid(String id) {
        AuctionRecord record = auctionDao.findFirstByBidRequestId(id);
        if (record != null) {
            try {
                return objectMapper.writeValueAsString(record);
            } catch (IOException ex) {
                //
            }
        }
        return "";
    }
}
