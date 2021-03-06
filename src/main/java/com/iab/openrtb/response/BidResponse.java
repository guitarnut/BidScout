package com.iab.openrtb.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;

/**
 * This object is the top-level bid response object (i.e., the unnamed outer
 * JSON object). The {@code id} attribute is a reflection of the bid request ID
 * for logging purposes. Similarly, {@code bidid} is an optional response
 * tracking ID for bidders. If specified, it can be included in the subsequent
 * win notice call if the bidder wins. At least one {@code seatbid} object is
 * required, which contains at least one bid for an impression. Other attributes
 * are optional.
 * <p>To express a “no-bid”, the options are to return an empty response with
 * HTTP 204. Alternately if the bidder wishes to convey to the exchange a reason
 * for not bidding, just a {@link BidResponse} object is returned with a reason
 * code in the {@code nbr} attribute.
 */

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BidResponse {

    /**
     * ID of the bid request to which this is a response.
     * (required)
     */
    String id;

    /** Array of seatbid objects; 1+ required if a bid is to be made. */
    List<SeatBid> seatbid;

    /** Bidder generated response ID to assist with logging/tracking. */
    String bidid;

    /** Bid currency using ISO-4217 alpha codes. */
    String cur;

    /**
     * Optional feature to allow a bidder to set data in the exchange’s cookie.
     * The string must be in base85 cookie safe characters and be in any format.
     * Proper JSON encoding must be used to include “escaped” quotation marks.
     */
    String customdata;

    /** Reason for not bidding. Refer to List 5.24. */
    Integer nbr;

    /** Placeholder for bidder-specific extensions to OpenRTB. */
    Map<String, Object> ext;

    public BidResponse(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<SeatBid> getSeatbid() {
        return seatbid;
    }

    public void setSeatbid(List<SeatBid> seatbid) {
        this.seatbid = seatbid;
    }

    public String getBidid() {
        return bidid;
    }

    public void setBidid(String bidid) {
        this.bidid = bidid;
    }

    public String getCur() {
        return cur;
    }

    public void setCur(String cur) {
        this.cur = cur;
    }

    public String getCustomdata() {
        return customdata;
    }

    public void setCustomdata(String customdata) {
        this.customdata = customdata;
    }

    public Integer getNbr() {
        return nbr;
    }

    public void setNbr(Integer nbr) {
        this.nbr = nbr;
    }

    public Map<String, Object> getExt() {
        return ext;
    }

    public void setExt(Map<String, Object> ext) {
        this.ext = ext;
    }

    public static final Comparator<BidResponse> COMPARATOR = (left, right) -> {
        if (isNull(left))
            return -1;
        if (left.getSeatbid().isEmpty())
            return -1;
        if (left.getSeatbid().get(0).getBid().isEmpty())
            return -1;
        if (isNull(right))
            return -1;
        if (right.getSeatbid().isEmpty())
            return 1;
        if (right.getSeatbid().get(0).getBid().isEmpty())
            return 1;
        return left.getSeatbid().get(0).getBid().get(0).getPrice()
                   .compareTo(right.getSeatbid().get(0).getBid().get(0).getPrice());
    };
}
