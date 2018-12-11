package com.iab.openrtb.vast.ad.creative.linear;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

public class Duration {

    @JacksonXmlText
    private String value;

    public Duration(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
