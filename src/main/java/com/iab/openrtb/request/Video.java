package com.iab.openrtb.request;

import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;

/**
 * This object represents an in-stream video impression. Many of the fields are
 * non-essential for minimally viable transactions, but are included to offer
 * fine control when needed. Video in OpenRTB generally assumes compliance with
 * the VAST standard. As such, the notion of companion ads is supported by
 * optionally including an array of {@link Banner} objects (refer to the
 * {@link Banner} object in Section 3.2.6) that define these companion ads.
 * <p>The presence of a {@link Video} as a subordinate of the {@link Imp} object
 * indicates that this impression is offered as a video type impression. At the
 * publisher’s discretion, that same impression may also be offered as banner,
 * audio, and/or native by also including as {@link Imp} subordinates objects of
 * those types. However, any given bid for the impression must conform to one of
 * the offered types.
 */


public class Video {

    /**
     * Content MIME types supported (e.g., “video/x-ms-wmv”, “video/mp4”).
     * (required)
     */
    List<String> mimes;

    /** Minimum video ad duration in seconds. (recommended) */
    Integer minduration;

    /** Maximum video ad duration in seconds. (recommended) */
    Integer maxduration;

    /**
     * Array of supported video protocols. Refer to List 5.8. At least one
     * supported protocol must be specified in either the protocol or protocols
     * attribute. (recommended)
     */
    List<Integer> protocols;

    /**
     * Width of the video player in device independent pixels (DIPS).
     * (recommended)
     */
    Integer w;

    /**
     * Height of the video player in device independent pixels (DIPS).
     * (recommended)
     */
    Integer h;

    /**
     * Indicates the start delay in seconds for pre-roll, mid-roll, or post-roll
     * ad placements. Refer to List 5.12 for additional generic values.
     * (recommended)
     */
    Integer startdelay;

    /** Placement type for the impression. Refer to List 5.9. */
    Integer placement;

    /**
     * Indicates if the impression must be linear, nonlinear, etc. If none
     * specified, assume all are allowed. Refer to List 5.7.
     */
    Integer linearity;

    /**
     * Indicates if the player will allow the video to be skipped, where 0 = no,
     * 1 = yes. <p>If a bidder sends markup/creative that is itself skippable,
     * the Bid object should include the attr array with an element of 16
     * indicating skippable video. Refer to List 5.3.
     */
    Integer skip;

    /**
     * Videos of total duration greater than this number of seconds can be
     * skippable; only applicable if the ad is skippable.
     */
    int skipmin;

    /**
     * Number of seconds a video must play before skipping is enabled; only
     * applicable if the ad is skippable.
     */
    int skipafter;

    /**
     * If multiple ad impressions are offered in the same bid request, the
     * sequence number will allow for the coordinated delivery of multiple
     * creatives.
     */
    Integer sequence;

    /** Blocked creative attributes. Refer to List 5.3. */
    List<Integer> battr;

    /**
     * Maximum extended ad duration if extension is allowed. If blank or 0,
     * extension is not allowed. If -1, extension is allowed, and there is no
     * time limit imposed. If greater than 0, then the value represents the
     * number of seconds of extended play supported beyond the maxduration
     * value.
     */
    Integer maxextended;

    /** Minimum bit rate in Kbps. */
    Integer minbitrate;

    /** Maximum bit rate in Kbps. */
    Integer maxbitrate;

    /**
     * Indicates if letter-boxing of 4:3 content into a 16:9 window is allowed,
     * where 0 = no, 1 = yes.;
     */
    Integer boxingallowed;

    /**
     * Playback methods that may be in use. If none are specified, any method
     * may be used. Refer to List 5.10. Only one method is typically used in
     * practice. As a result, this array may be converted to an Integer in a
     * future version of the specification. It is strongly advised to use only
     * the first element of this array in preparation for this change.
     */
    List<Integer> playbackmethod;

    /** The event that causes playback to end. Refer to List 5.11. */
    Integer playbackend;

    /**
     * Supported delivery methods (e.g., streaming, progressive). If none
     * specified, assume all are supported. Refer to List 5.15.
     */
    List<Integer> delivery;

    /** Ad position on screen. Refer to List 5.4. */
    Integer pos;

    /** Array of Banner objects (Section 3.2.6) if companion ads are available. */
    List<Banner> companionad;

    /**
     * List of supported API frameworks for this impression. Refer to List 5.6.
     * If an API is not explicitly listed, it is assumed not to be supported.
     */
    List<Integer> api;

    /**
     * Supported VAST companion ad types. Refer to List 5.14. Recommended if
     * companion Banner objects are included via the companion ad array.
     * If one of these banners will be rendered as an end-card, this can be
     * specified using the vcm attribute with the particular banner
     * (Section 3.2.6).
     */
    List<Integer> companiontype;

    /** Placeholder for exchange-specific extensions to OpenRTB. */
    ObjectNode ext;

    public List<String> getMimes() {
        return mimes;
    }

    public void setMimes(List<String> mimes) {
        this.mimes = mimes;
    }

    public Integer getMinduration() {
        return minduration;
    }

    public void setMinduration(Integer minduration) {
        this.minduration = minduration;
    }

    public Integer getMaxduration() {
        return maxduration;
    }

    public void setMaxduration(Integer maxduration) {
        this.maxduration = maxduration;
    }

    public List<Integer> getProtocols() {
        return protocols;
    }

    public void setProtocols(List<Integer> protocols) {
        this.protocols = protocols;
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

    public Integer getStartdelay() {
        return startdelay;
    }

    public void setStartdelay(Integer startdelay) {
        this.startdelay = startdelay;
    }

    public Integer getPlacement() {
        return placement;
    }

    public void setPlacement(Integer placement) {
        this.placement = placement;
    }

    public Integer getLinearity() {
        return linearity;
    }

    public void setLinearity(Integer linearity) {
        this.linearity = linearity;
    }

    public Integer getSkip() {
        return skip;
    }

    public void setSkip(Integer skip) {
        this.skip = skip;
    }

    public int getSkipmin() {
        return skipmin;
    }

    public void setSkipmin(int skipmin) {
        this.skipmin = skipmin;
    }

    public int getSkipafter() {
        return skipafter;
    }

    public void setSkipafter(int skipafter) {
        this.skipafter = skipafter;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public List<Integer> getBattr() {
        return battr;
    }

    public void setBattr(List<Integer> battr) {
        this.battr = battr;
    }

    public Integer getMaxextended() {
        return maxextended;
    }

    public void setMaxextended(Integer maxextended) {
        this.maxextended = maxextended;
    }

    public Integer getMinbitrate() {
        return minbitrate;
    }

    public void setMinbitrate(Integer minbitrate) {
        this.minbitrate = minbitrate;
    }

    public Integer getMaxbitrate() {
        return maxbitrate;
    }

    public void setMaxbitrate(Integer maxbitrate) {
        this.maxbitrate = maxbitrate;
    }

    public Integer getBoxingallowed() {
        return boxingallowed;
    }

    public void setBoxingallowed(Integer boxingallowed) {
        this.boxingallowed = boxingallowed;
    }

    public List<Integer> getPlaybackmethod() {
        return playbackmethod;
    }

    public void setPlaybackmethod(List<Integer> playbackmethod) {
        this.playbackmethod = playbackmethod;
    }

    public Integer getPlaybackend() {
        return playbackend;
    }

    public void setPlaybackend(Integer playbackend) {
        this.playbackend = playbackend;
    }

    public List<Integer> getDelivery() {
        return delivery;
    }

    public void setDelivery(List<Integer> delivery) {
        this.delivery = delivery;
    }

    public Integer getPos() {
        return pos;
    }

    public void setPos(Integer pos) {
        this.pos = pos;
    }

    public List<Banner> getCompanionad() {
        return companionad;
    }

    public void setCompanionad(List<Banner> companionad) {
        this.companionad = companionad;
    }

    public List<Integer> getApi() {
        return api;
    }

    public void setApi(List<Integer> api) {
        this.api = api;
    }

    public List<Integer> getCompaniontype() {
        return companiontype;
    }

    public void setCompaniontype(List<Integer> companiontype) {
        this.companiontype = companiontype;
    }

    public ObjectNode getExt() {
        return ext;
    }

    public void setExt(ObjectNode ext) {
        this.ext = ext;
    }
}
