package com.iab.openrtb.vast.inline.adverifications;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="FlashResource")
public class FlashResource {
    @XmlAttribute(name = "apiFramework")
    private String apiFramework;

    @XmlValue
    private String value;

    public String getApiFramework() {
        return apiFramework;
    }

    public void setApiFramework(String apiFramework) {
        this.apiFramework = apiFramework;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
