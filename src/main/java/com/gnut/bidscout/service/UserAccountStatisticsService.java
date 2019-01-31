package com.gnut.bidscout.service;

import com.gnut.bidscout.db.UserAccountStatisticsDao;
import com.gnut.bidscout.model.UserAccountStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Component
public class UserAccountStatisticsService {
    private Executor pool = Executors.newFixedThreadPool(100);
    private final long TIME_PERIOD = 1000 * 60 * 60 * 24;
    private final UserAccountStatisticsDao statisticsDao;

    @Autowired
    public UserAccountStatisticsService(UserAccountStatisticsDao statisticsDao) {
        this.statisticsDao = statisticsDao;
    }

    private UserAccountStatistics getRecord(String id) {
        return statisticsDao.findOneByUser(id);
    }

    private void save(UserAccountStatistics record) {
        pool.execute(() -> {
            statisticsDao.save(record);
        });
    }

    private void create(String user) {
        UserAccountStatistics record = new UserAccountStatistics();
        record.setUser(user);
        record.setPeriodEnd(System.currentTimeMillis() + TIME_PERIOD);
        statisticsDao.save(record);
    }

    public boolean addAuctionRecord(String user) {
        UserAccountStatistics account = getRecord(user);
        if (account != null && account.getAuctionRecords() < account.getAuctionRecordsLimit()) {
            account.setAuctionRecords(account.getAuctionRecords() + 1);
            save(account);
            return true;
        } else {
            if (account == null) {
                create(user);
            }
            return false;
        }
    }

    public boolean removeAuctionRecord(String user) {
        UserAccountStatistics account = getRecord(user);
        if (account != null && account.getAuctionRecords() > 0) {
            account.setAuctionRecords(account.getAuctionRecords() - 1);
            save(account);
            return true;
        } else {
            if (account == null) {
                create(user);
            }
            return false;
        }
    }

    public boolean removeAllAuctionRecords(String user) {
        UserAccountStatistics account = getRecord(user);
        if (account != null && account.getAuctionRecords() > 0) {
            account.setAuctionRecords(0);
            save(account);
            return true;
        } else {
            if (account == null) {
                create(user);
            }
            return false;
        }
    }

    public boolean addVastTagRecord(String user) {
        UserAccountStatistics account = getRecord(user);
        if (account != null && account.getVastRecords() < account.getVastRecordsLimit()) {
            account.setVastRecords(account.getVastRecords() + 1);
            save(account);
            return true;
        } else {
            if (account == null) {
                create(user);
            }
            return false;
        }
    }

    public boolean removeVastTagRecord(String user) {
        UserAccountStatistics account = getRecord(user);
        if (account != null && account.getVastRecords() > 0) {
            account.setVastRecords(account.getVastRecords() - 1);
            save(account);
            return true;
        } else {
            if (account == null) {
                create(user);
            }
            return false;
        }
    }

    public boolean removeAllVastTagRecords(String user) {
        UserAccountStatistics account = getRecord(user);
        if (account != null && account.getVastRecords() > 0) {
            account.setVastRecords(0);
            save(account);
            return true;
        } else {
            if (account == null) {
                create(user);
            }
            return false;
        }
    }

    public boolean addCampaign(String user) {
        UserAccountStatistics account = getRecord(user);
        if (account != null && account.getCampaigns() < account.getCampaignsLimit()) {
            account.setCampaigns(account.getCampaigns() + 1);
            save(account);
            return true;
        } else {
            if (account == null) {
                create(user);
            }
            return false;
        }
    }

    public boolean removeCampaign(String user) {
        UserAccountStatistics account = getRecord(user);
        if (account != null && account.getCampaigns() > 0) {
            account.setCampaigns(account.getCampaigns() - 1);
            save(account);
            return true;
        } else {
            if (account == null) {
                create(user);
            }
            return false;
        }
    }

    public boolean addCreative(String user) {
        UserAccountStatistics account = getRecord(user);
        if (account != null && account.getCreatives() < account.getCreativesLimit()) {
            account.setCreatives(account.getCreatives() + 1);
            save(account);
            return true;
        } else {
            if (account == null) {
                create(user);
            }
            return false;
        }
    }

    public boolean removeCreative(String user) {
        UserAccountStatistics account = getRecord(user);
        if (account != null && account.getCreatives() > 0) {
            account.setCreatives(account.getCreatives() - 1);
            save(account);
            return true;
        } else {
            if (account == null) {
                create(user);
            }
            return false;
        }
    }

    public boolean addVast(String user) {
        UserAccountStatistics account = getRecord(user);
        if (account != null && account.getVast() < account.getVastLimit()) {
            account.setVast(account.getVast() + 1);
            save(account);
            return true;
        } else {
            if (account == null) {
                create(user);
            }
            return false;
        }
    }

    public boolean removeVast(String user) {
        UserAccountStatistics account = getRecord(user);
        if (account != null && account.getVast() > 0) {
            account.setVast(account.getVast() - 1);
            save(account);
            return true;
        } else {
            if (account == null) {
                create(user);
            }
            return false;
        }
    }

    public boolean addBidRequest(String user) {
        UserAccountStatistics account = getRecord(user);
        if (account != null) {
            checkTimePeriod(account);
            account.setBidRequests(account.getBidRequests() + 1);
            if (account.getBidRequests() > account.getBidRequestsLimit()) {
                account.setBidRequestsOverage(account.getBidRequestsOverage() + 1);
            }
            save(account);
            return account.getBidRequests() < account.getBidRequestsLimit();
        } else {
            create(user);
            return false;
        }
    }

    public boolean bidRequestLimitViolation(String user) {
        UserAccountStatistics account = getRecord(user);
        if (account != null) {
            return account.getBidRequestsOverage() < account.getBidRequestsOverageLimit();
        } else {
            create(user);
            // for safety, return true and shut down this account if no stats are found
            return true;
        }
    }

    public boolean addVastTagRequest(String user) {
        UserAccountStatistics account = getRecord(user);
        if (account != null) {
            checkTimePeriod(account);
            account.setVastTagRequests(account.getVastTagRequests() + 1);
            if (account.getVastTagRequests() > account.getVastTagRequestsLimit()) {
                account.setVastTagRequestsOverage(account.getVastTagRequestsOverage() + 1);
            }
            save(account);
            return account.getVastTagRequests() < account.getVastTagRequestsLimit();
        } else {
            create(user);
            return false;
        }
    }

    public boolean vastTagRequestLimitViolation(String user) {
        UserAccountStatistics account = getRecord(user);
        if (account != null) {
            return account.getBidRequestsOverage() < account.getBidRequestsOverageLimit();
        } else {
            create(user);
            // for safety, return true and shut down this account if no stats are found
            return true;
        }
    }

    private void checkTimePeriod(UserAccountStatistics account) {
        Long timestamp = System.currentTimeMillis();
        if (timestamp > account.getPeriodEnd()) {
            account.setPeriodEnd(timestamp + TIME_PERIOD);
            account.setBidRequests(0);
            account.setBidRequestsOverage(0);
            account.setVastTagRequests(0);
            account.setVastTagRequestsOverage(0);
        }
    }
}
