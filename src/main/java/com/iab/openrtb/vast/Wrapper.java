package com.iab.openrtb.vast;

import com.iab.openrtb.vast.ad.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="Error")
public class Wrapper {

    @XmlElement
    private Impression impression;

    @XmlElement
    private VASTAdTagURI vastAdTagURI;

    @XmlElement
    private AdSystem adSystem;

    @XmlElement
    private Pricing pricing;

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
