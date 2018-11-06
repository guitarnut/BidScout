package com.gnut.bidscout.service;

import com.gnut.bidscout.db.CampaignDao;
import com.gnut.bidscout.model.Campaign;
import com.gnut.bidscout.model.Creative;
import com.gnut.bidscout.model.EligibleCampaignData;
import com.gnut.bidscout.model.RequestTargetingData;
import com.iab.openrtb.request.BidRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Component
public class CampaignService {
    private final SyncService syncService;
    private final CampaignDao campaignDao;
    private final CreativeService creativeService;
    private final TargetingService targetingService;
    private final EligibleService eligibleService;

    @Autowired
    public CampaignService(SyncService syncService, CampaignDao campaignDao, CreativeService creativeService, TargetingService targetingService, EligibleService eligibleService) {
        this.syncService = syncService;
        this.campaignDao = campaignDao;
        this.creativeService = creativeService;
        this.targetingService = targetingService;
        this.eligibleService = eligibleService;
    }

    public void incrementClick(String id) {
        Optional<Campaign> campaign = campaignDao.findById(id);
        if (campaign.isPresent()) {
            campaign.get().getStatistics().setClicks(campaign.get().getStatistics().getClicks() + 1);
            campaignDao.save(campaign.get());
        }
    }

    public boolean incrementImpressionAndCheckValidTTL(String id, long cb, float cp) {
        Optional<Campaign> campaign = campaignDao.findById(id);
        if (campaign.isPresent()) {
            if (System.currentTimeMillis() - cb > campaign.get().getImpressionExpiry() * 1000) {
                campaign.get().getStatistics().setExpiredImpressions(campaign.get().getStatistics().getExpiredImpressions() + 1);
                campaignDao.save(campaign.get());
                return false;
            } else {
                campaign.get().getStatistics().setImpressions(campaign.get().getStatistics().getImpressions() + 1);
                campaign.get().getStatistics().setRevenue(campaign.get().getStatistics().getRevenue() + cp / 1000);
               // campaign.get().getStatistics().setEcpm((campaign.get().getStatistics().getEcpm() + cp)campaign.get().getStatistics().getImpressions());
                campaignDao.save(campaign.get());
                return true;
            }
        }
        return true;
    }

    public void incrementExpiredImpression(String id) {
        Optional<Campaign> campaign = campaignDao.findById(id);
        if (campaign.isPresent()) {
            campaign.get().getStatistics().setExpiredImpressions(campaign.get().getStatistics().getExpiredImpressions() + 1);
            campaignDao.save(campaign.get());
        }
    }

    public void addCreativeToCampaign(String campaignId, String creativeId) {
        Optional<Campaign> campaign = campaignDao.findById(campaignId);
        if (campaign.isPresent()) {
            Optional<Creative> creative = creativeService.getCreative(creativeId);
            if (creative.isPresent()) {
                List<String> c = campaign.get().getCreatives();
                if (c == null) {
                    c = new ArrayList<>();
                }
                c.add(creative.get().getId());
                campaign.get().setCreatives(c);
                campaignDao.save(campaign.get());
            }
        }
    }

    public Optional<EligibleCampaignData> targetCampaign(String publisher, BidRequest bidRequest, HttpServletRequest request) {
        final Optional<Campaign> campaign = selectCampaign(bidRequest);
        if (campaign.isPresent()) {
            final RequestTargetingData targetingData = targetingService.generateTargetingData(publisher, bidRequest, request);

            if (eligibleService.isEligible(targetingData, campaign.get().getRequirements())) {
                final EligibleCampaignData campaignData = new EligibleCampaignData();
                campaignData.setCampaign(campaign.get());
                campaignData.setCreatives(eligibleService.getEligibleCreatives(targetingData, campaign.get()));
                campaignData.setData(targetingData);
                return Optional.of(campaignData);
            }
        }
        return Optional.empty();
    }

    public Optional<Campaign> getCampaign(String id) {
        return campaignDao.findById(id);
    }

    public Campaign saveCampaign(Campaign campaign) {
        return campaignDao.save(campaign);
    }

    public Map<String, String> getCampaignNames() {
        List<Campaign> campaigns = campaignDao.findAll();
        if (campaigns.isEmpty()) {
            return Collections.emptyMap();
        } else {
            final Map<String, String> results = new HashMap<>();
            campaigns.forEach(c -> {
                results.put(c.getId(), c.getName());
            });
            return results;
        }
    }

    private Optional<Campaign> selectCampaign(BidRequest bidRequest) {
        // Todo: Target off of request
        List<Campaign> campaigns = campaignDao.findAllByEnabled(true);
        if (campaigns.isEmpty()) {
            return null;
        }
        return Optional.of(campaigns.get(0));
    }

}
