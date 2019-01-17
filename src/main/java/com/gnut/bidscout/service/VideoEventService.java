package com.gnut.bidscout.service;

import com.gnut.bidscout.db.EventRecordDao;
import com.gnut.bidscout.db.VastTagRecordDao;
import com.gnut.bidscout.model.EventRecord;
import com.gnut.bidscout.model.VastTagRecord;
import org.springframework.stereotype.Component;

@Component
public class VideoEventService {
    private final EventRecordDao eventRecordDao;
    private final VastTagRecordDao vastTagRecordDao;

    public VideoEventService(EventRecordDao eventRecordDao, VastTagRecordDao vastTagRecordDao) {
        this.eventRecordDao = eventRecordDao;
        this.vastTagRecordDao = vastTagRecordDao;
    }

    public void recordEvent(String id, String event, long cb) {
        final VastTagRecord vastTagRecord = vastTagRecordDao.findFirstById(id);
        if (vastTagRecord != null) {
            final EventRecord eventRecord = new EventRecord();
            eventRecord.setEvent(event);
            eventRecord.setTagRequestId(id);
            eventRecord.setEventTimestamp(System.currentTimeMillis());
            eventRecord.setCb(cb);
            eventRecordDao.save(eventRecord);
        }
    }
}
