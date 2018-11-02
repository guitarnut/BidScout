package com.iab.openrtb.request;

import com.fasterxml.jackson.databind.node.ObjectNode;

import java.math.BigDecimal;

/**
 * This object provides information pertaining to the device through which the
 * user is interacting. Device information includes its hardware, platform,
 * location, and carrier data. The device can refer to a mobile handset,
 * a desktop computer, set top box, or other digital device.
 * <p><em>BEST PRACTICE</em>: There are currently no prominent open source lists
 * for device makes, models, operating systems, or carriers. Exchanges typically
 * use commercial products or other proprietary lists for these attributes.
 * Until suitable open standards are available, exchanges are highly encouraged
 * to publish lists of their device make, model, operating system, and carrier
 * values to bidders.
 * <p><em>BEST PRACTICE</em>: Proper device IP detection in mobile is not
 * straightforward. Typically it involves starting at the left of the
 * {@code x-forwarded-for} header, skipping private carrier networks
 * (e.g., 10.x.x.x or 192.x.x.x), and possibly scanning for known carrier IP
 * ranges. Exchanges are urged to research and implement this feature carefully
 * when presenting device IP values to bidders.
 */


public class Device {

    /** Browser user agent string. (recommended) */
    String ua;

    /**
     * Location of the device assumed to be the user’s current location defined
     * by a Geo object (Section 3.2.19).
     * (recommended)
     */
    Geo geo;

    /**
     * Standard “Do Not Track” flag as set in the header by the browser,
     * where 0 = tracking is unrestricted, 1 = do not track.
     * (recommended)
     */
    Integer dnt;

    /**
     * “Limit Ad Tracking” signal commercially endorsed (e.g., iOS, Android),
     * where 0 = tracking is unrestricted, 1 = tracking must be limited per
     * commercial guidelines.
     * (recommended)
     */
    Integer lmt;

    /**
     * IPv4 address closest to device.
     * (recommended)
     */
    String ip;

    /** IP address closest to device as IPv6. */
    String ipv6;

    /** The general type of device. Refer to List 5.21. */
    Integer devicetype;

    /** Device make (e.g., “Apple”). */
    String make;

    /** Device model (e.g., “iPhone”). */
    String model;

    /** Device operating system (e.g., “iOS”). */
    String os;

    /** Device operating system version (e.g., “3.1.2”). */
    String osv;

    /** Hardware version of the device (e.g., “5S” for iPhone 5S). */
    String hwv;

    /** Physical height of the screen in pixels. */
    Integer h;

    /** Physical width of the screen in pixels. */
    Integer w;

    /** Screen size as pixels per linear inch. */
    Integer ppi;

    /** The ratio of physical pixels to device independent pixels. */
    BigDecimal pxratio;

    /** Support for JavaScript, where 0 = no, 1 = yes. */
    Integer js;

    /**
     * Indicates if the geolocation API will be available to JavaScript code
     * running in the banner, where 0 = no, 1 = yes.
     */
    Integer geofetch;

    /** Version of Flash supported by the browser. */
    String flashver;

    /** Browser language using ISO-639-1-alpha-2. */
    String language;

    /**
     * Carrier or ISP (e.g., “VERIZON”) using exchange curated string names
     * which should be published to bidders a priori.
     */
    String carrier;

    /**
     * Mobile carrier as the concatenated MCC-MNC code (e.g., “310-005”
     * identifies Verizon Wireless CDMA in the USA). Refer to
     * https://en.wikipedia.org/wiki/Mobile_country_code for further examples.
     * Note that the dash between the MCC and MNC parts is required to remove
     * parsing ambiguity.
     */
    String mccmnc;

    /** Network connection type. Refer to List 5.22. */
    Integer connectiontype;

    /** ID sanctioned for advertiser use in the clear (i.e., not hashed). */
    String ifa;

    /** Hardware device ID (e.g., IMEI); hashed via SHA1. */
    String didsha1;

    /** Hardware device ID (e.g., IMEI); hashed via MD5. */
    String didmd5;

    /** Platform device ID (e.g., Android ID); hashed via SHA1. */
    String dpidsha1;

    /** Platform device ID (e.g., Android ID); hashed via MD5. */
    String dpidmd5;

    /** MAC address of the device; hashed via SHA1. */
    String macsha1;

    /** MAC address of the device; hashed via MD5. */
    String macmd5;

    /** Placeholder for exchange-specific extensions to OpenRTB. */
    ObjectNode ext;

    public String getUa() {
        return ua;
    }

    public void setUa(String ua) {
        this.ua = ua;
    }

    public Geo getGeo() {
        return geo;
    }

    public void setGeo(Geo geo) {
        this.geo = geo;
    }

    public Integer getDnt() {
        return dnt;
    }

    public void setDnt(Integer dnt) {
        this.dnt = dnt;
    }

    public Integer getLmt() {
        return lmt;
    }

    public void setLmt(Integer lmt) {
        this.lmt = lmt;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIpv6() {
        return ipv6;
    }

    public void setIpv6(String ipv6) {
        this.ipv6 = ipv6;
    }

    public Integer getDevicetype() {
        return devicetype;
    }

    public void setDevicetype(Integer devicetype) {
        this.devicetype = devicetype;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getOsv() {
        return osv;
    }

    public void setOsv(String osv) {
        this.osv = osv;
    }

    public String getHwv() {
        return hwv;
    }

    public void setHwv(String hwv) {
        this.hwv = hwv;
    }

    public Integer getH() {
        return h;
    }

    public void setH(Integer h) {
        this.h = h;
    }

    public Integer getW() {
        return w;
    }

    public void setW(Integer w) {
        this.w = w;
    }

    public Integer getPpi() {
        return ppi;
    }

    public void setPpi(Integer ppi) {
        this.ppi = ppi;
    }

    public BigDecimal getPxratio() {
        return pxratio;
    }

    public void setPxratio(BigDecimal pxratio) {
        this.pxratio = pxratio;
    }

    public Integer getJs() {
        return js;
    }

    public void setJs(Integer js) {
        this.js = js;
    }

    public Integer getGeofetch() {
        return geofetch;
    }

    public void setGeofetch(Integer geofetch) {
        this.geofetch = geofetch;
    }

    public String getFlashver() {
        return flashver;
    }

    public void setFlashver(String flashver) {
        this.flashver = flashver;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getMccmnc() {
        return mccmnc;
    }

    public void setMccmnc(String mccmnc) {
        this.mccmnc = mccmnc;
    }

    public Integer getConnectiontype() {
        return connectiontype;
    }

    public void setConnectiontype(Integer connectiontype) {
        this.connectiontype = connectiontype;
    }

    public String getIfa() {
        return ifa;
    }

    public void setIfa(String ifa) {
        this.ifa = ifa;
    }

    public String getDidsha1() {
        return didsha1;
    }

    public void setDidsha1(String didsha1) {
        this.didsha1 = didsha1;
    }

    public String getDidmd5() {
        return didmd5;
    }

    public void setDidmd5(String didmd5) {
        this.didmd5 = didmd5;
    }

    public String getDpidsha1() {
        return dpidsha1;
    }

    public void setDpidsha1(String dpidsha1) {
        this.dpidsha1 = dpidsha1;
    }

    public String getDpidmd5() {
        return dpidmd5;
    }

    public void setDpidmd5(String dpidmd5) {
        this.dpidmd5 = dpidmd5;
    }

    public String getMacsha1() {
        return macsha1;
    }

    public void setMacsha1(String macsha1) {
        this.macsha1 = macsha1;
    }

    public String getMacmd5() {
        return macmd5;
    }

    public void setMacmd5(String macmd5) {
        this.macmd5 = macmd5;
    }

    public ObjectNode getExt() {
        return ext;
    }

    public void setExt(ObjectNode ext) {
        this.ext = ext;
    }
}
