package com.iab.openrtb.vast.ad;

import com.gnut.bidscout.xml.AdapterCDATA;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="Pricing")
public class Pricing
{
    @XmlAttribute(name="model")
    private String model;

    @XmlAttribute(name="currency")
    private String currency;

    @XmlJavaTypeAdapter(AdapterCDATA.class)
    @XmlValue
    private String value;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
