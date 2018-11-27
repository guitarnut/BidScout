package com.iab.openrtb.vast.ad.adverifications;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="Verification")
public class Verification {

    @JsonProperty("vendor")
    @XmlAttribute(name = "vendor")
    private String vendor;

    @JsonProperty("JavaScriptResource")
    @XmlElement(name = "JavaScriptResource")
    private JavaScriptResource javaScriptResource;

    @JsonProperty("FlashResource")
    @XmlElement(name = "FlashResource")
    private FlashResource flashResource;

    @JsonProperty("ViewableImpression")
    @XmlElement(name = "ViewableImpression")
    private ViewableImpression viewableImpression;

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public JavaScriptResource getJavaScriptResource() {
        return javaScriptResource;
    }

    public void setJavaScriptResource(JavaScriptResource javaScriptResource) {
        this.javaScriptResource = javaScriptResource;
    }

    public FlashResource getFlashResource() {
        return flashResource;
    }

    public void setFlashResource(FlashResource flashResource) {
        this.flashResource = flashResource;
    }

    public ViewableImpression getViewableImpression() {
        return viewableImpression;
    }

    public void setViewableImpression(ViewableImpression viewableImpression) {
        this.viewableImpression = viewableImpression;
    }
}
