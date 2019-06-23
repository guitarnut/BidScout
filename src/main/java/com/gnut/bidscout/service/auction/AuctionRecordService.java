package com.gnut.bidscout.service.auction;

import com.gnut.bidscout.db.AuctionDao;
import com.gnut.bidscout.model.AuctionRecord;
import com.gnut.bidscout.service.user.UserAccountStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AuctionRecordService {
    private final AuctionDao auctionDao;
    private final UserAccountStatisticsService statisticsService;
    @Autowired
    public AuctionRecordService(
            AuctionDao auctionDao,
            UserAccountStatisticsService statisticsService
    ) {
        this.auctionDao = auctionDao;
        this.statisticsService = statisticsService;
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
