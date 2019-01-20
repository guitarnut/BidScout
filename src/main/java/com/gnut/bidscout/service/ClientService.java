package com.gnut.bidscout.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gnut.bidscout.db.AuctionDao;
import com.gnut.bidscout.db.EventRecordDao;
import com.gnut.bidscout.model.*;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ClientService {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final AuctionDao auctionDao;
    private final ImpressionService impressionService;
    private final ClickService clickService;
    private final CampaignService campaignService;
    private final CreativeService creativeService;
    private final AuctionRecordService auctionRecordService;
    private final VastService vastService;
    private final EventRecordDao eventRecordDao;

    static {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @Autowired
    public ClientService(
            AuctionDao auctionDao,
            ImpressionService impressionService,
            ClickService clickService,
            CampaignService campaignService,
            CreativeService creativeService,
            AuctionRecordService auctionRecordService,
            VastService vastService,
            EventRecordDao eventRecordDao
    ) {
        this.auctionDao = auctionDao;
        this.impressionService = impressionService;
        this.clickService = clickService;
        this.campaignService = campaignService;
        this.creativeService = creativeService;
        this.auctionRecordService = auctionRecordService;
        this.vastService = vastService;
        this.eventRecordDao = eventRecordDao;
    }

    /**
     * ------------- Campaigns -------------
     */

    public Campaign saveCampaign(String account, Campaign campaign) {
        campaign.setOwner(account);
        if (!Strings.isNullOrEmpty(campaign.getId())) {
            Campaign c = campaignService.getCampaign(account, campaign.getId());
            if (c != null) {
                c.copyValues(campaign);
            }
            return campaignService.saveCampaign(c);
        }
        return campaignService.saveCampaign(campaign);
    }

    public Map<String, String> getCampaignNames(String owner) {
        return campaignService.getCampaignNames(owner);
    }

    public Campaign getCampaign(String account, String campaignId) {
        return campaignService.getCampaign(account, campaignId);
    }

    public Campaign getCampaignByProperty(String account, String property, String value) {
        switch (property) {
            case "creative":
                return campaignService.getCampaignWithCreative(account, value);
        }
        return null;
    }

    public void deleteCampaign(String account, String creativeId) {
        campaignService.deleteCampaign(creativeId, account);
    }

    /**
     * ------------- Creatives -------------
     */

    public Creative saveCreative(String account, Creative creative) {
        creative.setOwner(account);
        if (!Strings.isNullOrEmpty(creative.getId())) {
            Creative c = creativeService.getCreative(account, creative.getId());
            if (c != null) {
                c.copyValues(creative);
            }
            return creativeService.saveCreative(c);
        }
        return creativeService.saveCreative(creative);
    }

    public Map<String, String> getCreativeNames(String account) {
        return creativeService.getCreativeNames(account);
    }

    public void addCreativeToCampaign(String owner, String campaignId, String creativeId) {
        campaignService.addCreativeToCampaign(owner, campaignId, creativeId);
    }

    public Campaign removeCreativeFromCampaign(String owner, String campaignId, String creativeId) {
        return campaignService.removeCreativeFromCampaign(owner, campaignId, creativeId);
    }

    public Creative getCreative(String account, String creativeId) {
        return creativeService.getCreative(account, creativeId);
    }

    public Map<String, String> getCreativeNamesByCampaign(String account, String campaignId) {
        Campaign campaign = campaignService.getCampaign(account, campaignId);
        final Map<String, String> results = new HashMap<>();
        if (campaign != null) {
            if (campaign.getCreatives() != null) {
                campaign.getCreatives().forEach(creativeId -> {
                    Creative creative = creativeService.getCreative(account, creativeId);
                    if (creative != null) {
                        results.put(creative.getId(), creative.getName());
                    }
                });
            }
        }
        return results;
    }

    public void deleteCreative(String account, String creativeId) {
        creativeService.deleteCreative(creativeId, account);
    }

    /**
     * ------------- Bids -------------
     */

    public AuctionRecord getBid(String account, String id) {
        AuctionRecord record = auctionDao.findFirstByIdAndOwner(id, account);
        if (record != null && record.getOwner().equals(account)) {
            return record;
        }
        return null;
    }

    public List<List<String>> getBidErrors(String account) {
        List<AuctionRecord> record = auctionDao.findAllByBidRequestErrorsIsNotNullAndOwner(account);
        if (record != null) {
            final List<List<String>> result = new ArrayList<>();
            record.forEach(r -> {
                if (r.getBidRequestErrors() != null && !r.getBidRequestErrors().isEmpty()) {
                    result.add(r.getBidRequestErrors());
                }
            });
            return result;
        }
        return null;
    }

    public Map<String, String> getAuctionRecordList(String account) {
        return auctionRecordService.getListOfAllRecords(account);
    }

    public void deleteAllBids(String account) {
        auctionRecordService.deleteAllBids(account);
    }

    public void deleteBid(String account, String id) {
        auctionRecordService.deleteBid(account, id);
    }

    /**
     * ------------- Clicks -------------
     */

    public List<ClickRecord> getClicks(String account, String bidId) {
        return clickService.getClicks(account, bidId);
    }

    /**
     * ------------- Impressions -------------
     */

    public List<ImpressionRecord> getImpressions(String account, String bidId) {
        return impressionService.getImpressions(account, bidId);
    }

    /**
     * ------------- XML -------------
     */

    public void saveXml(String account, Xml xml) {
        xml.setOwner(account);
        if (!Strings.isNullOrEmpty(xml.getId())) {
            Xml x = vastService.getXml(account, xml.getId());
            if (x != null) {
                x.copyValues(xml);
            }
            vastService.saveXml(account, x);
        } else {
            vastService.saveXml(account, xml);
        }
    }

    public Map<String, String> getAllXml(String account) {
        return vastService.getAllVastDocuments(account);
    }

    public Xml getXml(String account, String xmlId) {
        return vastService.getXml(account, xmlId);
    }

    public void deleteXml(String account, String xmlId) {
        vastService.deleteXml(account, xmlId);
    }

    public void resetCreative(String account, String creativeId) {
        final Creative c = creativeService.getCreative(account, creativeId);
        if (c != null) {
            c.setStatistics(resetStatistics(c.getStatistics()));
            creativeService.saveCreative(c);
        }
    }

    public void resetCampaign(String account, String campaignId) {
        final Campaign c = campaignService.getCampaign(account, campaignId);
        if (c != null) {
            c.setStatistics(resetStatistics(c.getStatistics()));
            campaignService.saveCampaign(c);
        }
    }

    private Statistics resetStatistics(Statistics oldStats) {
        final Statistics resetStats = new Statistics();
        resetStats.setId(oldStats.getId());
        return resetStats;
    }

    public Map<String, VastTagRecord> getAllVastRecords(String account) {
        final List<VastTagRecord> records = vastService.getAllVastTagRecords(account);
        if (records.isEmpty()) {
            return Collections.emptyMap();
        } else {
            final Map<String, VastTagRecord> results = new HashMap<>();
            records.forEach(r -> {
                results.put(r.getId(), r);
            });
            return results;
        }
    }

    public VastTagRecord getVastRecordByRequestId(String account, String requestId) {
        return vastService.getVastTagRecord(account, requestId);
    }

    public List<EventRecord> getAllVastTagEvents(String requestId) {
        return eventRecordDao.findAllByTagRequestId(requestId);
    }
}
