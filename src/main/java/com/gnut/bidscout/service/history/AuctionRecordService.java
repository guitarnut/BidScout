package com.gnut.bidscout.service.history;

import com.gnut.bidscout.db.AuctionDao;
import com.gnut.bidscout.model.AuctionRecord;
import com.gnut.bidscout.service.user.AccountService;
import com.gnut.bidscout.service.user.UserAccountStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    public Map<String, AuctionRecord> getListOfAllRecords(String account) {
        final Map<String, AuctionRecord> results = new HashMap<>();
        List<AuctionRecord> records = auctionDao.findAllByOwner(account);
        if (records != null) {
            records.forEach(r -> {
                final AuctionRecord auctionRecord = new AuctionRecord();
                auctionRecord.setBidRequestId(r.getBidRequestId());
                auctionRecord.setRequestTimestamp(r.getRequestTimestamp());
                results.put(r.getId(), auctionRecord);
            });
        }
        return results;
    }

    public List<AuctionRecord> getAllAuctionRecords() {
        final Map<String, AuctionRecord> results = new HashMap<>();
        return auctionDao.findAll();
    }

    public AuctionRecord view(String id) {
        Optional<AuctionRecord> a = auctionDao.findById(id);
        if (a.isPresent()) {
            return a.get();
        } else {
            return null;
        }
    }

    public void delete(String id) {
        Optional<AuctionRecord> a = auctionDao.findById(id);
        if (a.isPresent()) {
            auctionDao.deleteById(id);
            accountService.deleteAuctionRecord();
        }
    }

    public void deleteBid(String account, String id) {
        AuctionRecord record = auctionDao.findFirstByIdAndOwner(id, account);
        if (record != null) {
            auctionDao.deleteById(id);
            statisticsService.removeAuctionRecord(account);
        }
    }

    public void deleteAllBids(String account) {
        List<AuctionRecord> allRecords = auctionDao.findAllByOwner(account);
        if (allRecords != null) {
            allRecords.forEach(r -> {
                auctionDao.deleteById(r.getId());
            });
            statisticsService.removeAllAuctionRecords(account);
        }
    }
}
