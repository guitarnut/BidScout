package com.gnut.bidscout.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gnut.bidscout.db.AuctionDao;
import com.gnut.bidscout.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    // Todo: Remove objectmapper

    public String saveCampaign(String account, Campaign campaign) {
        campaign.setOwner(account);
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

    public String saveCreative(String account, Creative creative) {
        creative.setOwner(account);
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

    public Map<String, String> getCampaignNames(String owner) {
        return campaignService.getCampaignNames(owner);
    }

    public Map<String, String> getCreativeNames(String account) {
        return creativeService.getCreativeNames(account);
    }

    public String getBid(String account, String id) {
        AuctionRecord record = auctionDao.findFirstByBidRequestIdAndOwner(id, account);
        if (record != null && record.getOwner().equals(account)) {
            try {
                return objectMapper.writeValueAsString(record);
            } catch (IOException ex) {
                //
            }
        }
        return "";
    }

    public void addCreativeToCampaign(String owner, String campaignId, String creativeId) {
        campaignService.addCreativeToCampaign(owner, campaignId, creativeId);
    }

    public Campaign removeCreativeFromCampaign(String owner, String campaignId, String creativeId) {
        return campaignService.removeCreativeFromCampaign(owner, campaignId, creativeId);
    }

    public String getCampaign(String account, String campaignId) {
        Campaign campaign = campaignService.getCampaign(account, campaignId);
        if (campaign != null) {
            try {
                return objectMapper.writeValueAsString(campaign);
            } catch (IOException ex) {
                //
            }
        }
        return "";
    }

    public String getCreative(String account, String creativeId) {
        Creative creative = creativeService.getCreative(account, creativeId);
        if (creative != null) {
            try {
                return objectMapper.writeValueAsString(creative);
            } catch (IOException ex) {
                //
            }
        }
        return "";
    }

    public Map<String, String> getCreativeNamesByCampaign(String account, String campaignId) {
        Campaign campaign = campaignService.getCampaign(account, campaignId);
        final Map<String, String> results = new HashMap<>();
        if(campaign != null) {
            campaign.getCreatives().forEach(creativeId->{
                Creative creative = creativeService.getCreative(account, creativeId);
                if (creative != null) {
                    results.put(creative.getId(), creative.getName());
                }
            });
        }
        return results;
    }

    public Campaign getCampaignByProperty(String account, String property, String value) {
        switch(property) {
            case "creative":
                return campaignService.getCampaignWithCreative(account, value);
        }
        return null;
    }

    public List<ClickRecord> getClicks(String account, String bidId) {
        return clickService.getClicks(account, bidId);
    }

    public List<ImpressionRecord> getImpressions(String account, String bidId) {
        return impressionService.getImpressions(account, bidId);
    }
}

