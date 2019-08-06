package com.gnut.bidscout.service.user;

import com.gnut.bidscout.db.UserAccountStatisticsDao;
import com.gnut.bidscout.model.UserAccountStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Component
public class AccountStatisticsService {
    private Executor pool = Executors.newFixedThreadPool(100);
    private final long TIME_PERIOD = 1000 * 60 * 60 * 24;
    private final UserAccountStatisticsDao statisticsDao;

    @Autowired
    public AccountStatisticsService(UserAccountStatisticsDao statisticsDao) {
        this.statisticsDao = statisticsDao;
    }

    public UserAccountStatistics getRecord(String id) {
        UserAccountStatistics record = statisticsDao.findOneByUser(id);
        if (record == null) {
            record = create(id);
        } else {
            checkTimePeriod(record);
        }
        return record;
    }

    // non-blocking db write
    private void save(UserAccountStatistics record) {
        pool.execute(() -> {
            statisticsDao.save(record);
        });
    }

    private UserAccountStatistics create(String id) {
        UserAccountStatistics record = new UserAccountStatistics();
        record.setUser(id);
        record.setPeriodEnd(System.currentTimeMillis() + TIME_PERIOD);
        return statisticsDao.save(record);
    }

    public boolean addAuctionRecord(String id) {
        UserAccountStatistics account = getRecord(id);
        if (account.getAuctionRecords() < account.getAuctionRecordsLimit()) {
            account.setAuctionRecords(account.getAuctionRecords() + 1);
            save(account);
            return true;
        } else {
            return false;
        }
    }

    public boolean removeAuctionRecord(String id) {
        UserAccountStatistics account = getRecord(id);
        if (account.getAuctionRecords() > 0) {
            account.setAuctionRecords(account.getAuctionRecords() - 1);
            save(account);
            return true;
        } else {
            return false;
        }
    }

    public boolean removeAllAuctionRecords(String id) {
        UserAccountStatistics account = getRecord(id);
        if (account.getAuctionRecords() > 0) {
            account.setAuctionRecords(0);
            save(account);
            return true;
        } else {
            return false;
        }
    }

    public boolean addVastTagRecord(String id) {
        UserAccountStatistics account = statisticsDao.findAll().get(0);
        if (account.getVastRecords() < account.getVastRecordsLimit()) {
            account.setVastRecords(account.getVastRecords() + 1);
            save(account);
            return true;
        } else {
            return false;
        }
    }

    public boolean removeVastTagRecord(String id) {
        UserAccountStatistics account = getRecord(id);
        if (account.getVastRecords() > 0) {
            account.setVastRecords(account.getVastRecords() - 1);
            save(account);
            return true;
        } else {
            return false;
        }
    }

    public boolean removeAllVastTagRecords(String id) {
        UserAccountStatistics account = getRecord(id);
        if (account.getVastRecords() > 0) {
            account.setVastRecords(0);
            save(account);
            return true;
        } else {
            return false;
        }
    }

    public boolean addCampaign(String id) {
        UserAccountStatistics account = getRecord(id);
        if (account.getCampaigns() < account.getCampaignsLimit()) {
            account.setCampaigns(account.getCampaigns() + 1);
            save(account);
            return true;
        } else {
            return false;
        }
    }

    public boolean removeCampaign(String id) {
        UserAccountStatistics account = getRecord(id);
        if (account.getCampaigns() > 0) {
            account.setCampaigns(account.getCampaigns() - 1);
            save(account);
            return true;
        } else {
            return false;
        }
    }

    public boolean addCreative(String id) {
        UserAccountStatistics account = getRecord(id);
        if (account.getCreatives() < account.getCreativesLimit()) {
            account.setCreatives(account.getCreatives() + 1);
            save(account);
            return true;
        } else {
            return false;
        }
    }

    public boolean removeCreative(String id) {
        UserAccountStatistics account = getRecord(id);
        if (account.getCreatives() > 0) {
            account.setCreatives(account.getCreatives() - 1);
            save(account);
            return true;
        } else {
            return false;
        }
    }

    public boolean addVast(String id) {
        UserAccountStatistics account = getRecord(id);
        if (account.getVast() < account.getVastLimit()) {
            account.setVast(account.getVast() + 1);
            save(account);
            return true;
        } else {
            return false;
        }
    }

    public boolean removeVast(String id) {
        UserAccountStatistics account = getRecord(id);
        if (account.getVast() > 0) {
            account.setVast(account.getVast() - 1);
            save(account);
            return true;
        } else {
            return false;
        }
    }

    public boolean addBidRequest(String id) {
        UserAccountStatistics account = getRecord(id);
        checkTimePeriod(account);
        account.setBidRequests(account.getBidRequests() + 1);
        if (account.getBidRequests() > account.getBidRequestsLimit()) {
            account.setBidRequestsOverage(account.getBidRequestsOverage() + 1);
        }
        save(account);
        return account.getBidRequests() < account.getBidRequestsLimit();
    }

    public boolean bidRequestLimitViolation(String id) {
        UserAccountStatistics account = getRecord(id);
        return account.getBidRequestsOverage() < account.getBidRequestsOverageLimit();
    }

    public boolean addVastTagRequest(String id) {
        UserAccountStatistics account = statisticsDao.findAll().get(0);
        checkTimePeriod(account);
        account.setVastTagRequests(account.getVastTagRequests() + 1);
        if (account.getVastTagRequests() > account.getVastTagRequestsLimit()) {
            account.setVastTagRequestsOverage(account.getVastTagRequestsOverage() + 1);
        }
        save(account);
        return account.getVastTagRequests() < account.getVastTagRequestsLimit();
    }

    public boolean vastTagRequestLimitViolation(String id) {
        UserAccountStatistics account = getRecord(id);
        return account.getBidRequestsOverage() < account.getBidRequestsOverageLimit();
    }

    private void checkTimePeriod(UserAccountStatistics account) {
        Long timestamp = System.currentTimeMillis();
        if (timestamp > account.getPeriodEnd()) {
            account.setPeriodEnd(timestamp + TIME_PERIOD);
            account.setBidRequests(0);
            account.setBidRequestsOverage(0);
            account.setVastTagRequests(0);
            account.setVastTagRequestsOverage(0);
            save(account);
        }
    }

    // Todo: Run schedule to reset account statistics
    public UserAccountStatistics getAccountStatus(String id) {
        UserAccountStatistics stats = statisticsDao.findOneByUser(id);
//        if (stats != null) {
//            if (stats.getPeriodEnd() < System.currentTimeMillis()) {
//                stats.setPeriodEnd(System.currentTimeMillis() + 1000 * 60 * 60 * 24);
//                stats.setVastTagRequests(0);
//                stats.setBidRequests(0);
//                stats.setVastTagRequestsOverage(0);
//                stats.setBidRequestsOverage(0);
//                statisticsDao.save(stats);
//            }
//        }
        return stats;
    }
}
