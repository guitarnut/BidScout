package com.gnut.bidscout.values;

public enum BidRequestError {
    PARSE_ERROR("Parsing error"),
    NO_IMPRESSION("No impression object"),
    NO_BANNER(""),
    NO_SIZE(""),
    NO_USER_AGENT(""),
    NO_IP(""),
    NO_BUYERUID(""),
    NO_USER_ID(""),
    NO_IDFA(""),
    NO_DOMAIN(""),
    NO_PAGE(""),
    NO_PUBLISHER(""),
    NO_BID_ID("");

    private String v;

    BidRequestError(String value) {
        this.v = value;
    }

    public String value() {
        return v;
    }
}
