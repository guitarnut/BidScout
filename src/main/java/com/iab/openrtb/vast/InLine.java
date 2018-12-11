package com.iab.openrtb.vast;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.iab.openrtb.vast.ad.*;
import com.iab.openrtb.vast.ad.adverifications.Verification;
import com.iab.openrtb.vast.ad.creative.Creative;
import com.iab.openrtb.vast.ad.extensions.Extension;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class InLine {

    @JsonProperty("AdSystem")
    @JacksonXmlProperty(localName = "AdSystem")
    private AdSystem adSystem;

    @JsonProperty("AdTitle")
    @JacksonXmlProperty(localName = "AdTitle")
    private AdTitle adTitle;

    @JsonProperty("Impression")
    @JacksonXmlProperty(localName = "Impression")
    private Impression impression;

    @JsonProperty("Category")
    @JacksonXmlProperty(localName = "Category")
    private Category category;

    @JsonProperty("Description")
    @JacksonXmlProperty(localName = "Description")
    private Description description;

    @JsonProperty("Advertiser")
    @JacksonXmlProperty(localName = "Advertiser")
    private Advertiser advertiser;

    @JsonProperty("Pricing")
    @JacksonXmlProperty(localName = "Pricing")
    private Pricing pricing;

    @JsonProperty("Survey")
    @JacksonXmlProperty(localName = "Survey")
    private Survey survey;

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
    @JacksonXmlElementWrapper(localName = "Extensions")
    @JacksonXmlProperty(localName = "Extension")
    private List<Extension> extension;

    @JsonProperty("Creatives")
    @JacksonXmlElementWrapper(localName = "Creatives")
    @JacksonXmlProperty(localName = "Creative")
    private List<Creative> creative;

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

    public List<Verification> getAdVerifications() {
        return adVerifications;
    }

    public void setAdVerifications(List<Verification> adVerifications) {
        this.adVerifications = adVerifications;
    }

    public List<Extension> getExtension() {
        return extension;
    }

    public void setExtension(List<Extension> extension) {
        this.extension = extension;
    }

    public List<Creative> getCreative() {
        return creative;
    }

    public void setCreative(List<Creative> creative) {
        this.creative = creative;
    }
}
