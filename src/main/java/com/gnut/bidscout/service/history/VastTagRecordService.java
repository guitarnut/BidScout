package com.gnut.bidscout.service.history;

import com.gnut.bidscout.db.VastTagRecordDao;
import com.gnut.bidscout.model.AuctionRecord;
import com.gnut.bidscout.model.VastTagRecord;
import com.gnut.bidscout.service.user.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class VastTagRecordService {

    private final VastTagRecordDao vastTagRecordDao;
    private final AccountService accountService;

    @Autowired
    public VastTagRecordService(
            VastTagRecordDao vastTagRecordDao,
            AccountService accountService
    ) {
        this.vastTagRecordDao = vastTagRecordDao;
        this.accountService = accountService;
    }

    public VastTagRecord getVastTagRecord(String id) {
        return vastTagRecordDao.findFirstById(id);
    }

    public List<VastTagRecord> getAllVastTagRecords() {
        return vastTagRecordDao.findAll();
    }

    public VastTagRecord view(String id) {
        Optional<VastTagRecord> a = vastTagRecordDao.findById(id);
        if (a.isPresent()) {
            return a.get();
        } else {
            return null;
        }
    }

    public void delete(String id) {
        Optional<VastTagRecord> a = vastTagRecordDao.findById(id);
        if (a.isPresent()) {
            vastTagRecordDao.deleteById(id);
            accountService.deleteVastTagRecord();
        }
    }
}
