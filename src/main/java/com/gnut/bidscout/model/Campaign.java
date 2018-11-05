package com.gnut.bidscout.model;

import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class Campaign {
    @Id
    private String id;
    private List<String> publishers;
    private String cid;
    private String name;
    private String seat;
    private Limits limits;
    private Flags flags;
    private Set<Creative> creatives;
    private AuctionStats auctionStats;
    private Requirements requirements;
    private Statistics statistics;
    private String nurl;
    private long impressionExpiry;
    private boolean syncUsers;
    private Date startDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getPublishers() {
        return publishers;
    }

    public void setPublishers(List<String> publishers) {
        this.publishers = publishers;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public Limits getLimits() {
        return limits;
    }

    public void setLimits(Limits limits) {
        this.limits = limits;
    }

    public Flags getFlags() {
        return flags;
    }

    public void setFlags(Flags flags) {
        this.flags = flags;
    }

    public Set<Creative> getCreatives() {
        return creatives;
    }

    public void setCreatives(Set<Creative> creatives) {
        this.creatives = creatives;
    }

    public AuctionStats getAuctionStats() {
        return auctionStats;
    }

    public void setAuctionStats(AuctionStats auctionStats) {
        this.auctionStats = auctionStats;
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

    public String getNurl() {
        return nurl;
    }

    public void setNurl(String nurl) {
        this.nurl = nurl;
    }

    public long getImpressionExpiry() {
        return impressionExpiry;
    }

    public void setImpressionExpiry(long impressionExpiry) {
        this.impressionExpiry = impressionExpiry;
    }

    public boolean isSyncUsers() {
        return syncUsers;
    }

    public void setSyncUsers(boolean syncUsers) {
        this.syncUsers = syncUsers;
    }
}
