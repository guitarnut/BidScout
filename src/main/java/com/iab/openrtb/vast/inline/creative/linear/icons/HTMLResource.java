package com.iab.openrtb.vast.inline.creative.linear.icons;

import com.gnut.bidscout.xml.AdapterCDATA;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "HTMLResource")
public class HTMLResource {

    @XmlAttribute(name = "creativeType")
    private String creativeType;

    @XmlJavaTypeAdapter(AdapterCDATA.class)
    @XmlValue
    private String value;

    public String getCreativeType() {
        return creativeType;
    }

    public void setCreativeType(String creativeType) {
        this.creativeType = creativeType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
