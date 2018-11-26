package com.iab.openrtb.vast.inline.adverifications;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="ViewableImpression")
public class ViewableImpression {
    @XmlAttribute(name = "id")
    private String id;

    @XmlValue
    private String value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
