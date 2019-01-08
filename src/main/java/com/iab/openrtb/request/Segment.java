package com.iab.openrtb.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.node.ObjectNode;


/**
 * Segment objects are essentially key-value pairs that convey specific units of
 * data. The parent {@link Data} object is a collection of such values from a
 * given data provider.
 * The specific segment names and value options must be published by the
 * exchange <em>a priori</em> to its bidders.
 */

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Segment {

    /** ID of the data segment specific to the data provider. */
    String id;

    /** Name of the data segment specific to the data provider. */
    String name;

    /** String representation of the data segment value. */
    String value;

    /** Placeholder for exchange-specific extensions to OpenRTB. */
    ObjectNode ext;

    public Segment(){}

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ObjectNode getExt() {
        return ext;
    }

    public void setExt(ObjectNode ext) {
        this.ext = ext;
    }
}
