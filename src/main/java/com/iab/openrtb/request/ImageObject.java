package com.iab.openrtb.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ImageObject {
    Integer type;

    Integer w;

    Integer wmin;

    Integer h;

    Integer hmin;

    List<String> mimes;

    ObjectNode ext;

    public ImageObject(){}

    public void setType(Integer type) {
        this.type = type;
    }

    public void setW(Integer w) {
        this.w = w;
    }

    public void setWmin(Integer wmin) {
        this.wmin = wmin;
    }

    public void setH(Integer h) {
        this.h = h;
    }

    public void setHmin(Integer hmin) {
        this.hmin = hmin;
    }

    public void setMimes(List<String> mimes) {
        this.mimes = mimes;
    }

    public void setExt(ObjectNode ext) {
        this.ext = ext;
    }
}
