package com.gnut.bidscout.service;

import com.gnut.bidscout.cache.ImpressionCache;
import com.gnut.bidscout.db.ImpressionDao;
import com.gnut.bidscout.model.ImpressionRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component
public class ImpressionService {
    private final ImpressionDao impressionDao;
    private final CampaignService campaignService;
    private final CreativeService creativeService;
    private final SyncService syncService;
    private final ImpressionCache impressionCache;

    @Autowired
    public ImpressionService(
            ImpressionDao impressionDao,
            CampaignService campaignService,
            CreativeService creativeService,
            SyncService syncService,
            ImpressionCache impressionCache
    ) {
        this.impressionDao = impressionDao;
        this.campaignService = campaignService;
        this.creativeService = creativeService;
        this.syncService = syncService;
        this.impressionCache = impressionCache;
    }

    public List<ImpressionRecord> getImpressions(String id, String bidId) {
        return impressionDao.findAllByOwnerAndBidRequestId(id, bidId);
    }

    public void handleRequest(
            String id,
            HttpServletRequest request,
            HttpServletResponse response,
            String bid,
            String campaign,
            String creative,
            String bidPrice,
            String cp,
            String cb
    ) {
        ImpressionRecord record = new ImpressionRecord();
        record.setOwner(id);
        record.setImpressionTimestamp(System.currentTimeMillis());
        record.setIp(request.getRemoteAddr());
        record.setUserAgent(request.getHeader("User-Agent"));
        record.setCookies(request.getHeader("Cookie"));
        record.setxForwardedFor(request.getHeader("X-Forwarded-For"));
        record.setHost(request.getHeader("Host"));
        record.setBidRequestId(bid);
        record.setCampaign(campaign);
        record.setCreative(creative);
        record.setBidPrice(Float.valueOf(bidPrice));
        record.setUrl(request.getRequestURL().toString());
        record.setUserCookie(syncService.getUserCookieValue(request));
        record.setSyncCookie(syncService.getSyncCookieValue(request));
        record.setImpressionCookie(syncService.getImpCookieValue(request));

        try {
            record.setCb(Long.valueOf(cb));
        } catch (NumberFormatException ex) {
            // Todo: Handle invalid
            record.setCb(-1);
            response.setStatus(204);
            return;
        }

        try {
            record.setCp(Float.valueOf(cp));
            syncService.updateUserImpressionCount(request);
        } catch (NumberFormatException ex) {
            record.setCp(0);
        }

        record.setValidKnownUser(syncService.isValidUserRefreshedExistingCookies(request, response));
        boolean validImpression = true;

        if (impressionCache.addImpression(record.getBidRequestId()) > 1) {
            record.setDuplicate(true);
            campaignService.incrementDuplicateImpression(record.getCampaign());
            creativeService.incrementDuplicateImpression(record.getCreative());
            validImpression = false;
        }

        if (record.getCp() <= 0) {
            campaignService.incrementInvalidImpression(record.getCampaign());
            creativeService.incrementInvalidImpression(record.getCreative());
            validImpression = false;
        }

        if (!campaignService.isValidImpressionTTL(record.getCampaign(), record.getCb())) {
            campaignService.incrementExpiredImpression(record.getCampaign());
            creativeService.incrementExpiredImpression(record.getCreative());
            record.setExpired(true);
            validImpression = false;
        }

        if (validImpression) {
            campaignService.incrementImpression(record.getCampaign(), record.getCp());
            creativeService.incrementImpression(record.getCreative(), record.getCp());
        }

        impressionDao.save(record);
        response.setStatus(204);
    }
}
