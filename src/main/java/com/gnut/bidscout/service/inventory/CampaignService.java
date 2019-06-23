package com.gnut.bidscout.service.inventory;

import com.gnut.bidscout.db.CampaignDao;
import com.gnut.bidscout.model.*;
import com.gnut.bidscout.service.EligibleService;
import com.gnut.bidscout.service.SyncService;
import com.gnut.bidscout.service.TargetingService;
import com.gnut.bidscout.service.user.UserAccountStatisticsService;
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
    private final UserAccountStatisticsService statisticsService;

    @Autowired
    public CampaignService(
            SyncService syncService,
            CampaignDao campaignDao,
            CreativeService creativeService,
            TargetingService targetingService,
            EligibleService eligibleService,
            UserAccountStatisticsService statisticsService
    ) {
        this.syncService = syncService;
        this.campaignDao = campaignDao;
        this.creativeService = creativeService;
        this.targetingService = targetingService;
        this.eligibleService = eligibleService;
        this.statisticsService = statisticsService;
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
            if (c.getImpressionExpiry() == 0 || System.currentTimeMillis() - cb <= c.getImpressionExpiry() * 1000) {
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
            c.getStatistics().setEcpm((c.getStatistics().getRevenue() / c.getStatistics().getImpressions()) * 1000);
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
            if (campaign.getCreatives() != null) {
                campaign.getCreatives().remove(creativeId);
                campaignDao.save(campaign);
            }
        }
        return campaign;
    }

    public Optional<EligibleCampaignData> targetCampaign(
            Campaign campaign,
            AuctionImp auctionImp,
            HttpServletRequest request,
            AuctionRecord auctionRecord,
            Iterable<Creative> campaignCreatives
    ) {
        final RequestTargetingData targetingData = targetingService.generateTargetingData(auctionImp, request);

        if (eligibleService.isEligible(targetingData, campaign.getRequirements(), Optional.of(campaign), Optional.empty(), auctionRecord)) {
            final EligibleCampaignData campaignData = new EligibleCampaignData();
            campaignData.setCampaign(campaign);
            campaignData.setCreatives(eligibleService.getEligibleCreatives(targetingData, campaign, auctionRecord, campaignCreatives));
            campaignData.setData(targetingData);
            return Optional.of(campaignData);
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

    public void deleteCampaign(String id, String account) {
        if (campaignDao.findByIdAndOwner(id, account) != null) {
            campaignDao.deleteById(id);
            statisticsService.removeCampaign(account);
        }
    }
}
