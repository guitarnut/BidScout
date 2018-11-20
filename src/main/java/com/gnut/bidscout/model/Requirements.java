package com.gnut.bidscout.model;

import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Requirements {
    private String id;
    private boolean userMatch;
    private boolean secure;
    private List<String> publisherWhitelist;
    private List<String> domainWhitelist;
    private List<String> bundleWhitelist;
    private List<String> publisherBlacklist;
    private List<String> domainBlacklist;
    private List<String> bundleBlacklist;
    @Field
    private List<String> dealIds = Collections.emptyList();
    private boolean mobile;
    private boolean desktop;
    private boolean inapp;
    private boolean ctv;
    private Date startDate;
    private Date endDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public List<String> getPublisherWhitelist() {
        return publisherWhitelist;
    }

    public void setPublisherWhitelist(List<String> publisherWhitelist) {
        this.publisherWhitelist = publisherWhitelist;
    }

    public List<String> getDomainWhitelist() {
        return domainWhitelist;
    }

    public void setDomainWhitelist(List<String> domainWhitelist) {
        this.domainWhitelist = domainWhitelist;
    }

    public List<String> getBundleWhitelist() {
        return bundleWhitelist;
    }

    public void setBundleWhitelist(List<String> bundleWhitelist) {
        this.bundleWhitelist = bundleWhitelist;
    }

    public List<String> getPublisherBlacklist() {
        return publisherBlacklist;
    }

    public void setPublisherBlacklist(List<String> publisherBlacklist) {
        this.publisherBlacklist = publisherBlacklist;
    }

    public List<String> getDomainBlacklist() {
        return domainBlacklist;
    }

    public void setDomainBlacklist(List<String> domainBlacklist) {
        this.domainBlacklist = domainBlacklist;
    }

    public List<String> getBundleBlacklist() {
        return bundleBlacklist;
    }

    public void setBundleBlacklist(List<String> bundleBlacklist) {
        this.bundleBlacklist = bundleBlacklist;
    }

    public List<String> getDealIds() {
        return dealIds;
    }

    public void setDealIds(List<String> dealIds) {
        this.dealIds = dealIds;
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
