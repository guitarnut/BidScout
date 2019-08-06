package com.gnut.bidscout.service.inventory;

import com.gnut.bidscout.db.CampaignDao;
import com.gnut.bidscout.db.CreativeDao;
import com.gnut.bidscout.model.*;
import com.gnut.bidscout.service.user.AccountService;
import com.gnut.bidscout.service.user.AccountStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Component
public class CreativeService {
    private final CreativeDao creativeDao;
    private final CampaignDao campaignDao;
    private final VastService vastService;
    private final AccountStatisticsService statisticsService;
    private final AccountService accountService;

    @Autowired
    public CreativeService(
            CreativeDao creativeDao,
            CampaignDao campaignDao,
            VastService vastService,
            AccountStatisticsService statisticsService,
            AccountService accountService
    ) {
        this.creativeDao = creativeDao;
        this.campaignDao = campaignDao;
        this.vastService = vastService;
        this.statisticsService = statisticsService;
        this.accountService = accountService;
    }

    public Creative saveCreative(Authentication auth, String id, Creative creative) {
        creative.setOwner(getAccount(auth));
        creative.setId(id);
        return creativeDao.save(creative);
    }

    public Creative getCreative(String account, String id) {
        return creativeDao.findByIdAndOwner(id, account);
    }

    public Iterable<Creative> getCreatives(List<String> ids) {
        return creativeDao.findAllById(ids);
    }

    public Creative createCreative(Authentication auth, HttpServletResponse response, Creative c) {
        Creative creative = creativeDao.findByNameAndOwner(c.getName(), getAccount(auth));
        if (creative != null || !accountService.addCreative(getAccount(auth))) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return null;
        } else {
            c.setOwner(getAccount(auth));
            return creativeDao.save(c);
        }
    }

    public Requirements getCreativeRequirements(Authentication auth, String id) {
        Creative creative = creativeDao.findByIdAndOwner(id, getAccount(auth));
        if (creative != null) {
            return creative.getRequirements();
        } else {
            return new Requirements();
        }
    }

    public Limits getCreativeLimits(Authentication auth, String id) {
        Creative creative = creativeDao.findByIdAndOwner(id, getAccount(auth));
        if (creative != null) {
            return creative.getLimits();
        } else {
            return new Limits();
        }
    }

    public Limits saveCreativeLimits(Authentication auth, String id, Limits limits) {
        Creative creative = creativeDao.findByIdAndOwner(id, getAccount(auth));
        if (creative != null) {
            creative.setLimits(limits);
            creativeDao.save(creative);
            return limits;
        } else {
            return null;
        }
    }

    public Requirements saveCreativeRequirements(Authentication auth, String id, Requirements requirements) {
        Creative creative = creativeDao.findByIdAndOwner(id, getAccount(auth));
        if (creative != null) {
            creative.setRequirements(requirements);
            creativeDao.save(creative);
            return requirements;
        } else {
            return null;
        }
    }

    public List<Creative> getCreatives(Authentication auth) {
        List<Creative> creatives = creativeDao.findAllByOwner(getAccount(auth));
        if (creatives.isEmpty()) {
            return Collections.emptyList();
        } else {
            return creatives;
        }
    }

    public Creative getCreative(Authentication auth, String id) {
        return creativeDao.findByIdAndOwner(id, getAccount(auth));
    }

    public Creative saveCreative(Creative c) {
        return creativeDao.save(c);
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

    public void deleteCreative(String id, Authentication auth) {
        if (creativeDao.findByIdAndOwner(id, getAccount(auth)) != null) {
            List<Campaign> campaigns = campaignDao.findAllByOwner(getAccount(auth));
            campaigns.forEach(c -> {
                if (c.getCreatives().contains(id)) {
                    c.getCreatives().remove(id);
                    campaignDao.save(c);
                }
            });
            creativeDao.deleteById(id);
            statisticsService.removeCreative(getAccount(auth));
        }
    }

    public Creative resetStatistics(Authentication auth, String id) {
        Creative creative = creativeDao.findByIdAndOwner(id, getAccount(auth));
        if (creative != null) {
            creative.setStatistics(new Statistics());
            creativeDao.save(creative);
            return creative;
        }
        return null;
    }

    public void setCreativeType(String id, Creative.Type type) {
        Optional<Creative> creative = creativeDao.findById(id);
        if (creative.isPresent()) {
            creative.get().setType(type);
            creativeDao.save(creative.get());
        }
    }

    private String getAccount(Authentication auth) {
        return auth.getAuthorities().iterator().next().getAuthority();
    }
}
