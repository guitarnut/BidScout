package com.iab.openrtb.vast;

import com.iab.openrtb.vast.inline.Ad;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "VAST")
public class Vast {
    @XmlAttribute(name = "version")
    private String id;

    @XmlValue
    private Error error;

    @XmlValue
    private Ad ad;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public Ad getAd() {
        return ad;
    }

    public void setAd(Ad ad) {
        this.ad = ad;
    }
}
