package com.iab.openrtb.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;
import java.util.Map;

/**
 * This object contains information known or derived about the human user of the
 * device (i.e., the audience for advertising). The user {@code id} is an
 * exchange artifact and may be subject to rotation or other privacy policies.
 * However, this user ID must be stable long enough to serve reasonably as the
 * basis for frequency capping and retargeting.
 */

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {

    /**
     * Exchange-specific ID for the user. At least one of id or buyeruid is
     * recommended.
     */
    String id;

    /**
     * Buyer-specific ID for the user as mapped by the exchange for the buyer.
     * At least one of buyeruid or id is recommended.
     */
    String buyeruid;

    /** Year of birth as a 4-digit integer. */
    Integer yob;

    /**
     * Gender, where “M” = male, “F” = female, “O” = known to be other (i.e.,
     * omitted is unknown).
     */
    String gender;

    String language;

    /** Comma separated list of keywords, interests, or intent. */
    List<String> keywords;

    /**
     * Optional feature to pass bidder data that was set in the exchange’s
     * cookie. The string must be in base85 cookie safe characters and be in any
     * format. Proper JSON encoding must be used to include “escaped” quotation
     * marks.
     */
    String customdata;

    /**
     * Location of the user’s home base defined by a Geo object (Section
     * 3.2.19). This is not necessarily their current location.
     */
    Geo geo;

    /**
     * Additional user data. Each Data object (Section 3.2.21) represents a
     * different data source.
     */
    List<Data> data;

    /** Placeholder for exchange-specific extensions to OpenRTB. */
    Map<Object, Object> ext;

    public User(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBuyeruid() {
        return buyeruid;
    }

    public void setBuyeruid(String buyeruid) {
        this.buyeruid = buyeruid;
    }

    public Integer getYob() {
        return yob;
    }

    public void setYob(Integer yob) {
        this.yob = yob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public String getCustomdata() {
        return customdata;
    }

    public void setCustomdata(String customdata) {
        this.customdata = customdata;
    }

    public Geo getGeo() {
        return geo;
    }

    public void setGeo(Geo geo) {
        this.geo = geo;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public Map<Object, Object> getExt() {
        return ext;
    }

    public void setExt(Map<Object, Object> ext) {
        this.ext = ext;
    }
}
