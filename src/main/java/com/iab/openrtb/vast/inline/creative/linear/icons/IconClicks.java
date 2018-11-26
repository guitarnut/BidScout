package com.iab.openrtb.vast.inline.creative.linear.icons;

import com.iab.openrtb.vast.inline.creative.linear.icons.iconclicks.IconClickThrough;
import com.iab.openrtb.vast.inline.creative.linear.icons.iconclicks.IconClickTracking;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "IconClicks")
public class IconClicks {

    @XmlElement
    private IconClickThrough iconClickThrough;

    @XmlElement
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
