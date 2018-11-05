package com.gnut.bidscout.model;

import java.util.List;

public class Creative {
    private String id;
    private String name;
    private int w;
    private int h;
    private boolean active;
    private String type;
    private List<String> iabCategories;
    private List<Integer> attr;
    private List<Integer> btype;
    private List<String> mimes;
    private String adId;
    private String crid;
    private List<String> adDomain;
    private String creativeUrl;
    private Limits limits;
    private Requirements requirements;
    private Statistics statistics;
    private float minBid;
    private float maxBid;
    private boolean syncUsers;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getIabCategories() {
        return iabCategories;
    }

    public void setIabCategories(List<String> iabCategories) {
        this.iabCategories = iabCategories;
    }

    public List<Integer> getAttr() {
        return attr;
    }

    public void setAttr(List<Integer> attr) {
        this.attr = attr;
    }

    public List<Integer> getBtype() {
        return btype;
    }

    public void setBtype(List<Integer> btype) {
        this.btype = btype;
    }

    public List<String> getMimes() {
        return mimes;
    }

    public void setMimes(List<String> mimes) {
        this.mimes = mimes;
    }

    public String getAdId() {
        return adId;
    }

    public void setAdId(String adId) {
        this.adId = adId;
    }

    public String getCrid() {
        return crid;
    }

    public void setCrid(String crid) {
        this.crid = crid;
    }

    public List<String> getAdDomain() {
        return adDomain;
    }

    public void setAdDomain(List<String> adDomain) {
        this.adDomain = adDomain;
    }

    public String getCreativeUrl() {
        return creativeUrl;
    }

    public void setCreativeUrl(String creativeUrl) {
        this.creativeUrl = creativeUrl;
    }

    public Limits getLimits() {
        return limits;
    }

    public void setLimits(Limits limits) {
        this.limits = limits;
    }

    public Requirements getRequirements() {
        return requirements;
    }

    public void setRequirements(Requirements requirements) {
        this.requirements = requirements;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }

    public float getMinBid() {
        return minBid;
    }

    public void setMinBid(float minBid) {
        this.minBid = minBid;
    }

    public float getMaxBid() {
        return maxBid;
    }

    public void setMaxBid(float maxBid) {
        this.maxBid = maxBid;
    }

    public boolean isSyncUsers() {
        return syncUsers;
    }

    public void setSyncUsers(boolean syncUsers) {
        this.syncUsers = syncUsers;
    }
}
