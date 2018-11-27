package com.iab.openrtb.vast;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "VAST")
public class Vast {
    @Id
    private String id;

    @XmlAttribute(name = "version")
    private String version;

    @JsonProperty("Error")
    @XmlElement
    private Error error;

    @JsonProperty("Ad")
    @XmlElement(name = "Ad")
    private Ad ad;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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
