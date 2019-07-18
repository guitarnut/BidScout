package com.gnut.bidscout.service.history;

import com.gnut.bidscout.db.AuctionDao;
import com.gnut.bidscout.model.AuctionRecord;
import com.gnut.bidscout.service.inventory.ImpressionService;
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
    private final ImpressionService impressionService;

    @Autowired
    public AuctionRecordService(
            AuctionDao auctionDao,
            UserAccountStatisticsService statisticsService,
            AccountService accountService,
            ImpressionService impressionService
    ) {
        this.auctionDao = auctionDao;
        this.statisticsService = statisticsService;
        this.accountService = accountService;
        this.impressionService = impressionService;
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
            impressionService.deleteAuctionRecordImpression(a.getBidRequestId());
            accountService.deleteAuctionRecord(getAccount(auth));
        }
    }

    public List<AuctionRecord> deleteAll(Authentication auth) {
        auctionDao.deleteAllByOwner(getAccount(auth));
        List<AuctionRecord> records = auctionDao.findAllByOwner(getAccount(auth));
        records.forEach(r->{
            impressionService.deleteAuctionRecordImpression(r.getBidRequestId());
            accountService.deleteAuctionRecord(getAccount(auth));
        });
        return Collections.emptyList();
    }

    private String getAccount(Authentication auth) {
        return auth.getAuthorities().iterator().next().getAuthority();
    }
}
