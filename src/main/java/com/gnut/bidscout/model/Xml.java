package com.gnut.bidscout.model;

import com.iab.openrtb.vast.Vast;

public class Xml {
    private String id;
    private String owner;
    private Vast vast;

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

    public Vast getVast() {
        return vast;
    }

    public void setVast(Vast vast) {
        this.vast = vast;
    }
}
