package com.iab.openrtb.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;

/**
 * This object defines the producer of the content in which the ad will be
 * shown. This is particularly useful when the content is syndicated and may be
 * distributed through different publishers and thus when the producer and
 * publisher are not necessarily the same entity.
 */

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Producer {

    /**
     * Content producer or originator ID. Useful if content is syndicated and
     * may be posted on a site using embed tags.
     */
    String id;

    /** Content producer or originator name (e.g., “Warner Bros”). */
    String name;

    /**
     * Array of IAB content categories that describe the content producer.
     * Refer to List 5.1.
     */
    List<String> cat;

    /** Highest level domain of the content producer (e.g., “producer.com”). */
    String domain;

    /** Placeholder for exchange-specific extensions to OpenRTB. */
    ObjectNode ext;

    public Producer(){}

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
