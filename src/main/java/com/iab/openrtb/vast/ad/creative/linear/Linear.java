package com.iab.openrtb.vast.ad.creative.linear;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iab.openrtb.vast.ad.creative.linear.icons.Icon;
import com.iab.openrtb.vast.ad.creative.linear.mediafiles.MediaFile;
import com.iab.openrtb.vast.ad.creative.linear.trackingevents.Tracking;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Linear")
public class Linear {

    @XmlAttribute(name="skipoffset")
    private String skipoffset;

    @JsonProperty("Duration")
    @XmlElement(name = "Duration")
    private Duration duration;

    @JsonProperty("AdParameters")
    @XmlElement(name = "AdParameters")
    private AdParameters adParameters;

    @JsonProperty("MediaFiles")
    @XmlElementWrapper(name="MediaFiles")
    @XmlElement(name="MediaFile")
    private List<MediaFile> mediaFiles;

    @JsonProperty("VideoClicks")
    @XmlElement(name = "VideoClicks")
    private VideoClicks videoClicks;

    @JsonProperty("TrackingEvents")
    @XmlElement(name = "TrackingEvents")
    private List<Tracking> trackingEvents;

    @JsonProperty("Icons")
    @XmlElement(name = "Icons")
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

    public List<MediaFile> getMediaFiles() {
        return mediaFiles;
    }

    public void setMediaFiles(List<MediaFile> mediaFiles) {
        this.mediaFiles = mediaFiles;
    }

    public VideoClicks getVideoClicks() {
        return videoClicks;
    }

    public void setVideoClicks(VideoClicks videoClicks) {
        this.videoClicks = videoClicks;
    }

    public List<Tracking> getTrackingEvents() {
        return trackingEvents;
    }

    public void setTrackingEvents(List<Tracking> trackingEvents) {
        this.trackingEvents = trackingEvents;
    }

    public List<Icon> getIcons() {
        return icons;
    }

    public void setIcons(List<Icon> icons) {
        this.icons = icons;
    }
}
