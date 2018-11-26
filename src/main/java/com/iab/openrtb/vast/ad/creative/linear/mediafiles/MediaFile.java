package com.iab.openrtb.vast.ad.creative.linear.mediafiles;

import com.gnut.bidscout.xml.AdapterCDATA;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "MediaFile")
public class MediaFile {

    @XmlAttribute(name = "id")
    private String id;

    @XmlAttribute(name = "delivery")
    private String delivery;

    @XmlAttribute(name = "type")
    private String type;

    @XmlAttribute(name = "bitrate")
    private String bitrate;

    @XmlAttribute(name = "minBitrate")
    private String minBitrate;

    @XmlAttribute(name = "maxBitrate")
    private String maxBitrate;

    @XmlAttribute(name = "width")
    private String width;

    @XmlAttribute(name = "height")
    private String height;

    @XmlAttribute(name = "scalable")
    private String scalable;

    @XmlAttribute(name = "mantainAspectRatio")
    private String mantainAspectRatio;

    @XmlAttribute(name = "codec")
    private String codec;

    @XmlAttribute(name = "apiFramework")
    private String apiFramework;

    @XmlJavaTypeAdapter(AdapterCDATA.class)
    @XmlElement
    private String value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBitrate() {
        return bitrate;
    }

    public void setBitrate(String bitrate) {
        this.bitrate = bitrate;
    }

    public String getMinBitrate() {
        return minBitrate;
    }

    public void setMinBitrate(String minBitrate) {
        this.minBitrate = minBitrate;
    }

    public String getMaxBitrate() {
        return maxBitrate;
    }

    public void setMaxBitrate(String maxBitrate) {
        this.maxBitrate = maxBitrate;
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

    public String getScalable() {
        return scalable;
    }

    public void setScalable(String scalable) {
        this.scalable = scalable;
    }

    public String getMantainAspectRatio() {
        return mantainAspectRatio;
    }

    public void setMantainAspectRatio(String mantainAspectRatio) {
        this.mantainAspectRatio = mantainAspectRatio;
    }

    public String getCodec() {
        return codec;
    }

    public void setCodec(String codec) {
        this.codec = codec;
    }

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
