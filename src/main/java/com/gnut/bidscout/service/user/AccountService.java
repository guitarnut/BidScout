package com.gnut.bidscout.service.user;

import com.gnut.bidscout.db.UserAccountStatisticsDao;
import com.gnut.bidscout.model.UserAccountStatistics;
import org.springframework.beans.factory.annotation.Autowired;
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
    public UserAccountStatistics getAccountStatistics() {
        List<UserAccountStatistics> stats = statisticsDao.findAll();
        return stats.size() > 0 ? stats.get(0) : null;
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

    public void deleteCampaign() {
        List<UserAccountStatistics> stats = statisticsDao.findAll();
        if (stats.size() > 0) {
            stats.get(0).setCampaigns(stats.get(0).getCampaigns() - 1);
            statisticsDao.save(stats.get(0));
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

    public void deleteAuctionRecord() {
        List<UserAccountStatistics> stats = statisticsDao.findAll();
        if (stats.size() > 0) {
            stats.get(0).setAuctionRecords(stats.get(0).getAuctionRecords() - 1);
            statisticsDao.save(stats.get(0));
        }
    }

    public void deleteVastTagRecord() {
        List<UserAccountStatistics> stats = statisticsDao.findAll();
        if (stats.size() > 0) {
            stats.get(0).setVastRecords(stats.get(0).getVastRecords() - 1);
            statisticsDao.save(stats.get(0));
        }
    }
}
