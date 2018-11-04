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
        // a valid user should always have all three
        if (!isValidUserRefreshedExistingCookies(request)) {
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

    public void updateUserImpressionCount(HttpServletRequest request, HttpServletResponse response) {
        if (cookiesPresent(request)) {
            for (Cookie c : request.getCookies()) {
                if (c.getName().equals(COOKIE_SYNC_COUNT_NAME)) {
                    int impCount = Integer.valueOf(c.getValue());
                    impCount ++;
                    c.setValue(String.valueOf(impCount));
                    c.setMaxAge(COOKIE_TTL);
                    response.addCookie(c);
                }
            }
        }
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

    private boolean isValidUserRefreshedExistingCookies(HttpServletRequest request) {
        boolean userExists = false;
        boolean syncExists = false;
        boolean impExists = false;

        if (cookiesPresent(request)) {
            for (Cookie c : request.getCookies()) {
                if (c.getName().equals(COOKIE_NAME)) {
                    userExists = true;
                    c.setMaxAge(COOKIE_TTL);
                } else if (c.getName().equals(COOKIE_SYNC_COUNT_NAME)) {
                    syncExists = true;
                    int syncCount = Integer.valueOf(c.getValue());
                    syncCount ++;
                    c.setValue(String.valueOf(syncCount));
                    c.setMaxAge(COOKIE_TTL);
                } else if (c.getName().equals(COOKIE_IMP_COUNT_NAME)) {
                    impExists = true;
                    c.setMaxAge(COOKIE_TTL);
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
        return c;
    }
}
