package com.gnut.bidscout.model;

import org.springframework.data.annotation.Id;

import java.util.Set;

public class ClickRecord {
    @Id
    private String id;
    private String bidRequestId;
    private String campaign;
    private String creative;
    private long cb;
    private String ip;
    private String userAgent;
    private long clickTimestamp;
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

    public String getCampaign() {
        return campaign;
    }

    public void setCampaign(String campaign) {
        this.campaign = campaign;
    }

    public String getCreative() {
        return creative;
    }

    public void setCreative(String creative) {
        this.creative = creative;
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

    public long getClickTimestamp() {
        return clickTimestamp;
    }

    public void setClickTimestamp(long clickTimestamp) {
        this.clickTimestamp = clickTimestamp;
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
}
