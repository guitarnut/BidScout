package com.iab.openrtb.vast;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iab.openrtb.vast.ad.*;
import com.iab.openrtb.vast.ad.adverifications.Verification;
import com.iab.openrtb.vast.ad.creative.Creative;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "InLine")
public class InLine {

    @JsonProperty("AdSystem")
    @XmlElement(name = "AdSystem")
    private AdSystem adSystem;

    @JsonProperty("AdTitle")
    @XmlElement(name = "AdTitle")
    private AdTitle adTitle;

    @JsonProperty("Impression")
    @XmlElement(name = "Impression")
    private Impression impression;

    @JsonProperty("Category")
    @XmlElement(name = "Category")
    private Category category;

    @JsonProperty("Description")
    @XmlElement(name = "Description")
    private Description description;

    @JsonProperty("Advertiser")
    @XmlElement(name = "Advertiser")
    private Advertiser advertiser;

    @JsonProperty("Pricing")
    @XmlElement(name = "Pricing")
    private Pricing pricing;

    @JsonProperty("Survey")
    @XmlElement(name = "Survey")
    private Survey survey;

    @JsonProperty("Error")
    @XmlElement(name = "Error")
    private Error error;

    @JsonProperty("ViewableImpression")
    @XmlElement(name = "ViewableImpression")
    private ViewableImpression viewableImpression;

    @JsonProperty("AdVerifications")
    @XmlElement(name = "AdVerifications")
    private AdVerifications adVerifications;

    @JsonProperty("Extensions")
    @XmlElement(name = "Extensions")
    private List<Extensions> extensions;

    @JsonProperty("Creatives")
    @XmlElement(name = "Creatives")
    private List<Creative> creatives;

    public AdSystem getAdSystem() {
        return adSystem;
    }

    public void setAdSystem(AdSystem adSystem) {
        this.adSystem = adSystem;
    }

    public AdTitle getAdTitle() {
        return adTitle;
    }

    public void setAdTitle(AdTitle adTitle) {
        this.adTitle = adTitle;
    }

    public Impression getImpression() {
        return impression;
    }

    public void setImpression(Impression impression) {
        this.impression = impression;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public Advertiser getAdvertiser() {
        return advertiser;
    }

    public void setAdvertiser(Advertiser advertiser) {
        this.advertiser = advertiser;
    }

    public Pricing getPricing() {
        return pricing;
    }

    public void setPricing(Pricing pricing) {
        this.pricing = pricing;
    }

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
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

    public AdVerifications getAdVerifications() {
        return adVerifications;
    }

    public void setAdVerifications(AdVerifications verification) {
        this.adVerifications = verification;
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
