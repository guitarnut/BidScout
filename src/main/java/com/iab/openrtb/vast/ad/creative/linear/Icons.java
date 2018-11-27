package com.iab.openrtb.vast.ad.creative.linear;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iab.openrtb.vast.ad.creative.linear.icons.Icon;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Icons")
public class Icons {

    @JsonProperty("Icons")
    @XmlElement(name = "Icons")
    private List<Icon> icons;

    public List<Icon> getIcons() {
        return icons;
    }

    public void setIcons(List<Icon> icons) {
        this.icons = icons;
    }
}
