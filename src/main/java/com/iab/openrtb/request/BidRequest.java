package com.iab.openrtb.request;

import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;

/**
 * The top-level bid request object contains a globally unique bid request or
 * auction ID. This {@code id} attribute is required as is at least one
 * impression object (Section 3.2.4). Other attributes in this top-level object
 * establish rules and restrictions that apply to all impressions being offered.
 * <p>There are also several subordinate objects that provide detailed data to
 * potential buyers. Among these are the {@link Site} and {@link App} objects,
 * which describe the type of published media in which the impression(s) appear.
 * These objects are highly recommended, but only one applies to a given bid
 * request depending on whether the media is browser-based web content or a
 * non-browser application, respectively.
 */


public class BidRequest {

    /**
     * Unique ID of the bid request, provided by the exchange.
     * (required)
     */
    String id;

    /**
     * Array of Imp objects (Section 3.2.4) representing the impressions
     * offered. At least 1 Imp object is required.
     */
    List<Imp> imp;

    /**
     * Details via a Site object (Section 3.2.13) about the publisher’s website.
     * Only applicable and recommended for websites.
     */
    Site site;

    /**
     * Details via an Application object (Section 3.2.14) about the publisher’s app
     * (i.e., non-browser applications). Only applicable and recommended for
     * apps.
     */
    App app;

    /**
     * Details via a Device object (Section 3.2.18) about the user’s device
     * to which the impression will be delivered.
     */
    Device device;

    /**
     * Details via a User object (Section 3.2.20) about the human user
     * of the device; the advertising audience.
     */
    User user;

    /**
     * Indicator of test mode in which auctions are not billable,
     * where 0 = live mode, 1 = test mode.
     */
    Integer test;

    /**
     * Auction type, where 1 = First Price, 2 = Second Price Plus. Exchange-specific
     * auction types can be defined using values greater than 500.
     */
    Integer at;

    /**
     * Maximum time in milliseconds the exchange allows for bids to be received
     * including Internet latency to avoid timeout. This value supersedes any
     * <em>a priori</em> guidance from the exchange.
     */
    Long tmax;

    /**
     * White list of buyer seats (e.g., advertisers, agencies) allowed to bid on
     * this impression. IDs of seats and knowledge of the buyer’s customers to
     * which they refer must be coordinated between bidders and the exchange
     * <em>a priori</em>. At most, only one of wseat and bseat should be used in
     * the same request. Omission of both implies no seat restrictions.
     */
    List<String> wseat;

    /**
     * Block list of buyer seats (e.g., advertisers, agencies) restricted from
     * bidding on this impression. IDs of seats and knowledge of the buyer’s
     * customers to which they refer must be coordinated between bidders and the
     * exchange <em>a priori</em>. At most, only one of wseat and bseat should
     * be used in the same request. Omission of both implies no seat
     * restrictions.
     */
    List<String> bseat;

    /**
     * Flag to indicate if Exchange can verify that the impressions offered
     * represent all of the impressions available in context (e.g., all on the
     * web page, all video spots such as pre/mid/post roll) to support
     * road-blocking. 0 = no or unknown, 1 = yes, the impressions offered
     * represent all that are available.
     */
    Integer allimps;

    /**
     * Array of allowed currencies for bids on this bid request using ISO-4217
     * alpha codes. Recommended only if the exchange accepts multiple
     * currencies.
     */
    List<String> cur;

    /**
     * White list of languages for creatives using ISO-639-1-alpha-2.
     * Omission implies no specific restrictions, but buyers would be advised
     * to consider language attribute in the Device and/or Content objects
     * if available.
     */
    List<String> wlang;

    /**
     * Blocked advertiser categories using the IAB content categories.
     * Refer to List 5.1.
     */
    List<String> bcat;

    /**
     * Block list of advertisers by their domains (e.g., “ford.com”).
     */
    List<String> badv;

    /**
     * Block list of applications by their platform-specific
     * exchange-independent application identifiers. On Android, these should be
     * bundle or package names (e.g., com.foo.mygame). On iOS, these are numeric
     * IDs.
     */
    List<String> bapp;

    /**
     * A Source object (Section 3.2.2) that provides data about the inventory
     * source and which entity makes the final decision.
     */
    Source source;

    /**
     * A Regs object (Section 3.2.3) that specifies any industry, legal,
     * or governmental regulations in force for this request.
     */
    Regs regs;

    /**
     * Placeholder for exchange-specific extensions to OpenRTB
     */
    ObjectNode ext;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Imp> getImp() {
        return imp;
    }

    public void setImp(List<Imp> imp) {
        this.imp = imp;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getTest() {
        return test;
    }

    public void setTest(Integer test) {
        this.test = test;
    }

    public Integer getAt() {
        return at;
    }

    public void setAt(Integer at) {
        this.at = at;
    }

    public Long getTmax() {
        return tmax;
    }

    public void setTmax(Long tmax) {
        this.tmax = tmax;
    }

    public List<String> getWseat() {
        return wseat;
    }

    public void setWseat(List<String> wseat) {
        this.wseat = wseat;
    }

    public List<String> getBseat() {
        return bseat;
    }

    public void setBseat(List<String> bseat) {
        this.bseat = bseat;
    }

    public Integer getAllimps() {
        return allimps;
    }

    public void setAllimps(Integer allimps) {
        this.allimps = allimps;
    }

    public List<String> getCur() {
        return cur;
    }

    public void setCur(List<String> cur) {
        this.cur = cur;
    }

    public List<String> getWlang() {
        return wlang;
    }

    public void setWlang(List<String> wlang) {
        this.wlang = wlang;
    }

    public List<String> getBcat() {
        return bcat;
    }

    public void setBcat(List<String> bcat) {
        this.bcat = bcat;
    }

    public List<String> getBadv() {
        return badv;
    }

    public void setBadv(List<String> badv) {
        this.badv = badv;
    }

    public List<String> getBapp() {
        return bapp;
    }

    public void setBapp(List<String> bapp) {
        this.bapp = bapp;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public Regs getRegs() {
        return regs;
    }

    public void setRegs(Regs regs) {
        this.regs = regs;
    }

    public ObjectNode getExt() {
        return ext;
    }

    public void setExt(ObjectNode ext) {
        this.ext = ext;
    }
}
