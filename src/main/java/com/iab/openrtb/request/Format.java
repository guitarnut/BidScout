package com.iab.openrtb.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Map;


/**
 * This object represents an allowed size (i.e., height and width combination)
 * or Flex Ad parameters for a banner impression. These are typically used in an
 * array where multiple sizes are permitted. It is recommended that either the
 * {@code w}/{@code h} pair or the {@code wratio}/{@code hratio}/{@code wmin}
 * set (i.e., for Flex Ads) be specified.
 */

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Format {

    /** Width in device independent pixels (DIPS). */
    Integer w;

    /** Height in device independent pixels (DIPS). */
    Integer h;

    /** Relative width when expressing size as a ratio. */
    Integer wratio;

    /** Relative height when expressing size as a ratio. */
    Integer hratio;

    /**
     * The minimum width in device independent pixels (DIPS) at which the ad
     * will be displayed the size is expressed as a ratio.
     */
    Integer wmin;

    /** Placeholder for exchange-specific extensions to OpenRTB. */
    Map<Object, Object> ext;

    public Format(){}

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

    public Integer getWmin() {
        return wmin;
    }

    public void setWmin(Integer wmin) {
        this.wmin = wmin;
    }

    public Map<Object, Object> getExt() {
        return ext;
    }

    public void setExt(Map<Object, Object> ext) {
        this.ext = ext;
    }
}
