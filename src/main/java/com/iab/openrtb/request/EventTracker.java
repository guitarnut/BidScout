package com.iab.openrtb.request;

import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;



public class EventTracker {
    Integer event;

    List<Integer> methods;

    ObjectNode ext;

    public Integer getEvent() {
        return event;
    }

    public void setEvent(Integer event) {
        this.event = event;
    }

    public List<Integer> getMethods() {
        return methods;
    }

    public void setMethods(List<Integer> methods) {
        this.methods = methods;
    }

    public ObjectNode getExt() {
        return ext;
    }

    public void setExt(ObjectNode ext) {
        this.ext = ext;
    }
}
