package com.gnut.bidscout.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Creative implements Advertisment {
    public enum Type {
       DISPLAY, VAST, VPAID
    }
    @Id
    private String id;
    private String owner;
    private String name;
    private Type type;
    private boolean enabled;
    private Limits limits = new Limits();
    private Requirements requirements = new Requirements();
    private Statistics statistics = new Statistics();
    private float minBid;
    private float maxBid;
    private boolean syncUsers;
    private int bidFrequency;

    public void copyValues(Creative c) {
        this.name = c.getName();
        this.type = c.getType();
        this.enabled = c.isEnabled();
        this.limits = c.getLimits();
        this.requirements = c.getRequirements();
        this.statistics = c.getStatistics();
        this.minBid = c.getMinBid();
        this.maxBid = c.getMaxBid();
        this.syncUsers = c.isSyncUsers();
        this.bidFrequency = c.getBidFrequency();
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
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

    public int getBidFrequency() {
        return bidFrequency;
    }

    public void setBidFrequency(int bidFrequency) {
        this.bidFrequency = bidFrequency;
    }
}
