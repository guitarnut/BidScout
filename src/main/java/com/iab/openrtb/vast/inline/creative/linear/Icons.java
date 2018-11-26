package com.iab.openrtb.vast.inline.creative.linear;

import com.iab.openrtb.vast.inline.creative.linear.icons.Icon;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Icons")
public class Icons {

    @XmlElement
    private List<Icon> icons;

    public List<Icon> getIcons() {
        return icons;
    }

    public void setIcons(List<Icon> icons) {
        this.icons = icons;
    }
}
