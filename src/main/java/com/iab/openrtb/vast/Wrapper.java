package com.iab.openrtb.vast;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iab.openrtb.vast.ad.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="Error")
public class Wrapper {

    @JsonProperty("Impression")
    @XmlElement(name = "Impression")
    private Impression impression;

    @JsonProperty("VASTAdTagURI")
    @XmlElement(name = "VASTAdTagURI")
    private VASTAdTagURI vastAdTagURI;

    @JsonProperty("AdSystem")
    @XmlElement(name = "AdSystem")
    private AdSystem adSystem;

    @JsonProperty("Pricing")
    @XmlElement(name = "Pricing")
    private Pricing pricing;

    @JsonProperty("Error")
    @XmlElement(name = "Error")
    private Error error;

    @JsonProperty("ViewableImpression")
    @XmlElement(name = "ViewableImpression")
    private ViewableImpression viewableImpression;

    @JsonProperty("AdVerifications")
    @XmlElement(name = "AdVerifications")
    private List<AdVerifications> adVerifications;

    @JsonProperty("Extensions")
    @XmlElement(name = "Extensions")
    private List<Extensions> extensions;

    @JsonProperty("Creatives")
    @XmlElement(name = "Creatives")
    private List<Creatives> creatives;

    public Impression getImpression() {
        return impression;
    }

    public void setImpression(Impression impression) {
        this.impression = impression;
    }

    public VASTAdTagURI getVastAdTagURI() {
        return vastAdTagURI;
    }

    public void setVastAdTagURI(VASTAdTagURI vastAdTagURI) {
        this.vastAdTagURI = vastAdTagURI;
    }

    public AdSystem getAdSystem() {
        return adSystem;
    }

    public void setAdSystem(AdSystem adSystem) {
        this.adSystem = adSystem;
    }

    public Pricing getPricing() {
        return pricing;
    }

    public void setPricing(Pricing pricing) {
        this.pricing = pricing;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public ViewableImpression getViewableImpression() {
        return viewableImpression;
    }

    public void setViewableImpression(ViewableImpression viewableImpression) {
        this.viewableImpression = viewableImpression;
    }

    public List<AdVerifications> getAdVerifications() {
        return adVerifications;
    }

    public void setAdVerifications(List<AdVerifications> adVerifications) {
        this.adVerifications = adVerifications;
    }

    public List<Extensions> getExtensions() {
        return extensions;
    }

    public void setExtensions(List<Extensions> extensions) {
        this.extensions = extensions;
    }

    public List<Creatives> getCreatives() {
        return creatives;
    }

    public void setCreatives(List<Creatives> creatives) {
        this.creatives = creatives;
    }
}
