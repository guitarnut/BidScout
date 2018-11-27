package com.iab.openrtb.vast.ad.creative;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iab.openrtb.vast.ad.creative.linear.Linear;

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

    @JsonProperty("UniversalAdId")
    @XmlElement(name = "UniversalAdId")
    private UniversalAdId universalAdId;

    @JsonProperty("CreativeExtensions")
    @XmlElement(name = "CreativeExtensions")
    private List<CreativeExtensions> creativeExtensions;

    @JsonProperty("Linear")
    @XmlElement(name = "Linear")
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
