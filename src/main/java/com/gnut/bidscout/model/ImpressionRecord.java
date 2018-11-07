package com.gnut.bidscout.model;

import org.springframework.data.annotation.Id;

import java.util.Set;

public class ImpressionRecord {
    @Id
    private String id;
    private String bidRequestId;
    private String campaign;
    private String creative;
    private long cb;
    private String ip;
    private String userAgent;
    private long impressionTimestamp;
    private float cp;
    private float bidPrice;
    private String url;
    private String cookies;
    private String host;
    private String xForwardedFor;
    private Set<String> violations;
    private String userCookie;
    private String impressionCookie;
    private String syncCookie;
    private boolean isValidKnownUser;
    private boolean expired;
    private boolean duplicate;

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

    public long getImpressionTimestamp() {
        return impressionTimestamp;
    }

    public void setImpressionTimestamp(long impressionTimestamp) {
        this.impressionTimestamp = impressionTimestamp;
    }

    public float getCp() {
        return cp;
    }

    public void setCp(float cp) {
        this.cp = cp;
    }

    public float getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(float bidPrice) {
        this.bidPrice = bidPrice;
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

    public String getUserCookie() {
        return userCookie;
    }

    public void setUserCookie(String userCookie) {
        this.userCookie = userCookie;
    }

    public String getImpressionCookie() {
        return impressionCookie;
    }

    public void setImpressionCookie(String impressionCookie) {
        this.impressionCookie = impressionCookie;
    }

    public String getSyncCookie() {
        return syncCookie;
    }

    public void setSyncCookie(String syncCookie) {
        this.syncCookie = syncCookie;
    }

    public boolean isValidKnownUser() {
        return isValidKnownUser;
    }

    public void setValidKnownUser(boolean validKnownUser) {
        isValidKnownUser = validKnownUser;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public boolean isDuplicate() {
        return duplicate;
    }

    public void setDuplicate(boolean duplicate) {
        this.duplicate = duplicate;
    }
}
