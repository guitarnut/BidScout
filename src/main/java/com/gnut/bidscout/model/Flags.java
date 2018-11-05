package com.gnut.bidscout.model;

public class Flags {
    private String id;
    private boolean ipSpam;
    private boolean clearingPriceFraud;
    private boolean expiredImpressions;
    private boolean duplicateImpressions;
    private boolean badRequests;
    private boolean userMatch;
    private boolean blacklistSite;
    private boolean blacklistBundle;
    private boolean blacklistDomain;
    private boolean whitelistSite;
    private boolean whitelistBundle;
    private boolean whitelistDomain;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isIpSpam() {
        return ipSpam;
    }

    public void setIpSpam(boolean ipSpam) {
        this.ipSpam = ipSpam;
    }

    public boolean isClearingPriceFraud() {
        return clearingPriceFraud;
    }

    public void setClearingPriceFraud(boolean clearingPriceFraud) {
        this.clearingPriceFraud = clearingPriceFraud;
    }

    public boolean isExpiredImpressions() {
        return expiredImpressions;
    }

    public void setExpiredImpressions(boolean expiredImpressions) {
        this.expiredImpressions = expiredImpressions;
    }

    public boolean isDuplicateImpressions() {
        return duplicateImpressions;
    }

    public void setDuplicateImpressions(boolean duplicateImpressions) {
        this.duplicateImpressions = duplicateImpressions;
    }

    public boolean isBadRequests() {
        return badRequests;
    }

    public void setBadRequests(boolean badRequests) {
        this.badRequests = badRequests;
    }

    public boolean isUserMatch() {
        return userMatch;
    }

    public void setUserMatch(boolean userMatch) {
        this.userMatch = userMatch;
    }

    public boolean isBlacklistSite() {
        return blacklistSite;
    }

    public void setBlacklistSite(boolean blacklistSite) {
        this.blacklistSite = blacklistSite;
    }

    public boolean isBlacklistBundle() {
        return blacklistBundle;
    }

    public void setBlacklistBundle(boolean blacklistBundle) {
        this.blacklistBundle = blacklistBundle;
    }

    public boolean isBlacklistDomain() {
        return blacklistDomain;
    }

    public void setBlacklistDomain(boolean blacklistDomain) {
        this.blacklistDomain = blacklistDomain;
    }

    public boolean isWhitelistSite() {
        return whitelistSite;
    }

    public void setWhitelistSite(boolean whitelistSite) {
        this.whitelistSite = whitelistSite;
    }

    public boolean isWhitelistBundle() {
        return whitelistBundle;
    }

    public void setWhitelistBundle(boolean whitelistBundle) {
        this.whitelistBundle = whitelistBundle;
    }

    public boolean isWhitelistDomain() {
        return whitelistDomain;
    }

    public void setWhitelistDomain(boolean whitelistDomain) {
        this.whitelistDomain = whitelistDomain;
    }
}
