package com.gnut.bidscout.model;

import org.springframework.data.annotation.Id;

import java.util.Set;

public class ImpressionRecord {
    @Id
    private String id;
    private String bidRequestId;
    private float clearingPrice;
    private String campaign;
    private long cb;
    private String ip;
    private String userAgent;
    private long impressionTimestamp;
    private String url;
    private String cookies;
    private String host;
    private String xForwardedFor;
    private Set<String> violations;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBidRequestId() {
        return bidRequestId;
    }

    public void setBidRequestId(String bidRequestId) {
        this.bidRequestId = bidRequestId;
    }

    public float getClearingPrice() {
        return clearingPrice;
    }

    public void setClearingPrice(float clearingPrice) {
        this.clearingPrice = clearingPrice;
    }

    public String getCampaign() {
        return campaign;
    }

    public void setCampaign(String campaign) {
        this.campaign = campaign;
    }

    public long getCb() {
        return cb;
    }

    public void setCb(long cb) {
        this.cb = cb;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public long getImpressionTimestamp() {
        return impressionTimestamp;
    }

    public void setImpressionTimestamp(long impressionTimestamp) {
        this.impressionTimestamp = impressionTimestamp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCookies() {
        return cookies;
    }

    public void setCookies(String cookies) {
        this.cookies = cookies;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getxForwardedFor() {
        return xForwardedFor;
    }

    public void setxForwardedFor(String xForwardedFor) {
        this.xForwardedFor = xForwardedFor;
    }

    public Set<String> getViolations() {
        return violations;
    }

    public void setViolations(Set<String> violations) {
        this.violations = violations;
    }

    @Override
    public String toString() {
        return "ImpressionRecord{" +
                "id='" + id + '\'' +
                ", bidRequestId='" + bidRequestId + '\'' +
                ", clearingPrice=" + clearingPrice +
                ", campaign='" + campaign + '\'' +
                ", cb=" + cb +
                ", ip='" + ip + '\'' +
                ", userAgent='" + userAgent + '\'' +
                ", impressionTimestamp=" + impressionTimestamp +
                ", url='" + url + '\'' +
                ", cookies='" + cookies + '\'' +
                ", host='" + host + '\'' +
                ", xForwardedFor='" + xForwardedFor + '\'' +
                ", violations=" + violations +
                '}';
    }
}
