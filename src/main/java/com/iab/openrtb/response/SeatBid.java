package com.iab.openrtb.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;

/**
 * A bid response can contain multiple {@link SeatBid} objects, each on behalf
 * of a different bidder seat and each containing one or more individual bids.
 * If multiple impressions are presented in the request, the {@code group}
 * attribute can be used to specify if a seat is willing to accept any
 * impressions that it can win (default) or if it is only interested in winning
 * any if it can win them all as a group.
 */

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SeatBid {

    /**
     * Array of 1+ Bid objects (Section 4.2.3) each related to an impression.
     * Multiple bids can relate to the same impression.
     * (required)
     */
    List<Bid> bid;

    /**
     * ID of the buyer seat (e.g., advertiser, agency) on whose behalf this bid
     * is made.
     */
    String seat;

    /**
     * 0 = impressions can be won individually; 1 = impressions must be won or
     * lost as a group.
     */
    int group;

    /** Placeholder for bidder-specific extensions to OpenRTB. */
    ObjectNode ext;

    public SeatBid(){}

    public List<Bid> getBid() {
        return bid;
    }

    public void setBid(List<Bid> bid) {
        this.bid = bid;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public ObjectNode getExt() {
        return ext;
    }

    public void setExt(ObjectNode ext) {
        this.ext = ext;
    }
}
