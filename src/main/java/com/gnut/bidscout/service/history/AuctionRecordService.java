package com.gnut.bidscout.service.history;

import com.gnut.bidscout.db.AuctionDao;
import com.gnut.bidscout.model.AuctionRecord;
import com.gnut.bidscout.service.user.AccountService;
import com.gnut.bidscout.service.user.UserAccountStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class AuctionRecordService {
    private final AuctionDao auctionDao;
    private final UserAccountStatisticsService statisticsService;
    private final AccountService accountService;

    @Autowired
    public AuctionRecordService(
            AuctionDao auctionDao,
            UserAccountStatisticsService statisticsService,
            AccountService accountService
    ) {
        this.auctionDao = auctionDao;
        this.statisticsService = statisticsService;
        this.accountService = accountService;
    }

    public List<AuctionRecord> getAllAuctionRecords(Authentication auth) {
        return auctionDao.findAllByOwner(getAccount(auth));
    }

    public AuctionRecord view(Authentication auth, String id) {
        return auctionDao.findByIdAndOwner(id, getAccount(auth));
    }

    public void delete(Authentication auth, String id) {
        AuctionRecord a = auctionDao.findByIdAndOwner(id, getAccount(auth));
        if (a != null) {
            auctionDao.deleteById(id);
            accountService.deleteAuctionRecord(getAccount(auth));
        }
    }

    public List<AuctionRecord> deleteAll(Authentication auth) {
        auctionDao.deleteAllByOwner(getAccount(auth));
        return Collections.emptyList();
    }

    private String getAccount(Authentication auth) {
        return auth.getAuthorities().iterator().next().getAuthority();
    }
}
