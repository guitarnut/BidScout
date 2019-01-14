package com.gnut.bidscout.model;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

public class Campaign {
    @Id
    private String id;
    private String owner;
    private boolean enabled;
    private String publisher;
    private String cid;
    private String name;
    private String seat;
    private Limits limits;
    private Flags flags;
    private List<String> creatives = new ArrayList<>();
    private Requirements requirements;
    private Statistics statistics;
    private String nurl;
    private long impressionExpiry;
    private boolean syncUsers;

    public void copyValues(Campaign c) {
        this.name = c.getName();
        this.enabled = c.isEnabled();
        this.publisher = c.getPublisher();
        this.cid = c.getCid();
        this.seat = c.getSeat();
        this.flags = c.getFlags();
        this.creatives = c.getCreatives();
        this.limits = c.getLimits();
        this.requirements = c.getRequirements();
        this.statistics = c.getStatistics();
        this.nurl = c.getNurl();
        this.impressionExpiry = c.getImpressionExpiry();
        this.syncUsers = c.isSyncUsers();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
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

    public List<String> getCreatives() {
        return creatives;
    }

    public void setCreatives(List<String> creatives) {
        this.creatives = creatives;
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
