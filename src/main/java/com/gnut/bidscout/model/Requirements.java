package com.gnut.bidscout.model;

import java.util.Date;

public class Requirements {
    private long id;
    private boolean userMatch;
    private boolean secure;
    private String publisherWhitelist;
    private String domainWhitelist;
    private String bundleWhitelist;
    private String publisherBlacklist;
    private String domainBlacklist;
    private String bundleBlacklist;
    private boolean mobile;
    private boolean desktop;
    private boolean inapp;
    private boolean ctv;
    private Date startDate;
    private Date endDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isUserMatch() {
        return userMatch;
    }

    public void setUserMatch(boolean userMatch) {
        this.userMatch = userMatch;
    }

    public boolean isSecure() {
        return secure;
    }

    public void setSecure(boolean secure) {
        this.secure = secure;
    }

    public String getPublisherWhitelist() {
        return publisherWhitelist;
    }

    public void setPublisherWhitelist(String publisherWhitelist) {
        this.publisherWhitelist = publisherWhitelist;
    }

    public String getDomainWhitelist() {
        return domainWhitelist;
    }

    public void setDomainWhitelist(String domainWhitelist) {
        this.domainWhitelist = domainWhitelist;
    }

    public String getBundleWhitelist() {
        return bundleWhitelist;
    }

    public void setBundleWhitelist(String bundleWhitelist) {
        this.bundleWhitelist = bundleWhitelist;
    }

    public String getPublisherBlacklist() {
        return publisherBlacklist;
    }

    public void setPublisherBlacklist(String publisherBlacklist) {
        this.publisherBlacklist = publisherBlacklist;
    }

    public String getDomainBlacklist() {
        return domainBlacklist;
    }

    public void setDomainBlacklist(String domainBlacklist) {
        this.domainBlacklist = domainBlacklist;
    }

    public String getBundleBlacklist() {
        return bundleBlacklist;
    }

    public void setBundleBlacklist(String bundleBlacklist) {
        this.bundleBlacklist = bundleBlacklist;
    }

    public boolean isMobile() {
        return mobile;
    }

    public void setMobile(boolean mobile) {
        this.mobile = mobile;
    }

    public boolean isDesktop() {
        return desktop;
    }

    public void setDesktop(boolean desktop) {
        this.desktop = desktop;
    }

    public boolean isInapp() {
        return inapp;
    }

    public void setInapp(boolean inapp) {
        this.inapp = inapp;
    }

    public boolean isCtv() {
        return ctv;
    }

    public void setCtv(boolean ctv) {
        this.ctv = ctv;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
