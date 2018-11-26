package com.gnut.bidscout.model;

import org.springframework.data.annotation.Id;

import java.util.Set;

public class EventRecord {
    public enum Event {
        MUTE(""),
        UNMUTE(""),
        PAUSE(""),
        RESUME(""),
        REWIND(""),
        SKIP(""),
        PLAYER_EXPANDED(""),
        PLAYER_COLLAPSE(""),
        START(""),
        FIRST_QUARTILE(""),
        MIDPOINT(""),
        THIRD_QUARTILE(""),
        COMPLETE(""),
        ACCEPT_INVITATION_LINEAR(""),
        TIME_SPENT_VIEWING(""),
        OTHER_AD_INTERACTION(""),
        PROGRESS(""),
        CREATIVE_VIEW(""),
        ACCEPT_INVITATION(""),
        AD_EXPAND(""),
        AD_COLLAPSE(""),
        MINIMIZE(""),
        CLOSE(""),
        OVERLAY_VIEW_DURATION("");

        private String value;

        Event(String v) {
            this.value = v;
        }

        public String value() {
            return value;
        }

    }

    @Id
    private String id;
    private String owner;
    private Event event;
    private String bidRequestId;
    private String campaign;
    private String creative;
    private long cb;
    private String ip;
    private String userAgent;
    private long eventTimestamp;
    private String url;
    private String cookies;
    private String host;
    private String xForwardedFor;

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

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
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

    public long getEventTimestamp() {
        return eventTimestamp;
    }

    public void setEventTimestamp(long eventTimestamp) {
        this.eventTimestamp = eventTimestamp;
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
}
