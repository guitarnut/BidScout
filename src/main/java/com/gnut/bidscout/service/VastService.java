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

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class VastService {

    private final XmlDao xmlDao;
    private final VastTagRecordDao vastTagRecordDao;
    private final VideoEventsBuilder eventsBuilder;
    private final UserAccountStatisticsService statisticsService;

    @Autowired
    public VastService(
            XmlDao xmlDao,
            VastTagRecordDao vastTagRecordDao,
            VideoEventsBuilder eventsBuilder,
            UserAccountStatisticsService statisticsService
    ) {
        this.xmlDao = xmlDao;
        this.vastTagRecordDao = vastTagRecordDao;
        this.eventsBuilder = eventsBuilder;
        this.statisticsService = statisticsService;
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

    public Vast serveVast(String account, String id, HttpServletRequest request) {
        // daily limit met?
        if (!statisticsService.addVastTagRequest(account)) {
            return null;
        }

        Xml xml = xmlDao.findByOwnerAndId(account, id);
        if (xml != null) {
            long timestamp = System.currentTimeMillis();

            VastTagRecord vastTagRecord = new VastTagRecord();
            vastTagRecord.setRequestTimestamp(timestamp);
            vastTagRecord.setVastName(xml.getName());

            vastTagRecord.setIp(request.getRemoteAddr());
            vastTagRecord.setUserAgent(request.getHeader("User-Agent"));
            vastTagRecord.setCookies(request.getHeader("Cookie"));
            vastTagRecord.setxForwardedFor(request.getHeader("X-Forwarded-For"));
            vastTagRecord.setHost(request.getHeader("Host"));
            vastTagRecord.setOwner(account);

            if (statisticsService.addVastTagRecord(account)) {
                vastTagRecordDao.save(vastTagRecord);
            }

            addLinearTrackingEventsToServedVast(xml.getVast(), vastTagRecord.getId());
            addVideoClickToServedVast(xml.getVast(), vastTagRecord.getId());
            return xml.getVast();
        } else {
            return null;
        }
    }

    public Vast getVast(String account, String id) {
        Xml xml = xmlDao.findByOwnerAndId(account, id);
        if (xml != null) {
            addLinearTrackingEventsToServedVast(xml.getVast(), "{request_id}");
            addVideoClickToServedVast(xml.getVast(), "{request_id}");
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
            statisticsService.removeVast(account);
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

    private void addImpressionToServedVast(Vast vast) {
    }

    public VastTagRecord getVastTagRecord(String account, String requestId) {
        return vastTagRecordDao.findFirstByOwnerAndId(account, requestId);
    }

    public List<VastTagRecord> getAllVastTagRecords(String account) {
        return vastTagRecordDao.findAllByOwner(account);
    }

    public void deleteVastTagRecord(String account, String vastId) {
        vastTagRecordDao.deleteByOwnerAndId(account, vastId);
        statisticsService.removeVastTagRecord(account);
    }

    public void deleteAllVastTagRecords(String account) {
        vastTagRecordDao.deleteAllByOwner(account);
        statisticsService.removeAllVastTagRecords(account);
    }
}
