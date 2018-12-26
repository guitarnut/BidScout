package com.gnut.bidscout.service;

import com.gnut.bidscout.db.CampaignDao;
import com.gnut.bidscout.db.CreativeDao;
import com.gnut.bidscout.model.Campaign;
import com.gnut.bidscout.model.Creative;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CreativeService {
    private final CreativeDao creativeDao;
    private final CampaignDao campaignDao;

    @Autowired
    public CreativeService(CreativeDao creativeDao, CampaignDao campaignDao) {
        this.creativeDao = creativeDao;
        this.campaignDao = campaignDao;
    }

    public Creative saveCreative(Creative creative) {
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
            c.getStatistics().setEcpm(((c.getStatistics().getEcpm() + cp / 1000) / c.getStatistics().getImpressions()) * 1000);
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
        }
    }
}
