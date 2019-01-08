package com.iab.openrtb.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;

/**
 * The data and segment objects together allow additional data about the related
 * object (e.g., user, content) to be specified. This data may be from multiple
 * sources whether from the exchange itself or third parties as specified by the
 * {@code id} field. A bid request can mix data objects from multiple providers.
 * The specific data providers in use should be published by the exchange
 * <em>a priori</em> to its bidders.
 */

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Data {

    /** Exchange-specific ID for the data provider. */
    String id;

    /** Exchange-specific name for the data provider. */
    String name;

    /**
     * Array of Segment (Section 3.2.22) objects that contain the actual data
     * values.
     */
    List<Segment> segment;

    /** Placeholder for exchange-specific extensions to OpenRTB. */
    ObjectNode ext;

    public Data(){}

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

    public List<Segment> getSegment() {
        return segment;
    }

    public void setSegment(List<Segment> segment) {
        this.segment = segment;
    }

    public ObjectNode getExt() {
        return ext;
    }

    public void setExt(ObjectNode ext) {
        this.ext = ext;
    }
}
