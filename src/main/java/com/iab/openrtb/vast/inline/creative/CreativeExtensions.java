package com.iab.openrtb.vast.inline.creative;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "CreativeExtensions")
public class CreativeExtensions {

    @XmlElement
    private List<CreativeExtension> creativeExtensions;

    public List<CreativeExtension> getCreativeExtensions() {
        return creativeExtensions;
    }

    public void setCreativeExtensions(List<CreativeExtension> creativeExtensions) {
        this.creativeExtensions = creativeExtensions;
    }
}
