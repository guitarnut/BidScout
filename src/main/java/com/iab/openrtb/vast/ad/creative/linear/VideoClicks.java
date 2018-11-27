package com.iab.openrtb.vast.ad.creative.linear;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iab.openrtb.vast.ad.creative.linear.videoclicks.ClickThrough;
import com.iab.openrtb.vast.ad.creative.linear.videoclicks.ClickTracking;
import com.iab.openrtb.vast.ad.creative.linear.videoclicks.CustomClick;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "VideoClicks")
public class VideoClicks {

    @JsonProperty("ClickThrough")
    @XmlElement(name = "ClickThrough")
    private ClickThrough clickThrough;

    @JsonProperty("ClickTracking")
    @XmlElement(name = "ClickTracking")
    private ClickTracking clickTracking;

    @JsonProperty("CustomClick")
    @XmlElement(name = "CustomClick")
    private CustomClick customClick;

    public ClickThrough getClickThrough() {
        return clickThrough;
    }

    public void setClickThrough(ClickThrough clickThrough) {
        this.clickThrough = clickThrough;
    }

    public ClickTracking getClickTracking() {
        return clickTracking;
    }

    public void setClickTracking(ClickTracking clickTracking) {
        this.clickTracking = clickTracking;
    }

    public CustomClick getCustomClick() {
        return customClick;
    }

    public void setCustomClick(CustomClick customClick) {
        this.customClick = customClick;
    }
}
