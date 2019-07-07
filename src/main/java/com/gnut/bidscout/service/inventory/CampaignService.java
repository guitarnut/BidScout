package com.gnut.bidscout.service.inventory;

import com.gnut.bidscout.db.CampaignDao;
import com.gnut.bidscout.model.*;
import com.gnut.bidscout.service.EligibleService;
import com.gnut.bidscout.service.TargetingService;
import com.gnut.bidscout.service.user.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class CampaignService {
    private final CampaignDao campaignDao;
    private final CreativeService creativeService;
    private final TargetingService targetingService;
    private final EligibleService eligibleService;
    private final AccountService accountService;

    @Autowired
    public CampaignService(
            CampaignDao campaignDao,
            CreativeService creativeService,
            TargetingService targetingService,
            EligibleService eligibleService,
            AccountService accountService
    ) {
        this.campaignDao = campaignDao;
        this.creativeService = creativeService;
        this.targetingService = targetingService;
        this.eligibleService = eligibleService;
        this.accountService = accountService;
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

    public Campaign getCampaign(Authentication auth, String id) {
        return campaignDao.findByIdAndOwner(id, getAccount(auth));
    }

    public Campaign createCampaign(Authentication auth, HttpServletResponse response, Campaign c) {
        Campaign campaign = campaignDao.findByNameAndOwner(c.getName(), getAccount(auth));
        if (campaign != null || !accountService.addCampaign()) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return null;
        } else {
            c.setOwner(getAccount(auth));
            return campaignDao.save(c);
        }
    }

    public Campaign saveCampaign(Campaign c) {
        return campaignDao.save(c);
    }

    public Campaign saveCampaign(Authentication auth, String id, Campaign c) {
        Campaign campaign = campaignDao.findByIdAndOwner(id, getAccount(auth));
        if (campaign != null) {
            return campaignDao.save(c);
        } else {
            return null;
        }
    }

    public Limits saveCampaignLimits(Authentication auth, String id, Limits limits) {
        Campaign campaign = campaignDao.findByIdAndOwner(id, getAccount(auth));
        if (campaign != null) {
            campaign.setLimits(limits);
            campaignDao.save(campaign);
            return limits;
        } else {
            return null;
        }
    }

    public Requirements saveCampaignRequirements(Authentication auth, String id, Requirements requirements) {
        Campaign campaign = campaignDao.findByIdAndOwner(id, getAccount(auth));
        if (campaign != null) {
            campaign.setRequirements(requirements);
            campaignDao.save(campaign);
            return requirements;
        } else {
            return null;
        }
    }

    public List<Campaign> getCampaigns(Authentication auth) {
        List<Campaign> campaigns = campaignDao.findAllByOwner(getAccount(auth));
        if (campaigns.isEmpty()) {
            return Collections.emptyList();
        } else {
            return campaigns;
        }
    }

    public void deleteCampaign(Authentication auth, String id) {
        if (campaignDao.findByIdAndOwner(id, getAccount(auth)) != null) {
            campaignDao.deleteById(id);
            accountService.deleteCampaign(getAccount(auth));
        }
    }

    public Requirements getCampaignRequirements(Authentication auth, String id) {
        Campaign campaign = campaignDao.findByIdAndOwner(id, getAccount(auth));
        if (campaign != null) {
            return campaign.getRequirements();
        } else {
            return new Requirements();
        }
    }

    public Limits getCampaignLimits(Authentication auth, String id) {
        Campaign campaign = campaignDao.findByIdAndOwner(id, getAccount(auth));
        if (campaign != null) {
            return campaign.getLimits();
        } else {
            return new Limits();
        }
    }

    public void addCreative(Authentication auth, HttpServletResponse response, String id, String creativeId) {
        Campaign campaign = campaignDao.findByIdAndOwner(id, getAccount(auth));
        if (campaign != null) {
            Creative creative = creativeService.getCreative(auth, creativeId);
            if (creative != null && !campaign.getCreatives().contains(creativeId)) {
                campaign.getCreatives().add(creative.getId());
                campaignDao.save(campaign);
            }
        } else {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
    }

    public void removeCreative(Authentication auth, HttpServletResponse response, String id, String creativeId) {
        Campaign campaign = campaignDao.findByIdAndOwner(id, getAccount(auth));
        if (campaign != null) {
            if (campaign.getCreatives().contains(creativeId)) {
                campaign.getCreatives().remove(creativeId);
                campaignDao.save(campaign);
            }
        } else {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
    }

    public Campaign resetStatistics(Authentication auth, String id) {
        Campaign campaign = campaignDao.findByIdAndOwner(getAccount(auth), id);
        if (campaign != null) {
            campaign.setStatistics(new Statistics());
            campaignDao.save(campaign);
            return campaign;
        }
        return null;
    }

    private String getAccount(Authentication auth) {
        return auth.getAuthorities().iterator().next().getAuthority();
    }
}
