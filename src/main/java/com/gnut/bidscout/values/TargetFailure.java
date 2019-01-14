package com.gnut.bidscout.values;

public enum TargetFailure {
    CREATIVES_ALIGNED("No creatives aligned"),
    SIZE_MATCH("No size match"),
    MAX_BID_BELOW_FLOOR("Max bid amount is below floor"),
    MAX_BID_BELOW_FLOOR_AND_DEAL_FLOOR("Max bid amount is below floor and deal floor"),
    BADV("Blocked ad domain"),
    BATTR("Blocked attribute"),
    DEAL_ID("No matching deal id"),
    USER_MATCH("No user match"),
    FLIGHT_ENDED("Flight dates ended"),
    FLIGHT_NOT_STARTED("Flight dates not started"),
    PLATFORM_IN_APP("InApp not allowed"),
    PLATFORM_MOBILE("Mobile not allowed"),
    PLATFORM_DESKTOP("Desktop not allowed"),
    PLATFORM_CTV("CTV not allowed"),
    BUNDLE_WHITELIST("Bundle whitelist not matched"),
    BUNDLE_BLACKLIST("Bundle blacklist match"),
    DOMAIN_WHITELIST("Domain whitelist not matched"),
    DOMAIN_BLACKLIST("Domain blacklist matched"),
    PUBLISHER_WHITELIST("Publisher whitelist not matched"),
    PUBLISHER_BLACKLIST("Publisher blacklist matched"),
    IAB_CATEGORY("IAB category blocked"),
    REQUEST_LIMIT_REACHED("Request limit reached"),
    BID_LIMIT_REACHED("Bid limit reached"),
    IMPRESSION_LIMIT_REACHED("Impression limit reached"),
    REVENUE_LIMIT_REACHED("Revenue limit reached");

    private String v;

    TargetFailure(String v) {
        this.v = v;
    }

    public String value() {
        return v;
    }

}
