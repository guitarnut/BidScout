package com.iab.openrtb.vast.inline.adverifications;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="Verification")
public class Verification {

    @XmlAttribute(name = "vendor")
    private String vendor;

    @XmlElement
    private JavaScriptResource javaScriptResource;

    @XmlElement
    private FlashResource flashResource;

    @XmlElement
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
