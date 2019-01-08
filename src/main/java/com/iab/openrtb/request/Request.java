package com.iab.openrtb.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;

/**
 * The Native Object defines the native advertising opportunity available for bid via this bid request.
 * It will be included as a JSON-encoded string in the bid request’s imp.native field or as a direct JSON object,
 * depending on the choice of the exchange. While OpenRTB 2.x officially supports only JSON-encoded strings,
 * many exchanges have implemented a formal object. Check with your integration docs.
 * <p>
 * Note: Prior to VERSION 1.1, the specification could be interpreted as requiring the native request to have a
 * root node with a single field “native” that would contain the object above as its value.
 * The Native Markup Request Object specified above is now the root object.
 */

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Request {

    /** Version in use. **/
    String ver;

    /** The context in which the ad appears. */
    Integer context;

    /** A more detailed context in which the ad appears. */
    Integer contextsubtype;

    /** The design/format/layout of the ad unit being offered. */
    Integer plcmttype;

    /** The number of identical placements in this layout */
    Integer plcmtcnt;

    /** 0 for the first ad, 1 for the second ad, and so on */
    Integer seq;

    /** Any bid response must comply with the array of elements expressed in the bid request.*/
    List<Asset> assets;

    /** Set to '0' in case if supply source / impression supports returning an assets url. */
    Integer aurlsupport;

    /** Set to '0' in case if supply source / impression supports returning an dco url. */
    Integer durlsupport;

    /** Specifies types of events supported by tracking. */
    EventTracker eventtrackers;

    /** Set to '0' or field absent if doesn't support custom privacy or support unknown, otherwise '1'. */
    Integer privacy;

    /** Placeholder that may contain custom JSON. */
    ObjectNode ext;

    public Request(){}

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public Integer getContext() {
        return context;
    }

    public void setContext(Integer context) {
        this.context = context;
    }

    public Integer getContextsubtype() {
        return contextsubtype;
    }

    public void setContextsubtype(Integer contextsubtype) {
        this.contextsubtype = contextsubtype;
    }

    public Integer getPlcmttype() {
        return plcmttype;
    }

    public void setPlcmttype(Integer plcmttype) {
        this.plcmttype = plcmttype;
    }

    public Integer getPlcmtcnt() {
        return plcmtcnt;
    }

    public void setPlcmtcnt(Integer plcmtcnt) {
        this.plcmtcnt = plcmtcnt;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public List<Asset> getAssets() {
        return assets;
    }

    public void setAssets(List<Asset> assets) {
        this.assets = assets;
    }

    public Integer getAurlsupport() {
        return aurlsupport;
    }

    public void setAurlsupport(Integer aurlsupport) {
        this.aurlsupport = aurlsupport;
    }

    public Integer getDurlsupport() {
        return durlsupport;
    }

    public void setDurlsupport(Integer durlsupport) {
        this.durlsupport = durlsupport;
    }

    public EventTracker getEventtrackers() {
        return eventtrackers;
    }

    public void setEventtrackers(EventTracker eventtrackers) {
        this.eventtrackers = eventtrackers;
    }

    public Integer getPrivacy() {
        return privacy;
    }

    public void setPrivacy(Integer privacy) {
        this.privacy = privacy;
    }

    public ObjectNode getExt() {
        return ext;
    }

    public void setExt(ObjectNode ext) {
        this.ext = ext;
    }
}
