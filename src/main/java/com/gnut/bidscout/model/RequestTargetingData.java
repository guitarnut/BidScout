package com.gnut.bidscout.model;

import java.util.List;

public class RequestTargetingData {
    public enum Platform {
        DESKTOP, MOBILE, INAPP, CTV
    }

    private String publisher;
    private Platform platform;
    private String publisherId;
    private String domain;
    private String bundleId;
    private String idfa;
    private String buyeruid;
    private List<Integer> widths;
    private List<Integer> heights;
    private List<String> badv;
    private List<Integer> battr;
    private List<Integer> btype;
    private boolean secure;
    private float bidfloor;
    private boolean userMatch;

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public String getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getBundleId() {
        return bundleId;
    }

    public void setBundleId(String bundleId) {
        this.bundleId = bundleId;
    }

    public String getIdfa() {
        return idfa;
    }

    public void setIdfa(String idfa) {
        this.idfa = idfa;
    }

    public String getBuyeruid() {
        return buyeruid;
    }

    public void setBuyeruid(String buyeruid) {
        this.buyeruid = buyeruid;
    }

    public List<Integer> getWidths() {
        return widths;
    }

    public void setWidths(List<Integer> widths) {
        this.widths = widths;
    }

    public List<Integer> getHeights() {
        return heights;
    }

    public void setHeights(List<Integer> heights) {
        this.heights = heights;
    }

    public boolean isSecure() {
        return secure;
    }

    public void setSecure(boolean secure) {
        this.secure = secure;
    }

    public float getBidfloor() {
        return bidfloor;
    }

    public void setBidfloor(float bidfloor) {
        this.bidfloor = bidfloor;
    }

    public List<String> getBadv() {
        return badv;
    }

    public void setBadv(List<String> badv) {
        this.badv = badv;
    }

    public List<Integer> getBattr() {
        return battr;
    }

    public void setBattr(List<Integer> battr) {
        this.battr = battr;
    }

    public List<Integer> getBtype() {
        return btype;
    }

    public void setBtype(List<Integer> btype) {
        this.btype = btype;
    }

    public boolean isUserMatch() {
        return userMatch;
    }

    public void setUserMatch(boolean userMatch) {
        this.userMatch = userMatch;
    }
}
