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
    private final CampaignService campaignService;
    private final CreativeService creativeService;

    @Autowired
    public ClickService(ClickDao clickDao, CampaignService campaignService, CreativeService creativeService) {
        this.clickDao = clickDao;
        this.campaignService = campaignService;
        this.creativeService = creativeService;
    }

    public void handleRequest(
            HttpServletRequest request,
            HttpServletResponse response,
            String bid,
            String campaign,
            String creative,
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
        record.setCreative(creative);
        record.setCb(Long.valueOf(cb));
        record.setUrl(request.getRequestURL().toString());

        campaignService.incrementClick(record.getCampaign());
        creativeService.incrementClick(record.getCreative());
        clickDao.save(record);
        response.setStatus(204);
    }
}
