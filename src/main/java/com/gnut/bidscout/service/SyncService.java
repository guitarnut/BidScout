package com.gnut.bidscout.service;


import com.gnut.bidscout.db.CampaignDao;
import com.gnut.bidscout.db.CreativeDao;
import com.gnut.bidscout.model.Campaign;
import com.gnut.bidscout.model.Creative;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Component
public class SyncService {
    private static final String COOKIE_NAME = "__user";
    private static final String COOKIE_IMP_COUNT_NAME = "__imp";
    private static final String COOKIE_SYNC_COUNT_NAME = "__sync";
    private static final int COOKIE_TTL = 3600;

    @Value("${service.values.host.cookie}")
    private String HOST;

    private final CampaignDao campaignDao;
    private final CreativeDao creativeDao;

    @Autowired
    public SyncService(CampaignDao campaignDao, CreativeDao creativeDao) {
        this.campaignDao = campaignDao;
        this.creativeDao = creativeDao;
    }

    public void sync(HttpServletRequest request, HttpServletResponse response, String id, String campaignId, String creativeId) {
        final Campaign campaign = campaignDao.findByIdAndOwner(campaignId, id);
        final Creative creative = creativeDao.findByIdAndOwner(creativeId, id);

        if (campaign == null || creative == null) {
            response.setStatus(404);
        } else if (campaign.getOwner().equals(id) && creative.getOwner().equals(id)) {
            if (campaign.isSyncUsers() || creative.isSyncUsers()) {
                updateUserSyncCount(request);
                // a valid user should always have all three
                if (!isValidUserRefreshedExistingCookies(request, response)) {
                    addUserCookies(response);
                }
            }
            response.setStatus(204);
        }
    }

    public void addUserCookies(HttpServletResponse response) {
        response.addCookie(generateCookie(COOKIE_NAME, UUID.randomUUID().toString()));
        response.addCookie(generateCookie(COOKIE_IMP_COUNT_NAME, String.valueOf(0)));
        response.addCookie(generateCookie(COOKIE_SYNC_COUNT_NAME, String.valueOf(0)));
    }

    public void updateUserImpressionCount(HttpServletRequest request) {
        if (cookiesPresent(request)) {
            for (Cookie c : request.getCookies()) {
                if (c.getName().equals(COOKIE_IMP_COUNT_NAME)) {
                    incrementCookieValue(c);
                }
            }
        }
    }

    private void updateUserSyncCount(HttpServletRequest request) {
        if (cookiesPresent(request)) {
            for (Cookie c : request.getCookies()) {
                if (c.getName().equals(COOKIE_SYNC_COUNT_NAME)) {
                    incrementCookieValue(c);
                }
            }
        }
    }

    private void incrementCookieValue(Cookie c) {
        int count = 0;
        try {
            count = Integer.valueOf(c.getValue());
        } catch (NumberFormatException ex) {
            //
        }
        count++;
        c.setValue(String.valueOf(count));
    }

    public String getUserCookieValue(HttpServletRequest request) {
        if (cookiesPresent(request)) {
            for (Cookie c : request.getCookies()) {
                if (c.getName().equals(COOKIE_NAME)) {
                    return c.getValue();
                }
            }
        }
        return "";
    }

    public String getSyncCookieValue(HttpServletRequest request) {
        if (cookiesPresent(request)) {
            for (Cookie c : request.getCookies()) {
                if (c.getName().equals(COOKIE_NAME)) {
                    return c.getValue();
                }
            }
        }
        return "";
    }

    public String getImpCookieValue(HttpServletRequest request) {
        if (cookiesPresent(request)) {
            for (Cookie c : request.getCookies()) {
                if (c.getName().equals(COOKIE_NAME)) {
                    return c.getValue();
                }
            }
        }
        return "";
    }

    public boolean isValidUserRefreshedExistingCookies(HttpServletRequest request, HttpServletResponse response) {
        boolean userExists = false;
        boolean syncExists = false;
        boolean impExists = false;

        if (cookiesPresent(request)) {
            for (Cookie c : request.getCookies()) {
                if (c.getName().equals(COOKIE_NAME)) {
                    userExists = true;
                    response.addCookie(generateCookie(COOKIE_NAME, c.getValue()));
                } else if (c.getName().equals(COOKIE_SYNC_COUNT_NAME)) {
                    syncExists = true;
                    response.addCookie(generateCookie(COOKIE_SYNC_COUNT_NAME, c.getValue()));
                } else if (c.getName().equals(COOKIE_IMP_COUNT_NAME)) {
                    impExists = true;
                    response.addCookie(generateCookie(COOKIE_IMP_COUNT_NAME, c.getValue()));
                }
            }
        }

        return userExists && syncExists && impExists;
    }

    private boolean cookiesPresent(HttpServletRequest request) {
        return request.getCookies() != null && request.getCookies().length > 0;
    }

    private Cookie generateCookie(String n, String v) {
        Cookie c = new Cookie(n, v);
        c.setMaxAge(COOKIE_TTL);
        c.setDomain(HOST);
        c.setPath("/");
        return c;
    }
}
