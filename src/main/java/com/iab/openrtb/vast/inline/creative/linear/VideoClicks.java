package com.iab.openrtb.vast.inline.creative.linear;

import com.iab.openrtb.vast.inline.creative.linear.videoclicks.ClickThrough;
import com.iab.openrtb.vast.inline.creative.linear.videoclicks.ClickTracking;
import com.iab.openrtb.vast.inline.creative.linear.videoclicks.CustomClick;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "VideoClicks")
public class VideoClicks {

    @XmlElement
    private ClickThrough clickThrough;

    @XmlElement
    private ClickTracking clickTracking;

    @XmlElement
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
