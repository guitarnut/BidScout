package com.gnut.bidscout.service;

import com.gnut.bidscout.db.ImpressionDao;
import com.gnut.bidscout.model.ImpressionRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class ImpressionService {
    private final ImpressionDao impressionDao;
    private final SyncService syncService;

    @Autowired
    public ImpressionService(
            ImpressionDao impressionDao,
            SyncService syncService
    ) {
        this.impressionDao = impressionDao;
        this.syncService = syncService;
    }

    public void handleRequest(
            HttpServletRequest request,
            HttpServletResponse response,
            String bid,
            String campaign,
            String bidPrice,
            String cp,
            String cb
    ) {
        ImpressionRecord record = new ImpressionRecord();
        record.setImpressionTimestamp(System.currentTimeMillis());
        record.setIp(request.getRemoteUser());
        record.setUserAgent(request.getHeader("User-Agent"));
        record.setCookies(request.getHeader("Cookie"));
        record.setxForwardedFor(request.getHeader("X-Forwarded-For"));
        record.setHost(request.getHeader("Host"));
        record.setBidRequestId(bid);
        record.setCampaign(campaign);
        record.setBidPrice(Float.valueOf(bidPrice));
        record.setUrl(request.getRequestURL().toString());
        record.setUserCookie(syncService.getUserCookieValue(request));
        record.setSyncCookie(syncService.getSyncCookieValue(request));
        record.setImpressionCookie(syncService.getImpCookieValue(request));

        try {
            record.setCb(Long.valueOf(cb));
        } catch (NumberFormatException ex) {
            record.setCb(-1);
        }

        try {
            record.setCp(Float.valueOf(cp));
            syncService.updateUserImpressionCount(request);
        } catch (NumberFormatException ex) {
            record.setCp(0);
        }

        record.setValidKnownUser(syncService.isValidUserRefreshedExistingCookies(request, response));
        impressionDao.save(record);
        response.setStatus(204);
    }
}
