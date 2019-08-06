package com.gnut.bidscout.service.user;

import com.gnut.bidscout.db.UserAccountStatisticsDao;
import com.gnut.bidscout.model.UserAccountStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class AccountService {

    private final AccountStatisticsService statisticsService;
    private final UserAccountStatisticsDao statisticsDao;

    @Autowired
    public AccountService(AccountStatisticsService statisticsService, UserAccountStatisticsDao statisticsDao) {
        this.statisticsService = statisticsService;
        this.statisticsDao = statisticsDao;
    }

    public UserAccountStatistics getAccountStatistics(Authentication auth) {
        return statisticsService.getRecord(getAccount(auth));
    }

    public boolean addCampaign(String id) {
        return statisticsService.addCampaign(id);
    }

    public void deleteCampaign(String id) {
        statisticsService.removeCampaign(id);
    }

    public boolean addCreative(String id) {
        return statisticsService.addCreative(id);
    }

    public void deleteCreative(String id) {
        statisticsService.removeCreative(id);
    }

    public void deleteAuctionRecord(String id) {
        statisticsService.removeAuctionRecord(id);
    }

    public void deleteVastTagRecord(String id) {
        statisticsService.removeVastTagRecord(id);
    }

    private String getAccount(Authentication auth) {
        return auth.getAuthorities().iterator().next().getAuthority();
    }
}
