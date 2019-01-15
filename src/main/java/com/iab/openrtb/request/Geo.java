package com.iab.openrtb.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Map;


/**
 * This object encapsulates various methods for specifying a geographic location.
 * When subordinate to a {@link Device} object, it indicates the location of the
 * device which can also be interpreted as the user’s current location. When
 * subordinate to a {@link User} object, it indicates the location of the user’s
 * home base (i.e., not necessarily their current location).
 * <p>The {@code lat}/{code lon} attributes should only be passed if they
 * conform to the accuracy depicted in the {@code type} attribute. For example,
 * the centroid of a geographic region such as postal code should not be passed.
 */

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Geo {

    /** Latitude from -90.0 to +90.0, where negative is south. */
    Float lat;

    /** Longitude from -180.0 to +180.0, where negative is west. */
    Float lon;

    /**
     * Source of location data; recommended when passing lat/lon.
     * Refer to List 5.20.
     */
    Integer type;

    /**
     * Estimated location accuracy in meters; recommended when lat/lon are
     * specified and derived from a device’s location services (i.e., type = 1).
     * Note that this is the accuracy as reported from the device. Consult OS
     * specific documentation (e.g., Android, iOS) for exact interpretation.
     */
    Integer accuracy;

    /**
     * Number of seconds since this geolocation fix was established.
     * Note that devices may cache location data across multiple fetches.
     * Ideally, this value should be from the time the actual fix was taken.
     */
    Integer lastfix;

    /**
     * Service or provider used to determine geolocation from IP address if
     * applicable (i.e., type = 2). Refer to List 5.23.
     */
    Integer ipservice;

    /** Country code using ISO-3166-1-alpha-3. */
    String country;

    /** Region code using ISO-3166-2; 2-letter state code if USA. */
    String region;

    /**
     * Region of a country using FIPS 10-4 notation. While OpenRTB supports this
     * attribute, it has been withdrawn by NIST in 2008.
     */
    String regionfips104;

    /**
     * Google metro code; similar to but not exactly Nielsen DMAs.
     * See Appendix A for a link to the codes.
     */
    String metro;

    /**
     * City using United Nations Code for Trade & Transport Locations.
     * See Appendix A for a link to the codes.
     */
    String city;

    /** Zip or postal code. */
    String zip;

    /** Local time as the number +/- of minutes from UTC. */
    Integer utcoffset;

    /** Placeholder for exchange-specific extensions to OpenRTB. */
    Map<Object, Object> ext;

    public Geo(){}

    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Float getLon() {
        return lon;
    }

    public void setLon(Float lon) {
        this.lon = lon;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Integer accuracy) {
        this.accuracy = accuracy;
    }

    public Integer getLastfix() {
        return lastfix;
    }

    public void setLastfix(Integer lastfix) {
        this.lastfix = lastfix;
    }

    public Integer getIpservice() {
        return ipservice;
    }

    public void setIpservice(Integer ipservice) {
        this.ipservice = ipservice;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRegionfips104() {
        return regionfips104;
    }

    public void setRegionfips104(String regionfips104) {
        this.regionfips104 = regionfips104;
    }

    public String getMetro() {
        return metro;
    }

    public void setMetro(String metro) {
        this.metro = metro;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public Integer getUtcoffset() {
        return utcoffset;
    }

    public void setUtcoffset(Integer utcoffset) {
        this.utcoffset = utcoffset;
    }

    public Map<Object, Object> getExt() {
        return ext;
    }

    public void setExt(Map<Object, Object> ext) {
        this.ext = ext;
    }
}
