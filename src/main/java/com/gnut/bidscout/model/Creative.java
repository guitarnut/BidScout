package com.gnut.bidscout.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

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
    private int w;
    private int h;
    private boolean enabled;
    private List<String> iabCategories;
    private List<Integer> attr;
    private List<Integer> btype;
    private List<String> mimes;
    private String adId;
    private String crid;
    private List<String> adDomain;
    private String creativeUrl;
    private String xml;
    private String xmlId;
    private String adm;
    private Limits limits;
    private Requirements requirements;
    private Statistics statistics;
    private float minBid;
    private float maxBid;
    private boolean syncUsers;
    private int bidFrequency;

    public void copyValues(Creative c) {
        this.name = c.getName();
        this.type = c.getType();
        this.w = c.getW();
        this.h = c.getH();
        this.enabled = c.isEnabled();
        this.iabCategories = c.getIabCategories();
        this.attr = c.getAttr();
        this.btype = c.getBtype();
        this.mimes = c.getMimes();
        this.adId = c.getAdId();
        this.crid = c.getCrid();
        this.adDomain = c.getAdDomain();
        this.creativeUrl = c.getCreativeUrl();
        this.xml = c.getXml();
        this.xmlId = c.getXmlId();
        this.adm = c.getAdm();
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

    public List<String> getIabCategories() {
        return iabCategories;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
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

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

    public String getXmlId() {
        return xmlId;
    }

    public void setXmlId(String xmlId) {
        this.xmlId = xmlId;
    }

    public String getAdm() {
        return adm;
    }

    public void setAdm(String adm) {
        this.adm = adm;
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
