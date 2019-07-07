package com.gnut.bidscout.model;

public class UserAccountStatistics {
    private String id;
    private String user;
    private int campaigns;
    private int campaignsLimit = 10;
    private int creatives;
    private int creativesLimit = 10;
    private int vast;
    private int vastLimit = 10;
    private int auctionRecords;
    private int auctionRecordsLimit = 50;
    private int vastRecords;
    private int vastRecordsLimit = 50;
    private int vastTagRequests;
    private int vastTagRequestsLimit = 100;
    private int vastTagRequestsOverage;
    private int vastTagRequestsOverageLimit = 10;
    private int bidRequests;
    private int bidRequestsLimit = 100;
    private int bidRequestsOverage;
    private int bidRequestsOverageLimit = 10;
    private long periodEnd;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getCampaigns() {
        return campaigns;
    }

    public void setCampaigns(int campaigns) {
        this.campaigns = campaigns;
    }

    public int getCampaignsLimit() {
        return campaignsLimit;
    }

    public void setCampaignsLimit(int campaignsLimit) {
        this.campaignsLimit = campaignsLimit;
    }

    public int getCreatives() {
        return creatives;
    }

    public void setCreatives(int creatives) {
        this.creatives = creatives;
    }

    public int getCreativesLimit() {
        return creativesLimit;
    }

    public void setCreativesLimit(int creativesLimit) {
        this.creativesLimit = creativesLimit;
    }

    public int getVast() {
        return vast;
    }

    public void setVast(int vast) {
        this.vast = vast;
    }

    public int getVastLimit() {
        return vastLimit;
    }

    public void setVastLimit(int vastLimit) {
        this.vastLimit = vastLimit;
    }

    public int getAuctionRecords() {
        return auctionRecords;
    }

    public void setAuctionRecords(int auctionRecords) {
        this.auctionRecords = auctionRecords;
    }

    public int getAuctionRecordsLimit() {
        return auctionRecordsLimit;
    }

    public void setAuctionRecordsLimit(int auctionRecordsLimit) {
        this.auctionRecordsLimit = auctionRecordsLimit;
    }

    public int getVastRecords() {
        return vastRecords;
    }

    public void setVastRecords(int vastRecords) {
        this.vastRecords = vastRecords;
    }

    public int getVastRecordsLimit() {
        return vastRecordsLimit;
    }

    public void setVastRecordsLimit(int vastRecordsLimit) {
        this.vastRecordsLimit = vastRecordsLimit;
    }

    public int getVastTagRequests() {
        return vastTagRequests;
    }

    public void setVastTagRequests(int vastTagRequests) {
        this.vastTagRequests = vastTagRequests;
    }

    public int getVastTagRequestsLimit() {
        return vastTagRequestsLimit;
    }

    public void setVastTagRequestsLimit(int vastTagRequestsLimit) {
        this.vastTagRequestsLimit = vastTagRequestsLimit;
    }

    public int getVastTagRequestsOverage() {
        return vastTagRequestsOverage;
    }

    public void setVastTagRequestsOverage(int vastTagRequestsOverage) {
        this.vastTagRequestsOverage = vastTagRequestsOverage;
    }

    public int getVastTagRequestsOverageLimit() {
        return vastTagRequestsOverageLimit;
    }

    public void setVastTagRequestsOverageLimit(int vastTagRequestsOverageLimit) {
        this.vastTagRequestsOverageLimit = vastTagRequestsOverageLimit;
    }

    public int getBidRequests() {
        return bidRequests;
    }

    public void setBidRequests(int bidRequests) {
        this.bidRequests = bidRequests;
    }

    public int getBidRequestsLimit() {
        return bidRequestsLimit;
    }

    public void setBidRequestsLimit(int bidRequestsLimit) {
        this.bidRequestsLimit = bidRequestsLimit;
    }

    public int getBidRequestsOverage() {
        return bidRequestsOverage;
    }

    public void setBidRequestsOverage(int bidRequestsOverage) {
        this.bidRequestsOverage = bidRequestsOverage;
    }

    public int getBidRequestsOverageLimit() {
        return bidRequestsOverageLimit;
    }

    public void setBidRequestsOverageLimit(int bidRequestsOverageLimit) {
        this.bidRequestsOverageLimit = bidRequestsOverageLimit;
    }

    public long getPeriodEnd() {
        return periodEnd;
    }

    public void setPeriodEnd(long periodEnd) {
        this.periodEnd = periodEnd;
    }
}
