package com.iab.openrtb.vast.ad.creative;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.iab.openrtb.vast.ad.creative.linear.Linear;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Creative {

    @JacksonXmlProperty(isAttribute = true)
    private String id;

    @JacksonXmlProperty(isAttribute = true)
    private String sequence;

    @JacksonXmlProperty(isAttribute = true)
    private String adId;

    @JacksonXmlProperty(isAttribute = true)
    private String apiFramework;

    @JsonProperty("UniversalAdId")
    @JacksonXmlProperty(localName = "UniversalAdId")
    private UniversalAdId universalAdId;

    @JsonProperty("CreativeExtensions")
    @JacksonXmlElementWrapper(localName = "CreativeExtensions")
    @JacksonXmlProperty(localName = "CreativeExtension")
    private List<CreativeExtension> creativeExtensions;

    @JsonProperty("Linear")
    @JacksonXmlProperty(localName = "Linear")
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

    public List<CreativeExtension> getCreativeExtensions() {
        return creativeExtensions;
    }

    public void setCreativeExtensions(List<CreativeExtension> creativeExtensions) {
        this.creativeExtensions = creativeExtensions;
    }

    public Linear getLinear() {
        return linear;
    }

    public void setLinear(Linear linear) {
        this.linear = linear;
    }
}
