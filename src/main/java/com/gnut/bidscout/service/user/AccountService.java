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

    public void addCampaign() {
        List<UserAccountStatistics> stats = statisticsDao.findAll();
        if (stats.size() > 0) {
            stats.get(0).setCampaigns(stats.get(0).getCampaigns() + 1);
            statisticsDao.save(stats.get(0));
        }
    }

    public void deleteCampaign() {
        List<UserAccountStatistics> stats = statisticsDao.findAll();
        if (stats.size() > 0) {
            stats.get(0).setCampaigns(stats.get(0).getCampaigns() - 1);
            statisticsDao.save(stats.get(0));
        }
    }
}
