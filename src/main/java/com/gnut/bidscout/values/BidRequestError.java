package com.gnut.bidscout.values;

public enum BidRequestError {

    BID_REQUEST_ID_MISSING("Bid request ID is null or empty"),
    BID_REQUEST_ID_NOT_UNIQUE("A record for this bid request ID already exists"),

    NO_BID_ID("Bid id missing"),

    PARSE_ERROR("Parsing error"),

    NO_IMPRESSION("No impression object"),
    NO_IMPRESSION_ID("No impression ID"),
    NO_IMPRESSION_BID_FLOOR("No impression bidfloor"),

    NO_APP_OR_SITE("No app or site object"),
    APP_AND_SITE("App and site object"),

    NO_APP_DOMAIN("No app domain"),
    INVALID_APP_DOMAIN("Invalid app domain"),
    NO_APP_ID("No app ID"),
    NO_APP_BUNDLE("No app bundle"),
    NO_APP_STORE_URL("No app store URL"),

    NO_SITE_DOMAIN("No site domain"),
    INVALID_SITE_DOMAIN("Invalid site domain"),
    NO_SITE_ID("No site ID"),

    NO_USER("No user object"),
    NO_BUYERUID("No buyer id"),
    NO_USER_ID("No user id"),

    NO_PUBLISHER("No publisher object"),
    NO_PUBLISHER_ID("No publisher ID"),
    NO_DOMAIN("No publisher domain value"),
    NO_PAGE("No publisher page value"),

    NO_BANNER_OR_VIDEO("No banner or video object"),
    BANNER_AND_VIDEO("Banner and video object"),

    NO_BANNER_ID("No banner ID"),
    INVALID_BANNER_SIZE("Invalid banner size"),

    NO_DEVICE("No device object"),
    NO_DEVICE_USER_AGENT("No device user agent"),
    NO_DEVICE_IP("No device IP address"),
    DEVICE_LOCALHOST_IP("Localhost IP address used"),
    NO_DEVICE_IDFA("No device IDFA value"),

    NO_GEO_OBJECT("No device geo object"),

    NO_CURRENCY("No currency specified"),

    NO_CAMPAIGN("No campaign exists for this endpoint"),
    CAMPAIGN_NOT_ENABLED("The campaign for this enpoint is not enabled");

    private String v;

    BidRequestError(String value) {
        this.v = value;
    }

    public String value() {
        return v;
    }
}
