package com.iab.openrtb.vast.inline;

import com.iab.openrtb.vast.inline.adverifications.Verification;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="AdVerifications")
public class AdVerifications {

    @XmlElement
    private List<Verification> verifications;

    public List<Verification> getVerifications() {
        return verifications;
    }

    public void setVerifications(List<Verification> verifications) {
        this.verifications = verifications;
    }
}
