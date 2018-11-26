package com.iab.openrtb.vast.ad;

import com.iab.openrtb.vast.ad.extensions.Extension;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="Extensions")
public class Extensions {

    @XmlValue
    private List<Extension> extensions;

    public List<Extension> getExtensions() {
        return extensions;
    }

    public void setExtensions(List<Extension> extensions) {
        this.extensions = extensions;
    }
}
