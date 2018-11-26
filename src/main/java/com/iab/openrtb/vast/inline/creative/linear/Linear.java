package com.iab.openrtb.vast.inline.creative.linear;

import com.iab.openrtb.vast.inline.creative.linear.icons.Icon;
import com.iab.openrtb.vast.inline.creative.linear.mediafiles.MediaFile;
import com.iab.openrtb.vast.inline.creative.linear.trackingevents.Tracking;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Linear")
public class Linear {

    @XmlAttribute(name="skipoffset")
    private String skipoffset;

    @XmlElement
    private Duration duration;

    @XmlElement
    private AdParameters adParameters;

    @XmlElement
    private List<MediaFile> mediaFiles;

    @XmlElement
    private VideoClicks videoClicks;

    @XmlElement
    private List<Tracking> trackingEvents;

    @XmlElement
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
