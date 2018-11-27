package com.iab.openrtb.vast.ad.creative.linear.icons;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iab.openrtb.vast.ad.creative.linear.icons.iconclicks.IconClickThrough;
import com.iab.openrtb.vast.ad.creative.linear.icons.iconclicks.IconClickTracking;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "IconClicks")
public class IconClicks {

    @JsonProperty("IconClickThrough")
    @XmlElement(name = "IconClickThrough")
    private IconClickThrough iconClickThrough;

    @JsonProperty("IconClickTracking")
    @XmlElement(name = "IconClickTracking")
    private IconClickTracking iconClickTracking;

    public IconClickThrough getIconClickThrough() {
        return iconClickThrough;
    }

    public void setIconClickThrough(IconClickThrough iconClickThrough) {
        this.iconClickThrough = iconClickThrough;
    }

    public IconClickTracking getIconClickTracking() {
        return iconClickTracking;
    }

    public void setIconClickTracking(IconClickTracking iconClickTracking) {
        this.iconClickTracking = iconClickTracking;
    }
}
