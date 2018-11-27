package com.iab.openrtb.vast.ad;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iab.openrtb.vast.ad.adverifications.Verification;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="AdVerifications")
public class AdVerifications {

    @JsonProperty("Verifications")
    @XmlElement(name = "Verifications")
    private List<Verification> verifications;

    public List<Verification> getVerifications() {
        return verifications;
    }

    public void setVerifications(List<Verification> verifications) {
        this.verifications = verifications;
    }
}
