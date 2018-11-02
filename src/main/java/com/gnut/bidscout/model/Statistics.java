package com.gnut.bidscout.model;

public class Statistics {
    private long id;
    private long bids;
    private long impressions;
    private long duplicateImpressions;
    private long expiredImpressions;
    private long revenue;
    private float ecpm;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getBids() {
        return bids;
    }

    public void setBids(long bids) {
        this.bids = bids;
    }

    public long getImpressions() {
        return impressions;
    }

    public void setImpressions(long impressions) {
        this.impressions = impressions;
    }

    public long getDuplicateImpressions() {
        return duplicateImpressions;
    }

    public void setDuplicateImpressions(long duplicateImpressions) {
        this.duplicateImpressions = duplicateImpressions;
    }

    public long getExpiredImpressions() {
        return expiredImpressions;
    }

    public void setExpiredImpressions(long expiredImpressions) {
        this.expiredImpressions = expiredImpressions;
    }

    public long getRevenue() {
        return revenue;
    }

    public void setRevenue(long revenue) {
        this.revenue = revenue;
    }

    public float getEcpm() {
        return ecpm;
    }

    public void setEcpm(float ecpm) {
        this.ecpm = ecpm;
    }
}
