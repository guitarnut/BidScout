package com.gnut.bidscout.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gnut.bidscout.model.Campaign;
import com.gnut.bidscout.model.Creative;
import com.gnut.bidscout.service.inventory.CampaignService;
import com.gnut.bidscout.service.inventory.CreativeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClientService {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final CampaignService campaignService;
    private final CreativeService creativeService;

    static {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @Autowired
    public ClientService(
            CampaignService campaignService,
            CreativeService creativeService
    ) {

        this.campaignService = campaignService;
        this.creativeService = creativeService;

    }

    /**
     * ------------- Campaigns -------------
     */

    public Campaign getCampaign(String account, String campaignId) {
        return campaignService.getCampaign(account, campaignId);
    }

    /**
     * ------------- Creatives -------------
     */


    public Creative getCreative(String account, String creativeId) {
        return creativeService.getCreative(account, creativeId);
    }


    /**
     * ------------- Bids -------------
     */


    @Deprecated
    public List<List<String>> getBidErrors(String account) {
        return null;
    }

}
