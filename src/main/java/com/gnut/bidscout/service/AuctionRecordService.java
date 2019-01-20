package com.gnut.bidscout.service;

import com.gnut.bidscout.db.AuctionDao;
import com.gnut.bidscout.model.AuctionRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AuctionRecordService {
    private final AuctionDao auctionDao;

    @Autowired
    public AuctionRecordService(AuctionDao auctionDao) {
        this.auctionDao = auctionDao;
    }

    public Map<String, String> getListOfAllRecords(String account) {
        final Map<String, String> results = new HashMap<>();
        List<AuctionRecord> records = auctionDao.findAllByOwner(account);
        if (records != null) {
            records.forEach(r -> {
                results.put(r.getId(), r.getBidRequestId());
            });
        }
        return results;
    }

    public void deleteBid(String account, String id) {
        AuctionRecord record = auctionDao.findFirstByIdAndOwner(id, account);
        if (record != null) {
            auctionDao.deleteById(id);
        }
    }

    public void deleteAllBids(String account) {
        List<AuctionRecord> allRecords = auctionDao.findAllByOwner(account);
        if (allRecords != null) {
            allRecords.forEach(r -> {
                auctionDao.deleteById(r.getId());
            });
        }
    }
}
