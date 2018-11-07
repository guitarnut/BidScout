package com.gnut.bidscout.model;

public class Statistics {
    private String id;
    private long bids;
    private long nbr;
    private long impressions;
    private long duplicateImpressions;
    private long expiredImpressions;
    private long invalidImpressions;
    private float revenue;
    private float ecpm;
    private long requests;
    private float bidPriceTotal;
    private long clicks;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getBids() {
        return bids;
    }

    public void setBids(long bids) {
        this.bids = bids;
    }

    public long getNbr() {
        return nbr;
    }

    public void setNbr(long nbr) {
        this.nbr = nbr;
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

    public long getInvalidImpressions() {
        return invalidImpressions;
    }

    public void setInvalidImpressions(long invalidImpressions) {
        this.invalidImpressions = invalidImpressions;
    }

    public float getRevenue() {
        return revenue;
    }

    public void setRevenue(float revenue) {
        this.revenue = revenue;
    }

    public float getEcpm() {
        return ecpm;
    }

    public void setEcpm(float ecpm) {
        this.ecpm = ecpm;
    }

    public long getRequests() {
        return requests;
    }

    public void setRequests(long requests) {
        this.requests = requests;
    }

    public float getBidPriceTotal() {
        return bidPriceTotal;
    }

    public void setBidPriceTotal(float bidPriceTotal) {
        this.bidPriceTotal = bidPriceTotal;
    }

    public long getClicks() {
        return clicks;
    }

    public void setClicks(long clicks) {
        this.clicks = clicks;
    }
}
