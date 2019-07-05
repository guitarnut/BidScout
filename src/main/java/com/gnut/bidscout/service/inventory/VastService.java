package com.gnut.bidscout.service.inventory;

import com.gnut.bidscout.builder.VideoEventsBuilder;
import com.gnut.bidscout.db.VastTagRecordDao;
import com.gnut.bidscout.db.VideoAdDao;
import com.gnut.bidscout.db.XmlDao;
import com.gnut.bidscout.model.Creative;
import com.gnut.bidscout.model.VastTagRecord;
import com.gnut.bidscout.model.VideoAd;
import com.gnut.bidscout.model.Xml;
import com.gnut.bidscout.service.user.UserAccountStatisticsService;
import com.iab.openrtb.vast.Ad;
import com.iab.openrtb.vast.InLine;
import com.iab.openrtb.vast.Vast;
import com.iab.openrtb.vast.ad.AdSystem;
import com.iab.openrtb.vast.ad.AdTitle;
import com.iab.openrtb.vast.ad.Impression;
import com.iab.openrtb.vast.ad.creative.UniversalAdId;
import com.iab.openrtb.vast.ad.creative.linear.Linear;
import com.iab.openrtb.vast.ad.creative.linear.VideoClicks;
import com.iab.openrtb.vast.ad.creative.linear.mediafiles.MediaFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Component
public class VastService {


    //    @Autowired
//    public JavaMailSender emailSender;
    private final XmlDao xmlDao;
    private final VastTagRecordDao vastTagRecordDao;
    private final VideoAdDao videoAdDao;
    private final VideoEventsBuilder eventsBuilder;
    private final UserAccountStatisticsService statisticsService;

    @Autowired
    public VastService(
            XmlDao xmlDao,
            VastTagRecordDao vastTagRecordDao,
            VideoAdDao videoAdDao,
            VideoEventsBuilder eventsBuilder,
            UserAccountStatisticsService statisticsService
    ) {
        this.xmlDao = xmlDao;
        this.vastTagRecordDao = vastTagRecordDao;
        this.videoAdDao = videoAdDao;
        this.eventsBuilder = eventsBuilder;
        this.statisticsService = statisticsService;
    }

    public void saveXml(String account, Xml xml) {
        xml.setOwner(account);
        xmlDao.save(xml);
    }

    public Vast createVastDocument(HttpServletResponse response, String id) {
        Optional<VideoAd> videoAd = videoAdDao.findById(id);
        if (videoAd.isPresent()) {
            return createVastDocument(videoAd.get());
        } else {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }
    }

    public Vast createVastDocument(VideoAd v) {
        Vast vast = new Vast();
        InLine inLine = new InLine();
        com.iab.openrtb.vast.ad.creative.Creative creative = new com.iab.openrtb.vast.ad.creative.Creative();
        Linear linear = new Linear();
        MediaFile mediaFile = new MediaFile();
        Ad ad = new Ad();
        AdSystem adSystem = new AdSystem();
        AdTitle adTitle = new AdTitle();

        mediaFile.setDelivery(v.getDelivery());
        mediaFile.setType(v.getType());
        mediaFile.setBitrate(String.valueOf(v.getBitrate()));
        mediaFile.setMinBitrate(String.valueOf(v.getMinBitrate()));
        mediaFile.setMaxBitrate(String.valueOf(v.getMaxBitrate()));
        mediaFile.setWidth(String.valueOf(v.getWidth()));
        mediaFile.setHeight(String.valueOf(v.getHeight()));
        mediaFile.setScalable(String.valueOf(v.isScalable()));
        mediaFile.setCodec(v.getCodec());
        mediaFile.setApiFramework("TEMP");
        mediaFile.setValue(v.getVideoFile());

        linear.setMediaFile(Arrays.asList(mediaFile));

        creative.setLinear(linear);
        creative.setSequence("1");
        creative.setAdId(v.getAdId());

        adSystem.setValue("AuctionScout");
        adTitle.setValue(v.getName());

        inLine.setAdSystem(adSystem);
        inLine.setAdTitle(adTitle);
        inLine.setCreative(Arrays.asList(creative));

        ad.setSequence("1");
        ad.setInLine(inLine);

        vast.setVersion("3");
        vast.setAd(ad);

        return vast;
    }

    public void deleteVASTDocument(Creative creative) {
        // Todo: Remove existing vast when creative is changed from video to display
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
            addImpressionToServedVast(xml.getVast(), vastTagRecord.getId());

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
            addImpressionToServedVast(xml.getVast(), "{request_id}");
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

    public void addLinearTrackingEventsToCreativeXML(Vast vast, String requestId) {
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

    public void addVideoClickToCreativeXML(Vast vast, String requestId) {
        vast.getAd().getInLine().getCreative().get(0).getLinear().setVideoClicks(
                new VideoClicks()
        );
        vast.getAd().getInLine().getCreative().get(0).getLinear().getVideoClicks().setClickTracking(
                eventsBuilder.getClickTracking(requestId)
        );
    }


    /**
     * String id,
     * HttpServletRequest request,
     * HttpServletResponse response,
     * String bid,
     * String campaign,
     * String creative,
     * String bidPrice,
     * String cp,
     * String cb
     */
    private void addImpressionToServedVast(Vast vast, String requestId) {
        final Impression impression = eventsBuilder.getVastTagImpression(
                requestId
        );
        vast.getAd().getInLine().setImpression(impression);
    }

    public void addImpressionToCreativeXML(
            Vast vast,
            String id,
            String bidRequestId,
            String impressionId,
            String campaign,
            String creative,
            String bidPrice
    ) {
        final Impression impression = eventsBuilder.getImpression(
                id, bidRequestId, impressionId, campaign, creative, bidPrice
        );
        vast.getAd().getInLine().setImpression(impression);
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

//    public void sendEmail() {
//            SimpleMailMessage message = new SimpleMailMessage();
//            message.setFrom("guitar_nut@hotmail.com");
//            message.setTo("guitar_nut@hotmail.com");
//            message.setSubject("Test");
//            message.setText("Hello-\nThis is your test email.");
//            emailSender.send(message);
//    }
}
