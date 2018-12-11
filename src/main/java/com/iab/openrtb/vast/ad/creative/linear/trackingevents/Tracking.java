package com.iab.openrtb.vast.ad.creative.linear.trackingevents;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

public class Tracking {

    @JacksonXmlProperty(isAttribute = true, localName = "event")
    private String event;

    @JacksonXmlProperty(isAttribute = true, localName = "offset")
    private String offset;

    @JacksonXmlText
    @JacksonXmlCData
    private String value;

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
