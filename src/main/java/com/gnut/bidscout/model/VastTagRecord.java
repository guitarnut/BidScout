package com.gnut.bidscout.model;

import org.springframework.data.annotation.Id;

public class VastTagRecord {
    @Id
    private String id;
    private String vastName;
    private String tagRequestId;
    private long requestTimestamp;
    private long responseTimestamp;
    private String markup;
    private String cookies;
    private String host;
    private String xForwardedFor;
    private String ip;
    private String userAgent;
    private String owner;

    public VastTagRecord() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVastName() {
        return vastName;
    }

    public void setVastName(String vastName) {
        this.vastName = vastName;
    }

    public String getTagRequestId() {
        return tagRequestId;
    }

    public void setTagRequestId(String tagRequestId) {
        this.tagRequestId = tagRequestId;
    }

    public long getRequestTimestamp() {
        return requestTimestamp;
    }

    public void setRequestTimestamp(long requestTimestamp) {
        this.requestTimestamp = requestTimestamp;
    }

    public long getResponseTimestamp() {
        return responseTimestamp;
    }

    public void setResponseTimestamp(long responseTimestamp) {
        this.responseTimestamp = responseTimestamp;
    }

    public String getMarkup() {
        return markup;
    }

    public void setMarkup(String markup) {
        this.markup = markup;
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

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }
}
