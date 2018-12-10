package com.iab.openrtb.vast.ad;

import com.iab.openrtb.vast.ad.creative.Creative;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Creatives")
public class Creatives {

    @XmlElement(name = "Creative")
    private List<Creative> creative;

    public List<Creative> getCreative() {
        return creative;
    }

    public void setCreative(List<Creative> creative) {
        this.creative = creative;
    }
}
