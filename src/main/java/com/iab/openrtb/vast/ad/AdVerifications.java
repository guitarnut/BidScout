package com.iab.openrtb.vast.ad;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iab.openrtb.vast.ad.adverifications.Verification;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="AdVerifications")
public class AdVerifications {

    @JsonProperty("Verification")
    @XmlElement(name = "Verification")
    private List<Verification> value;

    public List<Verification> getValue() {
        return value;
    }

    public void setValue(List<Verification> value) {
        this.value = value;
    }
}
