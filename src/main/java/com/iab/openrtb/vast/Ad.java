package com.iab.openrtb.vast;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Ad {

    @JacksonXmlProperty(isAttribute = true)
    private String id;

    @JacksonXmlProperty(isAttribute = true)
    private String sequence;

    @JacksonXmlProperty(isAttribute = true)
    private String conditionalAd;

    @JsonProperty("InLine")
    @JacksonXmlProperty(localName = "InLine")
    private InLine inLine;

    @JsonProperty("Wrapper")
    @JacksonXmlProperty(localName = "Wrapper")
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
