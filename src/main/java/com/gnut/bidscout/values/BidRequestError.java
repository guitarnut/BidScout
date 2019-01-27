package com.gnut.bidscout.values;

public enum BidRequestError {

    BID_REQUEST_ID_MISSING("Bid request ID is null or empty"),
    BID_REQUEST_ID_NOT_UNIQUE("A record for this bid request ID already exists"),

    NO_BID_ID("Bid id missing"),

    PARSE_ERROR("Parsing error"),

    NO_IMPRESSION("No impression object"),

    NO_USER("No user object"),
    NO_BUYERUID("No buyer id"),
    NO_USER_ID("No user id"),

    NO_PUBLISHER("No publisher object"),
    NO_DOMAIN("No domain value"),
    NO_PAGE("No page value"),

    NO_BANNER("No banner object"),
    NO_SIZE("No size(s) specified"),

    NO_DEVICE("No device object"),
    NO_USER_AGENT("No user agent"),
    NO_IP("No device IP address"),
    NO_IDFA("No IDFA value"),

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
