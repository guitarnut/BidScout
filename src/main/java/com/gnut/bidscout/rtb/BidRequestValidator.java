package com.gnut.bidscout.rtb;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class BidRequestValidator {

    public enum Violation {
        NO_IMPRESSION(""),
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

        Violation (String value) {

        }
    }

    public Set<Violation> validateBidRequest() {
        final Set<Violation> violations = new HashSet<>();
        return violations;
    }

}
