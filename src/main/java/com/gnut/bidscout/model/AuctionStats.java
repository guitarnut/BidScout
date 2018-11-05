package com.gnut.bidscout.model;

public class AuctionStats {
    private String id;
    private long bids;
    private float bidPriceTotal;
    private long requests;
    private long impressions;
    private float revenueTotal;
    private float ecpm;
    private long nbr;

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

    public float getBidPriceTotal() {
        return bidPriceTotal;
    }

    public void setBidPriceTotal(float bidPriceTotal) {
        this.bidPriceTotal = bidPriceTotal;
    }

    public long getRequests() {
        return requests;
    }

    public void setRequests(long requests) {
        this.requests = requests;
    }

    public long getImpressions() {
        return impressions;
    }

    public void setImpressions(long impressions) {
        this.impressions = impressions;
    }

    public float getRevenueTotal() {
        return revenueTotal;
    }

    public void setRevenueTotal(float revenueTotal) {
        this.revenueTotal = revenueTotal;
    }

    public float getEcpm() {
        return ecpm;
    }

    public void setEcpm(float ecpm) {
        this.ecpm = ecpm;
    }

    public long getNbr() {
        return nbr;
    }

    public void setNbr(long nbr) {
        this.nbr = nbr;
    }
}
