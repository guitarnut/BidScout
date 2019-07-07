package com.gnut.bidscout.service.user;

import com.gnut.bidscout.db.UserAccountStatisticsDao;
import com.gnut.bidscout.model.UserAccountStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountService {

    private final UserAccountStatisticsDao statisticsDao;

    @Autowired
    public AccountService(UserAccountStatisticsDao statisticsDao) {
        this.statisticsDao = statisticsDao;
    }

    // Todo: Run schedule to reset account statistics
    public UserAccountStatistics getAccountStatistics(Authentication auth) {
        return statisticsDao.findOneByUser(getAccount(auth));
    }

    public boolean addCampaign() {
        List<UserAccountStatistics> stats = statisticsDao.findAll();
        if (stats.size() > 0) {
            UserAccountStatistics u = stats.get(0);
            if (u.getCampaignsLimit() > u.getCampaigns()) {
                u.setCampaigns(u.getCampaigns() + 1);
                statisticsDao.save(u);
                return true;
            }
        }
        // Todo: temp
        return true;
    }

    public void deleteCampaign(String id) {
        UserAccountStatistics stats = statisticsDao.findOneByUser(id);
        if (stats != null) {
            stats.setCampaigns(stats.getCampaigns() - 1);
            statisticsDao.save(stats);
        }
    }

    public boolean addCreative() {
        List<UserAccountStatistics> stats = statisticsDao.findAll();
        if (stats.size() > 0) {
            UserAccountStatistics u = stats.get(0);
            if (u.getCreativesLimit() > u.getCreatives()) {
                u.setCreatives(u.getCreatives() + 1);
                statisticsDao.save(u);
                return true;
            }
        }
        // Todo: temp
        return true;
    }

    public void deleteCreative() {
        List<UserAccountStatistics> stats = statisticsDao.findAll();
        if (stats.size() > 0) {
            stats.get(0).setCreatives(stats.get(0).getCreatives() - 1);
            statisticsDao.save(stats.get(0));
        }
    }

    public void deleteAuctionRecord(String id) {
        UserAccountStatistics stats = statisticsDao.findOneByUser(id);
        if (stats != null) {
            stats.setAuctionRecords(stats.getAuctionRecords() - 1);
            statisticsDao.save(stats);
        }
    }

    public void deleteVastTagRecord(String account) {
        UserAccountStatistics stats = statisticsDao.findOneByUser(account);
        if (stats != null) {
            stats.setVastRecords(stats.getVastRecords() - 1);
            statisticsDao.save(stats);
        }
    }

    private String getAccount(Authentication auth) {
        return auth.getAuthorities().iterator().next().getAuthority();
    }
}
