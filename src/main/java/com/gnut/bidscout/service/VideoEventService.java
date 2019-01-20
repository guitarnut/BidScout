package com.gnut.bidscout.service;

import com.gnut.bidscout.db.EventRecordDao;
import com.gnut.bidscout.db.VastTagRecordDao;
import com.gnut.bidscout.model.EventRecord;
import com.gnut.bidscout.model.VastTagRecord;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class VideoEventService {
    private final EventRecordDao eventRecordDao;
    private final VastTagRecordDao vastTagRecordDao;

    public VideoEventService(EventRecordDao eventRecordDao, VastTagRecordDao vastTagRecordDao) {
        this.eventRecordDao = eventRecordDao;
        this.vastTagRecordDao = vastTagRecordDao;
    }

    public void recordVastTagEvent(String id, String event, long cb, HttpServletRequest request) {
        final VastTagRecord vastTagRecord = vastTagRecordDao.findFirstById(id);
        if (vastTagRecord != null) {
            final EventRecord eventRecord = new EventRecord();
            eventRecord.setEvent(event);
            eventRecord.setTagRequestId(id);
            eventRecord.setEventTimestamp(System.currentTimeMillis());
            eventRecord.setCb(cb);
            eventRecord.setIp(request.getRemoteAddr());
            eventRecord.setUserAgent(request.getHeader("User-Agent"));
            eventRecord.setCookies(request.getHeader("Cookie"));
            eventRecord.setxForwardedFor(request.getHeader("X-Forwarded-For"));
            eventRecord.setHost(request.getHeader("Host"));
            eventRecord.setUrl(request.getRequestURI());

            eventRecordDao.save(eventRecord);
        }
    }
}
