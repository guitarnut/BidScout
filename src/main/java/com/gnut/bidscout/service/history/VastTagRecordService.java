package com.gnut.bidscout.service.history;

import com.gnut.bidscout.db.VastTagRecordDao;
import com.gnut.bidscout.model.VastTagRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VastTagRecordService {

    private final VastTagRecordDao vastTagRecordDao;

    @Autowired
    public VastTagRecordService(VastTagRecordDao vastTagRecordDao) {
        this.vastTagRecordDao = vastTagRecordDao;
    }

    public VastTagRecord getVastTagRecord(String id) {
        return vastTagRecordDao.findFirstById(id);
    }

    public List<VastTagRecord> getAllVastTagRecords() {
        return vastTagRecordDao.findAll();
    }
}
