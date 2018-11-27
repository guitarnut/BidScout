package com.iab.openrtb.vast.ad.creative.linear.icons;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Icon")
public class Icon {

    @XmlAttribute(name = "program")
    private String program;

    @XmlAttribute(name = "width")
    private String width;

    @XmlAttribute(name = "height")
    private String height;

    @XmlAttribute(name = "xPosition")
    private String xPosition;

    @XmlAttribute(name = "yPosition")
    private String yPosition;

    @XmlAttribute(name = "duration")
    private String duration;

    @XmlAttribute(name = "offset")
    private String offset;

    @XmlAttribute(name = "apiFramework")
    private String apiFramework;

    @XmlAttribute(name = "pxratio,")
    private String pxratio;

    @JsonProperty("StaticResource")
    @XmlElement(name = "StaticResource")
    StaticResource staticResource;

    @JsonProperty("IFrameResource")
    @XmlElement(name = "IFrameResource")
    IFrameResource iFrameResource;

    @JsonProperty("HTMLResource")
    @XmlElement(name = "HTMLResource")
    HTMLResource htmlResource;

    @JsonProperty("IconClicks")
    @XmlElement(name = "IconClicks")
    IconClicks iconClicks;

    @JsonProperty("IconViewTracking")
    @XmlElement(name = "IconViewTracking")
    IconViewTracking iconViewTracking;

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getxPosition() {
        return xPosition;
    }

    public void setxPosition(String xPosition) {
        this.xPosition = xPosition;
    }

    public String getyPosition() {
        return yPosition;
    }

    public void setyPosition(String yPosition) {
        this.yPosition = yPosition;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public String getApiFramework() {
        return apiFramework;
    }

    public void setApiFramework(String apiFramework) {
        this.apiFramework = apiFramework;
    }

    public String getPxratio() {
        return pxratio;
    }

    public void setPxratio(String pxratio) {
        this.pxratio = pxratio;
    }

    public StaticResource getStaticResource() {
        return staticResource;
    }

    public void setStaticResource(StaticResource staticResource) {
        this.staticResource = staticResource;
    }

    public IFrameResource getiFrameResource() {
        return iFrameResource;
    }

    public void setiFrameResource(IFrameResource iFrameResource) {
        this.iFrameResource = iFrameResource;
    }

    public HTMLResource getHtmlResource() {
        return htmlResource;
    }

    public void setHtmlResource(HTMLResource htmlResource) {
        this.htmlResource = htmlResource;
    }

    public IconClicks getIconClicks() {
        return iconClicks;
    }

    public void setIconClicks(IconClicks iconClicks) {
        this.iconClicks = iconClicks;
    }

    public IconViewTracking getIconViewTracking() {
        return iconViewTracking;
    }

    public void setIconViewTracking(IconViewTracking iconViewTracking) {
        this.iconViewTracking = iconViewTracking;
    }
}
