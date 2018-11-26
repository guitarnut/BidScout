package com.iab.openrtb.vast.ad.adverifications;

import com.gnut.bidscout.xml.AdapterCDATA;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="JavaScriptResource")
public class JavaScriptResource {

    @XmlAttribute(name = "apiFramework")
    private String apiFramework;

    @XmlJavaTypeAdapter(AdapterCDATA.class)
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
