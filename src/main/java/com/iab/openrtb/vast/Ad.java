package com.iab.openrtb.vast;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Ad")
public class Ad {

    @XmlAttribute(name = "id")
    private String id;

    @XmlAttribute(name = "sequence")
    private String sequence;

    @XmlAttribute(name = "conditionalAd")
    private String conditionalAd;

    @JsonProperty("InLine")
    @XmlElement(name = "InLine")
    private InLine inLine;

    @JsonProperty("Wrapper")
    @XmlElement(name = "Wrapper")
    private Wrapper wrapper;

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

    public InLine getInLine() {
        return inLine;
    }

    public void setInLine(InLine inLine) {
        this.inLine = inLine;
    }

    public Wrapper getWrapper() {
        return wrapper;
    }

    public void setWrapper(Wrapper wrapper) {
        this.wrapper = wrapper;
    }
}
