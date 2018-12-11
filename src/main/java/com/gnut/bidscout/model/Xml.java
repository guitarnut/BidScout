package com.gnut.bidscout.model;

import com.iab.openrtb.vast.Vast;

public class Xml {
    private String id;
    private String owner;
    private String name;
    private Vast vast;

    public void copyValues(Xml xml) {
        this.id = xml.getId();
        this.owner = xml.getOwner();
        this.name = xml.getName();
        this.vast = xml.getVast();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Vast getVast() {
        return vast;
    }

    public void setVast(Vast vast) {
        this.vast = vast;
    }
}
