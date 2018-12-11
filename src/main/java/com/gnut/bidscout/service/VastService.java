package com.gnut.bidscout.service;

import com.gnut.bidscout.builder.VideoEventsBuilder;
import com.gnut.bidscout.db.XmlDao;
import com.gnut.bidscout.model.Xml;
import com.iab.openrtb.vast.Vast;
import com.iab.openrtb.vast.ad.creative.linear.VideoClicks;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class VastService {

    private final XmlDao xmlDao;
    private final VideoEventsBuilder eventsBuilder;

    public VastService(
            XmlDao xmlDao,
            VideoEventsBuilder eventsBuilder
    ) {
        this.xmlDao = xmlDao;
        this.eventsBuilder = eventsBuilder;
    }

    public void saveXml(String account, Xml xml) {
        xml.setOwner(account);
        xmlDao.save(xml);
    }

    public Map<String, String> getAllVastDocuments(String account) {
        List<Xml> allXml = xmlDao.findAllByOwner(account);
        if (allXml != null) {
            Map<String, String> results = new HashMap<>();
            allXml.forEach(x -> {
                results.put(x.getId(), x.getName());
            });
            return results;
        } else {
            return Collections.emptyMap();
        }
    }

    public Vast serveVast(String account, String id) {
        Xml xml = xmlDao.findByOwnerAndId(account, id);
        if (xml != null) {
            //addLinearTrackingEventsToServedVast(xml.getVast());
            //addVideoClickToServedVast(xml.getVast());
            return xml.getVast();
        } else {
            return null;
        }
    }

    public Xml getXml(String account, String xmlId) {
        return xmlDao.findByOwnerAndId(account, xmlId);
    }

    public void deleteXml(String account, String xmlId) {
        Xml result = xmlDao.findByOwnerAndId(account, xmlId);
        if (result != null) {
            xmlDao.delete(result);
        }
    }

    private void addLinearTrackingEventsToServedVast(Vast vast) {
        vast.getAd().getInLine().getCreatives().get(0).getLinear().setTrackingEvents(
            eventsBuilder.getTrackingEventsLinear()
        );
        vast.getAd().getInLine().getCreatives().get(0).getLinear().getTrackingEvents().addAll(
                eventsBuilder.getTrackingEventsPlayer()
        );
    }

    private void addVideoClickToServedVast(Vast vast) {
        vast.getAd().getInLine().getCreatives().get(0).getLinear().setVideoClicks(
                new VideoClicks()
        );
        vast.getAd().getInLine().getCreatives().get(0).getLinear().getVideoClicks().setClickTracking(
                eventsBuilder.getClickTracking()
        );
    }
}
