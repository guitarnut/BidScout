package com.gnut.bidscout.service;

import com.gnut.bidscout.db.ClickDao;
import com.gnut.bidscout.model.ClickRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class ClickService {
    private final ClickDao clickDao;

    @Autowired
    public ClickService(ClickDao clickDao) {
        this.clickDao = clickDao;
    }

    public void handleRequest(
            HttpServletRequest request,
            HttpServletResponse response,
            String bid,
            String campaign,
            String cb
    ) {
        ClickRecord record = new ClickRecord();
        record.setClickTimestamp(System.currentTimeMillis());
        record.setIp(request.getRemoteUser());
        record.setUserAgent(request.getHeader("User-Agent"));
        record.setCookies(request.getHeader("Cookie"));
        record.setxForwardedFor(request.getHeader("X-Forwarded-For"));
        record.setHost(request.getHeader("Host"));
        record.setBidRequestId(bid);
        record.setCampaign(campaign);
        record.setCb(Long.valueOf(cb));
        record.setUrl(request.getRequestURL().toString());

        clickDao.save(record);
        response.setStatus(204);
    }
}
