package com.iab.openrtb.vast.inline.creative;

import com.iab.openrtb.vast.inline.creative.linear.Linear;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Creative")
public class Creative {

    @XmlAttribute(name = "id")
    private String id;

    @XmlAttribute(name = "sequence")
    private String sequence;

    @XmlAttribute(name = "adId")
    private String adId;

    @XmlAttribute(name = "apiFramework")
    private String apiFramework;

    @XmlElement
    private UniversalAdId universalAdId;

    @XmlElement
    private List<CreativeExtensions> creativeExtensions;

    @XmlElement
    private Linear linear;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getAdId() {
        return adId;
    }

    public void setAdId(String adId) {
        this.adId = adId;
    }

    public String getApiFramework() {
        return apiFramework;
    }

    public void setApiFramework(String apiFramework) {
        this.apiFramework = apiFramework;
    }

    public UniversalAdId getUniversalAdId() {
        return universalAdId;
    }

    public void setUniversalAdId(UniversalAdId universalAdId) {
        this.universalAdId = universalAdId;
    }

    public List<CreativeExtensions> getCreativeExtensions() {
        return creativeExtensions;
    }

    public void setCreativeExtensions(List<CreativeExtensions> creativeExtensions) {
        this.creativeExtensions = creativeExtensions;
    }

    public Linear getLinear() {
        return linear;
    }

    public void setLinear(Linear linear) {
        this.linear = linear;
    }
}
