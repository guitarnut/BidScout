package com.iab.openrtb.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VideoObject {
    List<String> mimes;

    Integer minduration;

    Integer maxduration;

    List<Integer> protocols;

    ObjectNode ext;

    public VideoObject(){}

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

    public ObjectNode getExt() {
        return ext;
    }

    public void setExt(ObjectNode ext) {
        this.ext = ext;
    }
}
