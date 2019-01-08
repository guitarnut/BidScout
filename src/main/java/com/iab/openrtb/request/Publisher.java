package com.iab.openrtb.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;

/**
 * This object describes the publisher of the media in which the ad will be
 * displayed. The publisher is typically the seller in an OpenRTB transaction.
 */

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Publisher {

    /** Exchange-specific publisher ID. */
    String id;

    /** Publisher name (may be aliased at the publisher’s request). */
    String name;

    /** Array of IAB content categories that describe the publisher. Refer to List 5.1. */
    List<String> cat;

    /** Highest level domain of the publisher (e.g., “publisher.com”). */
    String domain;

    /** Placeholder for exchange-specific extensions to OpenRTB. */
    ObjectNode ext;

    public Publisher(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getCat() {
        return cat;
    }

    public void setCat(List<String> cat) {
        this.cat = cat;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public ObjectNode getExt() {
        return ext;
    }

    public void setExt(ObjectNode ext) {
        this.ext = ext;
    }
}
