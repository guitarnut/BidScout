package com.iab.openrtb.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;

/**
 * This object represents the most general type of impression. Although the term
 * “banner” may have very specific meaning in other contexts, here it can be
 * many things including a simple static image, an expandable ad unit, or even
 * in-banner video (refer to the {@link Video} object in Section 3.2.7 for the
 * more generalized and full featured video ad units). An array of
 * {@link Banner} objects can also appear within the {@link Video} to describe
 * optional companion ads defined in the VAST specification.
 * <p>The presence of a {@link Banner} as a subordinate of the {@link Imp}
 * object indicates that this impression is offered as a banner type impression.
 * At the publisher’s discretion, that same impression may also be offered as
 * video, audio, and/or native by also including as {@link Imp} subordinates
 * objects of those types. However, any given bid for the impression must
 * conform to one of the offered types.
 */

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Banner {

    /**
     * Array of format objects (Section 3.2.10) representing the banner sizes
     * permitted. If none are specified, then use of the h and w attributes is
     * highly recommended.
     */
    List<Format> format;

    /**
     * Exact width in device independent pixels (DIPS);
     * recommended if no format objects are specified.
     */
    Integer w;

    /**
     * Exact height in device independent pixels (DIPS);
     * recommended if no format objects are specified.
     */
    Integer h;

    /** Blocked banner ad types. Refer to List 5.2. */
    List<Integer> btype;

    /** Blocked creative attributes. Refer to List 5.3. */
    List<Integer> battr;

    /** Ad position on screen. Refer to List 5.4. */
    Integer pos;

    /**
     * Content MIME types supported. Popular MIME types may include
     * “application/x-shockwave-flash”, “image/jpg”, and “image/gif”.
     */
    List<String> mimes;

    /**
     * Indicates if the banner is in the top frame as opposed to an iframe,
     * where 0 = no, 1 = yes.
     */
    Integer topframe;

    /** Directions in which the banner may expand. Refer to List 5.5. */
    List<Integer> expdir;

    /**
     * List of supported API frameworks for this impression. Refer to List 5.6.
     * If an API is not explicitly listed, it is assumed not to be supported.
     */
    List<Integer> api;

    /**
     * Unique identifier for this banner object. Recommended when Banner objects
     * are used with a Video object (Section 3.2.7) to represent an array of
     * companion ads. Values usually start at 1 and increase with each object;
     * should be unique within an impression.
     */
    String id;

    /**
     * Relevant only for Banner objects used with a Video object (Section 3.2.7)
     * in an array of companion ads. Indicates the companion banner rendering
     * mode relative to the associated video, where 0 = concurrent,
     * 1 = end-card.
     */
    Integer vcm;

    /** Placeholder for exchange-specific extensions to OpenRTB. */
    ObjectNode ext;

    public Banner(){}

    public List<Format> getFormat() {
        return format;
    }

    public void setFormat(List<Format> format) {
        this.format = format;
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

    public List<Integer> getBtype() {
        return btype;
    }

    public void setBtype(List<Integer> btype) {
        this.btype = btype;
    }

    public List<Integer> getBattr() {
        return battr;
    }

    public void setBattr(List<Integer> battr) {
        this.battr = battr;
    }

    public Integer getPos() {
        return pos;
    }

    public void setPos(Integer pos) {
        this.pos = pos;
    }

    public List<String> getMimes() {
        return mimes;
    }

    public void setMimes(List<String> mimes) {
        this.mimes = mimes;
    }

    public Integer getTopframe() {
        return topframe;
    }

    public void setTopframe(Integer topframe) {
        this.topframe = topframe;
    }

    public List<Integer> getExpdir() {
        return expdir;
    }

    public void setExpdir(List<Integer> expdir) {
        this.expdir = expdir;
    }

    public List<Integer> getApi() {
        return api;
    }

    public void setApi(List<Integer> api) {
        this.api = api;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getVcm() {
        return vcm;
    }

    public void setVcm(Integer vcm) {
        this.vcm = vcm;
    }

    public ObjectNode getExt() {
        return ext;
    }

    public void setExt(ObjectNode ext) {
        this.ext = ext;
    }
}
