package com.iab.openrtb.vast.inline;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="Ad")
public class Ad
{
    @XmlAttribute(name="id")
    private String id;

    @XmlAttribute(name="sequence")
    private String sequence;

    @XmlAttribute(name="conditionalAd")
    private String conditionalAd;

    @XmlValue
    private String value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getConditionalAd() {
        return conditionalAd;
    }

    public void setConditionalAd(String conditionalAd) {
        this.conditionalAd = conditionalAd;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
