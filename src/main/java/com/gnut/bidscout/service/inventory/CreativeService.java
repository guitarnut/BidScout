package com.gnut.bidscout.service.inventory;

import com.gnut.bidscout.db.CampaignDao;
import com.gnut.bidscout.db.CreativeDao;
import com.gnut.bidscout.model.*;
import com.gnut.bidscout.service.user.AccountService;
import com.gnut.bidscout.service.user.UserAccountStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Component
public class CreativeService {
    private final CreativeDao creativeDao;
    private final CampaignDao campaignDao;
    private final VastService vastService;
    private final UserAccountStatisticsService statisticsService;
    private final AccountService accountService;

    @Autowired
    public CreativeService(
            CreativeDao creativeDao,
            CampaignDao campaignDao,
            VastService vastService,
            UserAccountStatisticsService statisticsService,
            AccountService accountService
    ) {
        this.creativeDao = creativeDao;
        this.campaignDao = campaignDao;
        this.vastService = vastService;
        this.statisticsService = statisticsService;
        this.accountService = accountService;
    }

    public Creative saveCreative(String id, Creative creative) {
        return creativeDao.save(creative);
    }

    public Creative getCreative(String account, String id) {
        return creativeDao.findByIdAndOwner(id, account);
    }

    public Iterable<Creative> getCreatives(List<String> ids) {
        return creativeDao.findAllById(ids);
    }

    public Map<String, String> getCreativeNames(String account) {
        List<Creative> creatives = creativeDao.findAllByOwner(account);
        if (creatives.isEmpty()) {
            return Collections.emptyMap();
        } else {
            final Map<String, String> results = new HashMap<>();
            creatives.forEach(c -> {
                results.put(c.getId(), c.getName());
            });
            return results;
        }
    }

    public Creative createCreative(HttpServletResponse response, Creative c) {
        Creative creative = creativeDao.findByName(c.getName());
        if (creative != null || !accountService.addCreative()) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return null;
        } else {
            return creativeDao.save(c);
        }
    }

    public Requirements getCreativeRequirements(String id) {
        Optional<Creative> creative = creativeDao.findById(id);
        if (creative.isPresent()) {
            return creative.get().getRequirements();
        } else {
            return new Requirements();
        }
    }

    public Limits getCreativeLimits(String id) {
        Optional<Creative> creative = creativeDao.findById(id);
        if (creative.isPresent()) {
            return creative.get().getLimits();
        } else {
            return new Limits();
        }
    }

    public Limits saveCreativeLimits(String id, Limits limits) {
        Optional<Creative> creative = creativeDao.findById(id);
        if (creative.isPresent()) {
            creative.get().setLimits(limits);
            creativeDao.save(creative.get());
            return limits;
        } else {
            return null;
        }
    }

    public Requirements saveCreativeRequirements(String id, Requirements requirements) {
        Optional<Creative> creative = creativeDao.findById(id);
        if (creative.isPresent()) {
            creative.get().setRequirements(requirements);
            creativeDao.save(creative.get());
            return requirements;
        } else {
            return null;
        }
    }

    public List<Creative> getCreatives() {
        List<Creative> creatives = creativeDao.findAll();
        if (creatives.isEmpty()) {
            return Collections.emptyList();
        } else {
            return creatives;
        }
    }

    public Creative getCreative(String id) {
        Optional<Creative> creative = creativeDao.findById(id);
        if (creative.isPresent()) {
            return creative.get();
        } else {
            return null;
        }
    }

    public Creative saveCreative(Creative c) {
        return creativeDao.save(c);
    }

    public void deleteCreative(String id) {
        if (creativeDao.findById(id).isPresent()) {
            creativeDao.deleteById(id);
            accountService.deleteCreative();
        }
    }

    public void incrementClick(String id) {
        Optional<Creative> creative = creativeDao.findById(id);
        if (creative.isPresent()) {
            creative.get().getStatistics().setClicks(creative.get().getStatistics().getClicks() + 1);
            creativeDao.save(creative.get());
        }
    }

    public void incrementImpression(String id, float cp) {
        Optional<Creative> creative = creativeDao.findById(id);
        if (creative.isPresent()) {
            Creative c = creative.get();
            c.getStatistics().setImpressions(c.getStatistics().getImpressions() + 1);
            c.getStatistics().setRevenue(c.getStatistics().getRevenue() + cp / 1000);
            c.getStatistics().setEcpm((c.getStatistics().getRevenue() / c.getStatistics().getImpressions()) * 1000);
            creativeDao.save(c);
        }
    }

    public void incrementInvalidImpression(String id) {
        Optional<Creative> creative = creativeDao.findById(id);
        if (creative.isPresent()) {
            Creative c = creative.get();
            c.getStatistics().setInvalidImpressions(c.getStatistics().getInvalidImpressions() + 1);
            creativeDao.save(c);
        }
    }

    public void incrementDuplicateImpression(String id) {
        Optional<Creative> creative = creativeDao.findById(id);
        if (creative.isPresent()) {
            Creative c = creative.get();
            c.getStatistics().setDuplicateImpressions(c.getStatistics().getDuplicateImpressions() + 1);
            creativeDao.save(c);
        }
    }

    public void incrementExpiredImpression(String id) {
        Optional<Creative> creative = creativeDao.findById(id);
        if (creative.isPresent()) {
            Creative c = creative.get();
            c.getStatistics().setExpiredImpressions(c.getStatistics().getExpiredImpressions() + 1);
            creativeDao.save(c);
        }
    }

    public void deleteCreative(String id, String account) {
        if (creativeDao.findByIdAndOwner(id, account) != null) {
            List<Campaign> campaigns = campaignDao.findAllByOwner(account);
            campaigns.forEach(c -> {
                if (c.getCreatives().contains(id)) {
                    c.getCreatives().remove(id);
                    campaignDao.save(c);
                }
            });
            creativeDao.deleteById(id);
            statisticsService.removeCreative(account);
        }
    }

    public Creative resetStatistics(String id) {
        Optional<Creative> creative = creativeDao.findById(id);
        if (creative.isPresent()) {
            creative.get().setStatistics(new Statistics());
            creativeDao.save(creative.get());
            return creative.get();
        }
        return null;
    }
}
