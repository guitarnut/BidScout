package com.iab.openrtb.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;
import java.util.Map;

/**
 * This object constitutes a specific deal that was struck <em>a priori</em>
 * between a buyer and a seller. Its presence with the {@link Pmp} collection
 * indicates that this impression is available under the terms of that deal.
 * Refer to Section 7.3 for more details.
 */

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Deal {

    /** A unique identifier for the direct deal. (required) */
    String id;

    /** Minimum bid for this impression expressed in CPM. */
    float bidfloor;

    /**
     * Currency specified using ISO-4217 alpha codes. This may be different from
     * bid currency returned by bidder if this is allowed by the exchange.
     */
    String bidfloorcur;

    /**
     * Optional override of the overall auction type of the bid request,
     * where 1 = First Price, 2 = Second Price Plus, 3 = the value passed in
     * {@code bidfloor} is the agreed upon deal price. Additional auction types
     * can be defined by the exchange.
     */
    Integer at;

    /**
     * Whitelist of buyer seats (e.g., advertisers, agencies) allowed to bid on
     * this deal. IDs of seats and the buyer’s customers to which they refer
     * must be coordinated between bidders and the exchange a priori.
     * Omission implies no seat restrictions.
     */
    List<String> wseat;

    /**
     * Array of advertiser domains (e.g., advertiser.com) allowed to bid on this
     * deal. Omission implies no advertiser restrictions.
     */
    List<String> wadomain;

    /** Placeholder for exchange-specific extensions to OpenRTB. */
    Map<Object, Object> ext;

    public Deal(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getBidfloor() {
        return bidfloor;
    }

    public void setBidfloor(float bidfloor) {
        this.bidfloor = bidfloor;
    }

    public String getBidfloorcur() {
        return bidfloorcur;
    }

    public void setBidfloorcur(String bidfloorcur) {
        this.bidfloorcur = bidfloorcur;
    }

    public Integer getAt() {
        return at;
    }

    public void setAt(Integer at) {
        this.at = at;
    }

    public List<String> getWseat() {
        return wseat;
    }

    public void setWseat(List<String> wseat) {
        this.wseat = wseat;
    }

    public List<String> getWadomain() {
        return wadomain;
    }

    public void setWadomain(List<String> wadomain) {
        this.wadomain = wadomain;
    }

    public Map<Object, Object> getExt() {
        return ext;
    }

    public void setExt(Map<Object, Object> ext) {
        this.ext = ext;
    }
}
