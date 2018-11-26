package com.iab.openrtb.vast;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="Pricing")
public class Pricing
{
    @XmlAttribute(name="model")
    private String model;

    @XmlAttribute(name="currency")
    private String currency;

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
