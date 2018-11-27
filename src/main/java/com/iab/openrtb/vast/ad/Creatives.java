package com.iab.openrtb.vast.ad;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gnut.bidscout.model.Creative;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Creatives")
public class Creatives {

    @JsonProperty("Creatives")
    @XmlElement(name = "Creatives")
    private List<Creative> creatives;

    public List<Creative> getCreatives() {
        return creatives;
    }

    public void setCreatives(List<Creative> creatives) {
        this.creatives = creatives;
    }
}
