package com.iab.openrtb.vast.ad.creative.linear;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iab.openrtb.vast.ad.creative.linear.mediafiles.InteractiveCreativeFile;
import com.iab.openrtb.vast.ad.creative.linear.mediafiles.MediaFile;
import com.iab.openrtb.vast.ad.creative.linear.mediafiles.Mezzanine;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "MediaFiles")
public class MediaFiles {

    @JsonProperty("Mezzanine")
    @XmlElement(name = "Mezzanine")
    private Mezzanine mezzanine;

    @XmlValue
    private List<MediaFile> mediaFiles;

    @JsonProperty("InteractiveCreativeFile")
    @XmlElement(name = "InteractiveCreativeFile")
    private InteractiveCreativeFile interactiveCreativeFile;

    public Mezzanine getMezzanine() {
        return mezzanine;
    }

    public void setMezzanine(Mezzanine mezzanine) {
        this.mezzanine = mezzanine;
    }

    public List<MediaFile> getMediaFiles() {
        return mediaFiles;
    }

    public void setMediaFiles(List<MediaFile> mediaFiles) {
        this.mediaFiles = mediaFiles;
    }

    public InteractiveCreativeFile getInteractiveCreativeFile() {
        return interactiveCreativeFile;
    }

    public void setInteractiveCreativeFile(InteractiveCreativeFile interactiveCreativeFile) {
        this.interactiveCreativeFile = interactiveCreativeFile;
    }
}
