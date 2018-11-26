package com.iab.openrtb.vast.ad.creative;

import com.gnut.bidscout.xml.AdapterCDATA;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "UniversalAdId")
public class UniversalAdId {

    @XmlAttribute(name = "idRegistry")
    private String idRegistry;

    @XmlAttribute(name = "idValue")
    private String idValue;

    @XmlJavaTypeAdapter(AdapterCDATA.class)
    @XmlValue
    private String value;

    public String getIdRegistry() {
        return idRegistry;
    }

    public void setIdRegistry(String idRegistry) {
        this.idRegistry = idRegistry;
    }

    public String getIdValue() {
        return idValue;
    }

    public void setIdValue(String idValue) {
        this.idValue = idValue;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
