package com.gnut.bidscout.service;


import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Component
public class SyncService {
    private static final String COOKIE_NAME = "bscu_id";
    private static final String COOKIE_IMP_COUNT_NAME = "bscu_i";
    private static final String COOKIE_SYNC_COUNT_NAME = "bscu_s";
    private static final int COOKIE_TTL = 3600;

    public void sync(HttpServletRequest request, HttpServletResponse response) {
        updateUserSyncCount(request);
        // a valid user should always have all three
        if (!isValidUserRefreshedExistingCookies(request, response)) {
            addUserCookies(response);
        }
        response.setStatus(204);
    }

    public void addUserCookies(HttpServletResponse response) {
        response.addCookie(generateCookie(COOKIE_NAME, UUID.randomUUID().toString()));
        response.addCookie(generateCookie(COOKIE_IMP_COUNT_NAME, String.valueOf(0)));
        response.addCookie(generateCookie(COOKIE_SYNC_COUNT_NAME, String.valueOf(0)));
        response.setStatus(204);
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
        c.setDomain("localhost");
        c.setPath("/");
        return c;
    }
}
