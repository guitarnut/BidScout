package com.iab.openrtb.vast.inline;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "InLine")
public class InLine {

    @XmlElement
    private AdSystem adSystem;

    @XmlElement
    private AdTitle adTitle;

    @XmlElement
    private Impression impression;

    @XmlElement
    private Category category;

    @XmlElement
    private Description description;

    @XmlElement
    private Advertiser advertiser;

    @XmlElement
    private Pricing pricing;

    @XmlElement
    private Survey survey;

    @XmlElement
    private Error error;

    @XmlElement
    private ViewableImpression viewableImpression;

    @XmlElement
    private List<AdVerifications> adVerifications;

    @XmlElement
    private List<Extensions> extensions;

    @XmlElement
    private List<Creatives> creatives;

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
