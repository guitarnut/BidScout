package com.iab.openrtb.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Map;


/**
 * This object contains any legal, governmental, or industry regulations that
 * apply to the request. The {@code coppa} flag signals whether or not the
 * request falls under the United States Federal Trade Commission’s regulations
 * for the United States Children’s Online Privacy Protection Act (“COPPA”).
 */

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Regs {

    /**
     * Flag indicating if this request is subject to the COPPA regulations
     * established by the USA FTC, where 0 = no, 1 = yes. Refer to Section 7.5
     * for more information.
     */
    Integer coppa;

    /** Placeholder for exchange-specific extensions to OpenRTB. */
    Map<Object, Object> ext;

    public Regs(){}

    public Integer getCoppa() {
        return coppa;
    }

    public void setCoppa(Integer coppa) {
        this.coppa = coppa;
    }

    public Map<Object, Object> getExt() {
        return ext;
    }

    public void setExt(Map<Object, Object> ext) {
        this.ext = ext;
    }
}
