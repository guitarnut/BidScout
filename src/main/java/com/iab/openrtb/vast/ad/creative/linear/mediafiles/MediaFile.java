package com.iab.openrtb.vast.ad.creative.linear.mediafiles;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MediaFile {

    @JacksonXmlProperty(isAttribute = true)
    private String id;

    @JacksonXmlProperty(isAttribute = true)
    private String delivery;

    @JacksonXmlProperty(isAttribute = true)
    private String type;

    @JacksonXmlProperty(isAttribute = true)
    private String bitrate;

    @JacksonXmlProperty(isAttribute = true)
    private String minBitrate;

    @JacksonXmlProperty(isAttribute = true)
    private String maxBitrate;

    @JacksonXmlProperty(isAttribute = true)
    private String width;

    @JacksonXmlProperty(isAttribute = true)
    private String height;

    @JacksonXmlProperty(isAttribute = true)
    private String scalable;

    @JacksonXmlProperty(isAttribute = true)
    private String mantainAspectRatio;

    @JacksonXmlProperty(isAttribute = true)
    private String codec;

    @JacksonXmlProperty(isAttribute = true)
    private String apiFramework;

    @JacksonXmlCData
    @JacksonXmlText
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
