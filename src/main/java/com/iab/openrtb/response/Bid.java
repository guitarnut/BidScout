package com.iab.openrtb.response;

import com.fasterxml.jackson.databind.node.ObjectNode;

import java.math.BigDecimal;
import java.util.List;

/**
 * A {@link SeatBid} object contains one or more Bid objects, each of which
 * relates to a specific impression in the bid request via the {@code impid}
 * attribute and constitutes an offer to buy that impression for a given
 * {@code price}.
 * <p>
 * IMPORTANT: unlike other data classes this one is mutable (annotated with {@link Data} instead of
 * {@link lombok.Value}). Motivation: during the course of processing bids could be altered several times (price
 * adjustment, post-processing). Creating new instance of the bid in each of these cases seems to cause unnecessary
 * memory pressure. In order to avoid unnecessary allocations this class is made mutable (as an exception) i.e. this
 * decision could be seen as a performance optimisation.
 */
public class Bid {

    /** Bidder generated bid ID to assist with logging/tracking. (required) */
    String id;

    /** ID of the Imp object in the related bid request. (required) */
    String impid;

    /**
     * Bid price expressed as CPM although the actual transaction is for a unit
     * impression only. Note that while the type indicates float, Integer math
     * is highly recommended when handling currencies (e.g., BigDecimal in
     * Java).
     * (required)
     */
    BigDecimal price;

    /**
     * Win notice URL called by the exchange if the bid wins (not necessarily
     * indicative of a delivered, viewed, or billable ad); optional means of
     * serving ad markup. Substitution macros (Section 4.4) may be included in
     * both the URL and optionally returned markup.
     */
    String nurl;

    /**
     * Billing notice URL called by the exchange when a winning bid becomes
     * billable based on exchange-specific business policy (e.g., typically
     * delivered, viewed, etc.). Substitution macros (Section 4.4) may be
     * included.
     */
    String burl;

    /**
     * Loss notice URL called by the exchange when a bid is known to have been
     * lost. Substitution macros (Section 4.4) may be included.
     * Exchange-specific policy may preclude support for loss notices or the
     * disclosure of winning clearing prices resulting in ${AUCTION_PRICE}
     * macros being removed (i.e., replaced with a zero-length String).
     */
    String lurl;

    /**
     * Optional means of conveying ad markup in case the bid wins; supersedes
     * the win notice if markup is included in both. Substitution macros
     * (Section 4.4) may be included.
     */
    String adm;

    /** ID of a preloaded ad to be served if the bid wins. */
    String adid;

    /**
     * Advertiser domain for block list checking (e.g., “ford.com”). This can be
     * an array of for the case of rotating creatives. Exchanges can mandate
     * that only one domain is allowed.
     */
    List<String> adomain;

    /**
     * A platform-specific application identifier intended to be unique to the
     * app and independent of the exchange. On Android, this should be a bundle
     * or package name (e.g., com.foo.mygame). On iOS, it is a numeric ID.
     */
    String bundle;

    /**
     * URL without cache-busting to an image that is representative of the
     * content of the campaign for ad quality/safety checking.
     */
    String iurl;

    /**
     * Campaign ID to assist with ad quality checking; the collection of
     * creatives for which iurl should be representative.
     */
    String cid;

    /**
     * Creative ID to assist with ad quality checking. tactic String Tactic ID
     * to enable buyers to label bids for reporting to the exchange the tactic
     * through which their bid was submitted. The specific usage and meaning of
     * the tactic ID should be communicated between buyer and exchanges a
     * priori.
     */
    String crid;

    /** IAB content categories of the creative. Refer to List 5.1. */
    List<String> cat;

    /** Set of attributes describing the creative. Refer to List 5.3. */
    List<Integer> attr;

    /** API required by the markup if applicable. Refer to List 5.6. */
    Integer api;

    /** Video response protocol of the markup if applicable. Refer to List 5.8. */
    Integer protocol;

    /** Creative media rating per IQG guidelines. Refer to List 5.19. */
    Integer qagmediarating;

    /**
     * Language of the creative using ISO-639-1-alpha-2. The non- standard code
     * “xx” may also be used if the creative has no linguistic content (e.g., a
     * banner with just a company logo).
     */
    String language;

    /**
     * Reference to the deal.id from the bid request if this bid pertains to a
     * private marketplace direct deal.
     */
    String dealid;

    /** Width of the creative in device independent pixels (DIPS). */
    Integer w;

    /** Height of the creative in device independent pixels (DIPS). */
    Integer h;

    /**
     * Relative width of the creative when expressing size as a ratio. Required
     * for Flex Ads.
     */
    Integer wratio;

    /**
     * Relative height of the creative when expressing size as a ratio. Required
     * for Flex Ads.
     */
    Integer hratio;

    /**
     * Advisory as to the number of seconds the bidder is willing to wait
     * between the auction and the actual impression.
     */
    Integer exp;

    /** Placeholder for bidder-specific extensions to OpenRTB. */
    ObjectNode ext;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImpid() {
        return impid;
    }

    public void setImpid(String impid) {
        this.impid = impid;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getNurl() {
        return nurl;
    }

    public void setNurl(String nurl) {
        this.nurl = nurl;
    }

    public String getBurl() {
        return burl;
    }

    public void setBurl(String burl) {
        this.burl = burl;
    }

    public String getLurl() {
        return lurl;
    }

    public void setLurl(String lurl) {
        this.lurl = lurl;
    }

    public String getAdm() {
        return adm;
    }

    public void setAdm(String adm) {
        this.adm = adm;
    }

    public String getAdid() {
        return adid;
    }

    public void setAdid(String adid) {
        this.adid = adid;
    }

    public List<String> getAdomain() {
        return adomain;
    }

    public void setAdomain(List<String> adomain) {
        this.adomain = adomain;
    }

    public String getBundle() {
        return bundle;
    }

    public void setBundle(String bundle) {
        this.bundle = bundle;
    }

    public String getIurl() {
        return iurl;
    }

    public void setIurl(String iurl) {
        this.iurl = iurl;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCrid() {
        return crid;
    }

    public void setCrid(String crid) {
        this.crid = crid;
    }

    public List<String> getCat() {
        return cat;
    }

    public void setCat(List<String> cat) {
        this.cat = cat;
    }

    public List<Integer> getAttr() {
        return attr;
    }

    public void setAttr(List<Integer> attr) {
        this.attr = attr;
    }

    public Integer getApi() {
        return api;
    }

    public void setApi(Integer api) {
        this.api = api;
    }

    public Integer getProtocol() {
        return protocol;
    }

    public void setProtocol(Integer protocol) {
        this.protocol = protocol;
    }

    public Integer getQagmediarating() {
        return qagmediarating;
    }

    public void setQagmediarating(Integer qagmediarating) {
        this.qagmediarating = qagmediarating;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDealid() {
        return dealid;
    }

    public void setDealid(String dealid) {
        this.dealid = dealid;
    }

    public Integer getW() {
        return w;
    }

    public void setW(Integer w) {
        this.w = w;
    }

    public Integer getH() {
        return h;
    }

    public void setH(Integer h) {
        this.h = h;
    }

    public Integer getWratio() {
        return wratio;
    }

    public void setWratio(Integer wratio) {
        this.wratio = wratio;
    }

    public Integer getHratio() {
        return hratio;
    }

    public void setHratio(Integer hratio) {
        this.hratio = hratio;
    }

    public Integer getExp() {
        return exp;
    }

    public void setExp(Integer exp) {
        this.exp = exp;
    }

    public ObjectNode getExt() {
        return ext;
    }

    public void setExt(ObjectNode ext) {
        this.ext = ext;
    }
}
