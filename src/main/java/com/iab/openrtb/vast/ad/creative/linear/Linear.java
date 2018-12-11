package com.iab.openrtb.vast.ad.creative.linear;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.iab.openrtb.vast.ad.creative.linear.icons.Icon;
import com.iab.openrtb.vast.ad.creative.linear.mediafiles.InteractiveCreativeFile;
import com.iab.openrtb.vast.ad.creative.linear.mediafiles.MediaFile;
import com.iab.openrtb.vast.ad.creative.linear.mediafiles.Mezzanine;
import com.iab.openrtb.vast.ad.creative.linear.trackingevents.Tracking;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Linear {

    @JacksonXmlProperty(isAttribute = true)
    private String skipoffset;

    @JsonProperty("Duration")
    @JacksonXmlProperty(localName = "Duration")
    private Duration duration;

    @JsonProperty("AdParameters")
    @JacksonXmlProperty(localName = "AdParameters")
    private AdParameters adParameters;

    @JsonProperty("MediaFiles")
    @JacksonXmlElementWrapper(localName = "MediaFiles")
    @JacksonXmlProperty(localName = "MediaFile")
    private List<MediaFile> mediaFile;

    @JsonProperty("InteractiveCreativeFile")
    @JacksonXmlProperty(localName = "InteractiveCreativeFile")
    private InteractiveCreativeFile interactiveCreativeFile;

    @JsonProperty("Mezzanine")
    @JacksonXmlProperty(localName = "Mezzanine")
    private Mezzanine mezzanine;

    @JsonProperty("VideoClicks")
    @JacksonXmlProperty(localName = "VideoClicks")
    private VideoClicks videoClicks;

    @JsonProperty("TrackingEvents")
    @JacksonXmlElementWrapper(localName = "TrackingEvents")
    @JacksonXmlProperty(localName = "Tracking")
    private List<Tracking> trackingEvent;

    @JsonProperty("Icons")
    @JacksonXmlElementWrapper(localName = "Icons")
    @JacksonXmlProperty(localName = "Icon")
    private List<Icon> icons;

    public String getSkipoffset() {
        return skipoffset;
    }

    public void setSkipoffset(String skipoffset) {
        this.skipoffset = skipoffset;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public AdParameters getAdParameters() {
        return adParameters;
    }

    public void setAdParameters(AdParameters adParameters) {
        this.adParameters = adParameters;
    }

    public List<MediaFile> getMediaFile() {
        return mediaFile;
    }

    public void setMediaFile(List<MediaFile> mediaFile) {
        this.mediaFile = mediaFile;
    }

    public InteractiveCreativeFile getInteractiveCreativeFile() {
        return interactiveCreativeFile;
    }

    public void setInteractiveCreativeFile(InteractiveCreativeFile interactiveCreativeFile) {
        this.interactiveCreativeFile = interactiveCreativeFile;
    }

    public Mezzanine getMezzanine() {
        return mezzanine;
    }

    public void setMezzanine(Mezzanine mezzanine) {
        this.mezzanine = mezzanine;
    }

    public VideoClicks getVideoClicks() {
        return videoClicks;
    }

    public void setVideoClicks(VideoClicks videoClicks) {
        this.videoClicks = videoClicks;
    }

    public List<Tracking> getTrackingEvent() {
        return trackingEvent;
    }

    public void setTrackingEvent(List<Tracking> trackingEvent) {
        this.trackingEvent = trackingEvent;
    }

    public List<Icon> getIcons() {
        return icons;
    }

    public void setIcons(List<Icon> icons) {
        this.icons = icons;
    }
}
