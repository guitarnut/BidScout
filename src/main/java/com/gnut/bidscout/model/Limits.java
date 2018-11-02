package com.gnut.bidscout.model;

public class Limits {
    private long id;
    private long requestLimit;
    private long bidRate;
    private long bidLimit;
    private long impressionLimit;
    private float revenueLimit;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRequestLimit() {
        return requestLimit;
    }

    public void setRequestLimit(long requestLimit) {
        this.requestLimit = requestLimit;
    }

    public long getBidRate() {
        return bidRate;
    }

    public void setBidRate(long bidRate) {
        this.bidRate = bidRate;
    }

    public long getBidLimit() {
        return bidLimit;
    }

    public void setBidLimit(long bidLimit) {
        this.bidLimit = bidLimit;
    }

    public long getImpressionLimit() {
        return impressionLimit;
    }

    public void setImpressionLimit(long impressionLimit) {
        this.impressionLimit = impressionLimit;
    }

    public float getRevenueLimit() {
        return revenueLimit;
    }

    public void setRevenueLimit(float revenueLimit) {
        this.revenueLimit = revenueLimit;
    }
}
