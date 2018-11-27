package com.iab.openrtb.vast.ad.creative.linear;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iab.openrtb.vast.ad.creative.linear.mediafiles.InteractiveCreativeFile;
import com.iab.openrtb.vast.ad.creative.linear.mediafiles.MediaFile;
import com.iab.openrtb.vast.ad.creative.linear.mediafiles.Mezzanine;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "MediaFiles")
public class MediaFiles {

    @JsonProperty("Mezzanine")
    @XmlElement(name = "Mezzanine")
    private Mezzanine mezzanine;

    @JsonProperty("MediaFile")
    @XmlElement(name = "MediaFile")
    private MediaFile mediaFile;

    @JsonProperty("InteractiveCreativeFile")
    @XmlElement(name = "InteractiveCreativeFile")
    private InteractiveCreativeFile interactiveCreativeFile;

    public Mezzanine getMezzanine() {
        return mezzanine;
    }

    public void setMezzanine(Mezzanine mezzanine) {
        this.mezzanine = mezzanine;
    }

    public MediaFile getMediaFile() {
        return mediaFile;
    }

    public void setMediaFile(MediaFile mediaFile) {
        this.mediaFile = mediaFile;
    }

    public InteractiveCreativeFile getInteractiveCreativeFile() {
        return interactiveCreativeFile;
    }

    public void setInteractiveCreativeFile(InteractiveCreativeFile interactiveCreativeFile) {
        this.interactiveCreativeFile = interactiveCreativeFile;
    }
}