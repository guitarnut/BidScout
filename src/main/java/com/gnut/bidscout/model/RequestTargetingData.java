package com.gnut.bidscout.model;

import com.iab.openrtb.request.Deal;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RequestTargetingData {
    public enum Platform {
        DESKTOP, MOBILE, INAPP, CTV
    }

    private String parent;
    private String publisher;
    private Platform platform;
    private String publisherId;
    private String domain;
    private String bundleId;
    private String idfa;
    private String buyeruid;
    private List<Deal> dealIds = new ArrayList<>();
    private List<Integer> widths = new ArrayList<>();
    private List<Integer> heights = new ArrayList<>();
    private List<String> badv = new ArrayList<>();
    private List<String> bcat = new ArrayList<>();
    private List<Integer> battr = new ArrayList<>();
    private List<Integer> btype = new ArrayList<>();
    private boolean secure;
    private float bidfloor;
    private boolean userMatch;
    private String targetingFailureReason;

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

    public List<Deal> getDealIds() {
        return dealIds;
    }

    public void setDealIds(List<Deal> dealIds) {
        this.dealIds = dealIds;
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

    public List<String> getBcat() {
        return bcat;
    }

    public void setBcat(List<String> bcat) {
        this.bcat = bcat;
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

    public String getTargetingFailureReason() {
        return targetingFailureReason;
    }

    public void setTargetingFailureReason(String targetingFailureReason) {
        this.targetingFailureReason = targetingFailureReason;
    }
}
