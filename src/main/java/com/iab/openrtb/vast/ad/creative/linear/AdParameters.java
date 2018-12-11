package com.iab.openrtb.vast.ad.creative.linear;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

public class AdParameters {

    @JacksonXmlProperty(isAttribute = true)
    private String xmlEncoded;

    @JacksonXmlCData
    @JacksonXmlText
    private String value;

    public String getXmlEncoded() {
        return xmlEncoded;
    }

    public void setXmlEncoded(String xmlEncoded) {
        this.xmlEncoded = xmlEncoded;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
