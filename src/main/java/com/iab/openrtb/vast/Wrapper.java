package com.iab.openrtb.vast;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.iab.openrtb.vast.ad.*;
import com.iab.openrtb.vast.ad.adverifications.Verification;
import com.iab.openrtb.vast.ad.creative.Creative;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Wrapper {

    @JsonProperty("Impression")
    @JacksonXmlProperty(localName = "Impression")
    private Impression impression;

    @JsonProperty("VASTAdTagURI")
    @JacksonXmlProperty(localName = "VASTAdTagURI")
    private VASTAdTagURI vastAdTagURI;

    @JsonProperty("AdSystem")
    @JacksonXmlProperty(localName = "AdSystem")
    private AdSystem adSystem;

    @JsonProperty("Pricing")
    @JacksonXmlProperty(localName = "Pricing")
    private Pricing pricing;

    @JsonProperty("Error")
    @JacksonXmlProperty(localName = "Error")
    private Error error;

    @JsonProperty("ViewableImpression")
    @JacksonXmlProperty(localName = "ViewableImpression")
    private ViewableImpression viewableImpression;

    @JsonProperty("AdVerifications")
    @JacksonXmlElementWrapper(localName = "AdVerifications")
    @JacksonXmlProperty(localName = "Verification")
    private List<Verification> adVerifications;

    @JsonProperty("Extensions")
    @JacksonXmlProperty(localName = "Creative")
    private List<Extensions> extensions;

    @JsonProperty("Creatives")
    @JacksonXmlProperty(localName = "Creative")
    private List<Creative> creatives;

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

    public List<Verification> getAdVerifications() {
        return adVerifications;
    }

    public void setAdVerifications(List<Verification> adVerifications) {
        this.adVerifications = adVerifications;
    }

    public List<Extensions> getExtensions() {
        return extensions;
    }

    public void setExtensions(List<Extensions> extensions) {
        this.extensions = extensions;
    }

    public List<Creative> getCreatives() {
        return creatives;
    }

    public void setCreatives(List<Creative> creatives) {
        this.creatives = creatives;
    }
}
