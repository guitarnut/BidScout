package com.iab.openrtb.vast.ad.creative.linear;

import com.gnut.bidscout.xml.AdapterCDATA;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "AdParameters")
public class AdParameters {

    @XmlAttribute(name = "xmlEncoded")
    private String xmlEncoded;

    @XmlJavaTypeAdapter(AdapterCDATA.class)
    @XmlValue
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
