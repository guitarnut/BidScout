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

    @Autowired
    public ImpressionService(ImpressionDao impressionDao) {
        this.impressionDao = impressionDao;
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
        record.setCb(Long.valueOf(cb));
        record.setCp(Float.valueOf(cp));
        record.setBidPrice(Float.valueOf(bidPrice));
        record.setUrl(request.getRequestURL().toString());

        impressionDao.save(record);
        response.setStatus(204);
    }
}
