package com.gnut.bidscout.builder;

import com.iab.openrtb.vast.ad.creative.linear.trackingevents.Tracking;
import com.iab.openrtb.vast.ad.creative.linear.videoclicks.ClickTracking;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class VideoEventsBuilder {
    private static final String VIDEO_EVENT_URL = "/event/video";
    private static final String VIDEO_CLICK_TRACKING_URL = "/event/video/clicked";

    private static final List<String> PLAYER_EVENTS = Arrays.asList(
            "mute",
            "unmute",
            "pause",
            "resume",
            "rewind",
            "skip",
            "playerExpand",
            "playerCollapse"
    );
    private static final List<String> LINEAR_EVENTS = Arrays.asList(
            "start",
            "firstQuartile",
            "midpoint",
            "thirdQuartile",
            "complete",
            "acceptInvitationLinear",
            "timeSpentViewing",
            "otherAdInteraction"
    );

    @Value("${service.values.host.events}")
    private String HOST;

    public ClickTracking getClickTracking() {
        final ClickTracking ct = new ClickTracking();
        ct.setId("BidScout");
        ct.setValue(HOST + VIDEO_CLICK_TRACKING_URL + "?cb=" + System.currentTimeMillis());
        return ct;
    }

    public List<Tracking> getTrackingEventsPlayer() {
        return buildEvents(PLAYER_EVENTS);
    }

    public List<Tracking> getTrackingEventsLinear() {
        return buildEvents(LINEAR_EVENTS);
    }

    private List<Tracking> buildEvents(List<String> events) {
        final List<Tracking> result = new ArrayList<>();
        PLAYER_EVENTS.forEach(e -> {
            Tracking t = new Tracking();
            t.setEvent(e);
            t.setValue(HOST + VIDEO_EVENT_URL + "/" + e + "?cb=" + System.currentTimeMillis());
            result.add(t);
        });
        return result;
    }
}