package com.iab.openrtb.request;

import com.fasterxml.jackson.databind.node.ObjectNode;



/**
 * This object is associated with an impression as an array of metrics.
 * These metrics can offer insight into the impression to assist with
 * decisioning such as average recent viewability, click-through rate, etc.
 * Each metric is identified by its type, reports the value of the metric,
 * and optionally identifies the source or vendor measuring the value.
 */


public class Metric {

    /**
     * Type of metric being presented using exchange curated string names which
     * should be published to bidders <em>a priori</em>.
     * (required)
     */
    String type;

    /**
     * Number representing the value of the metric. Probabilities must be
     * in the range 0.0 – 1.0.
     */
    float value;

    /**
     * Source of the value using exchange curated string names which should be
     * published to bidders <em>a priori</em>. If the exchange itself is the
     * source versus a third party, “EXCHANGE” is recommended.
     */
    String vendor;

    /** Placeholder for exchange-specific extensions to OpenRTB. */
    ObjectNode ext;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public ObjectNode getExt() {
        return ext;
    }

    public void setExt(ObjectNode ext) {
        this.ext = ext;
    }
}
