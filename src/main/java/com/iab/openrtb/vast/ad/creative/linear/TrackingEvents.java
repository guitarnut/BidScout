package com.iab.openrtb.vast.ad.creative.linear;

import com.iab.openrtb.vast.ad.creative.linear.trackingevents.Tracking;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "TrackingEvents")
public class TrackingEvents {

    @XmlElement
    private List<Tracking> tracking;

    public List<Tracking> getTracking() {
        return tracking;
    }

    public void setTracking(List<Tracking> tracking) {
        this.tracking = tracking;
    }
}
