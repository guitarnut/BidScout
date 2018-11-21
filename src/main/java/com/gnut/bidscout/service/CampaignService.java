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

    public boolean isValidImpressionTTL(String id, long cb) {
        Optional<Campaign> campaign = campaignDao.findById(id);
        if (campaign.isPresent()) {
            Campaign c = campaign.get();
            if (System.currentTimeMillis() - cb <= c.getImpressionExpiry() * 1000) {
                return true;
            }
        }
        return false;
    }

    public void incrementImpression(String id, float cp) {
        Optional<Campaign> campaign = campaignDao.findById(id);
        if (campaign.isPresent()) {
            Campaign c = campaign.get();
            c.getStatistics().setImpressions(c.getStatistics().getImpressions() + 1);
            c.getStatistics().setRevenue(c.getStatistics().getRevenue() + cp / 1000);
            c.getStatistics().setEcpm(((c.getStatistics().getEcpm() + cp / 1000) / c.getStatistics().getImpressions()) * 1000);
            campaignDao.save(c);
        }
    }

    public void incrementExpiredImpression(String id) {
        Optional<Campaign> campaign = campaignDao.findById(id);
        if (campaign.isPresent()) {
            Campaign c = campaign.get();
            c.getStatistics().setExpiredImpressions(c.getStatistics().getExpiredImpressions() + 1);
            campaignDao.save(c);
        }
    }

    public void incrementInvalidImpression(String id) {
        Optional<Campaign> campaign = campaignDao.findById(id);
        if (campaign.isPresent()) {
            Campaign c = campaign.get();
            c.getStatistics().setInvalidImpressions(c.getStatistics().getInvalidImpressions() + 1);
            campaignDao.save(c);
        }
    }

    public void incrementDuplicateImpression(String id) {
        Optional<Campaign> campaign = campaignDao.findById(id);
        if (campaign.isPresent()) {
            Campaign c = campaign.get();
            c.getStatistics().setDuplicateImpressions(c.getStatistics().getDuplicateImpressions() + 1);
            campaignDao.save(c);
        }
    }

    public void addCreativeToCampaign(String owner, String campaignId, String creativeId) {
        Campaign campaign = campaignDao.findByIdAndOwner(campaignId, owner);
        if (campaign != null) {
            Creative creative = creativeService.getCreative(owner, creativeId);
            if (creative != null) {
                List<String> c = campaign.getCreatives();
                if (c == null) {
                    c = new ArrayList<>();
                }
                c.add(creative.getId());
                campaign.setCreatives(c);
                campaignDao.save(campaign);
            }
        }
    }

    public Campaign removeCreativeFromCampaign(String owner, String campaignId, String creativeId) {
        Campaign campaign = campaignDao.findByIdAndOwner(campaignId, owner);
        if (campaign != null) {
            if(campaign.getCreatives() != null) {
                campaign.getCreatives().remove(creativeId);
                campaignDao.save(campaign);
            }
        }
        return campaign;
    }

    public Optional<EligibleCampaignData> targetCampaign(String owner, String publisher, BidRequest bidRequest, HttpServletRequest request) {
        final Optional<Campaign> campaign = selectCampaign(owner, bidRequest);
        if (campaign.isPresent()) {
            final RequestTargetingData targetingData = targetingService.generateTargetingData(publisher, bidRequest, request);

            if (eligibleService.isEligible(targetingData, campaign.get().getRequirements(), Optional.empty())) {
                final EligibleCampaignData campaignData = new EligibleCampaignData();
                campaignData.setCampaign(campaign.get());
                campaignData.setCreatives(eligibleService.getEligibleCreatives(targetingData, campaign.get()));
                campaignData.setData(targetingData);
                return Optional.of(campaignData);
            }
        }
        return Optional.empty();
    }

    public Campaign getCampaign(String account, String id) {
        return campaignDao.findByIdAndOwner(id, account);
    }

    public Campaign getCampaignWithCreative(String owner, String id) {
        return campaignDao.findByCreativesContainsAndOwner(id, owner);
    }

    public Campaign saveCampaign(Campaign campaign) {
        return campaignDao.save(campaign);
    }

    public Map<String, String> getCampaignNames(String owner) {
        List<Campaign> campaigns = campaignDao.findAllByOwner(owner);
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

    private Optional<Campaign> selectCampaign(String owner, BidRequest bidRequest) {
        // Todo: Target off of request
        List<Campaign> campaigns = campaignDao.findAllByEnabledAndOwner(true, owner);
        if (campaigns.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(campaigns.get(0));
    }
}
