package com.gnut.bidscout.service;

import com.gnut.bidscout.builder.VideoEventsBuilder;
import com.gnut.bidscout.db.VastTagRecordDao;
import com.gnut.bidscout.db.XmlDao;
import com.gnut.bidscout.model.VastTagRecord;
import com.gnut.bidscout.model.Xml;
import com.iab.openrtb.vast.Vast;
import com.iab.openrtb.vast.ad.creative.linear.VideoClicks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class VastService {

    private final XmlDao xmlDao;
    private final VastTagRecordDao vastTagRecordDao;
    private final VideoEventsBuilder eventsBuilder;

    @Autowired
    public VastService(XmlDao xmlDao, VastTagRecordDao vastTagRecordDao, VideoEventsBuilder eventsBuilder) {
        this.xmlDao = xmlDao;
        this.vastTagRecordDao = vastTagRecordDao;
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
            long timestamp = System.currentTimeMillis();

            VastTagRecord vastTagRecord = new VastTagRecord();
            vastTagRecord.setRequestTimestamp(timestamp);
            vastTagRecord.setVastName(xml.getName());

            vastTagRecordDao.save(vastTagRecord);

            addLinearTrackingEventsToServedVast(xml.getVast(), vastTagRecord.getId());
            addVideoClickToServedVast(xml.getVast(), vastTagRecord.getId());
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

    private void addLinearTrackingEventsToServedVast(Vast vast, String requestId) {
        vast.getAd().getInLine().getCreative().get(0).getLinear().setTrackingEvent(
                eventsBuilder.getTrackingEventsLinear(requestId)
        );
        vast.getAd().getInLine().getCreative().get(0).getLinear().getTrackingEvent().addAll(
                eventsBuilder.getTrackingEventsPlayer(requestId)
        );
    }

    private void addVideoClickToServedVast(Vast vast, String requestId) {
        vast.getAd().getInLine().getCreative().get(0).getLinear().setVideoClicks(
                new VideoClicks()
        );
        vast.getAd().getInLine().getCreative().get(0).getLinear().getVideoClicks().setClickTracking(
                eventsBuilder.getClickTracking(requestId)
        );
    }

    private void addVImpressionToServedVast(Vast vast) {
    }
}
