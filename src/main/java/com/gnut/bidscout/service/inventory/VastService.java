package com.gnut.bidscout.service.inventory;

import com.gnut.bidscout.builder.VideoEventsBuilder;
import com.gnut.bidscout.db.VastTagRecordDao;
import com.gnut.bidscout.db.VideoAdDao;
import com.gnut.bidscout.model.VastTagRecord;
import com.gnut.bidscout.model.VideoAd;
import com.gnut.bidscout.service.user.AccountStatisticsService;
import com.iab.openrtb.vast.Ad;
import com.iab.openrtb.vast.InLine;
import com.iab.openrtb.vast.Vast;
import com.iab.openrtb.vast.ad.AdSystem;
import com.iab.openrtb.vast.ad.AdTitle;
import com.iab.openrtb.vast.ad.Impression;
import com.iab.openrtb.vast.ad.creative.linear.Linear;
import com.iab.openrtb.vast.ad.creative.linear.VideoClicks;
import com.iab.openrtb.vast.ad.creative.linear.mediafiles.MediaFile;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;

@Component
public class VastService {

    private final VastTagRecordDao vastTagRecordDao;
    private final VideoAdDao videoAdDao;
    private final VideoEventsBuilder eventsBuilder;
    private final AccountStatisticsService statisticsService;

    @Autowired
    public VastService(
            VastTagRecordDao vastTagRecordDao,
            VideoAdDao videoAdDao,
            VideoEventsBuilder eventsBuilder,
            AccountStatisticsService statisticsService
    ) {
        this.vastTagRecordDao = vastTagRecordDao;
        this.videoAdDao = videoAdDao;
        this.eventsBuilder = eventsBuilder;
        this.statisticsService = statisticsService;
    }

    public Vast createVastDocument(HttpServletRequest request, HttpServletResponse response, String id) {
        Optional<VideoAd> videoAd = videoAdDao.findById(id);
        if (videoAd.isPresent()) {
            VideoAd v = videoAd.get();
            // daily limit met?
            if (!statisticsService.addVastTagRequest("Admin")) {
                return null;
            }

            Vast vast = createVastDocument(videoAd.get());

            long timestamp = System.currentTimeMillis();
            VastTagRecord vastTagRecord = new VastTagRecord();
            vastTagRecord.setTagId(id);
            vastTagRecord.setRequestId(new ObjectId().toString());
            vastTagRecord.setRequestTimestamp(timestamp);
            vastTagRecord.setVastName(v.getName());
            vastTagRecord.setIp(request.getRemoteAddr());
            vastTagRecord.setUserAgent(request.getHeader("User-Agent"));
            vastTagRecord.setCookies(request.getHeader("Cookie"));
            vastTagRecord.setxForwardedFor(request.getHeader("X-Forwarded-For"));
            vastTagRecord.setHost(request.getHeader("Host"));
            vastTagRecord.setOwner(videoAd.get().getOwner());

            if (statisticsService.addVastTagRecord("Admin")) {
                vastTagRecordDao.save(vastTagRecord);
            }

            addLinearTrackingEventsToServedVast(vast, vastTagRecord.getRequestId());
            addVideoClickToServedVast(vast, vastTagRecord.getRequestId());
            addImpressionToServedVast(vast, vastTagRecord.getRequestId());

            return vast;
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
        if (v.getMinBitrate() > 0) {
            mediaFile.setMinBitrate(String.valueOf(v.getMinBitrate()));
        }
        if (v.getMaxBitrate() > 0) {
            mediaFile.setMaxBitrate(String.valueOf(v.getMaxBitrate()));
        }
        mediaFile.setWidth(String.valueOf(v.getWidth()));
        mediaFile.setHeight(String.valueOf(v.getHeight()));
        mediaFile.setScalable(String.valueOf(v.isScalable()));
        mediaFile.setCodec(v.getCodec());
//        mediaFile.setApiFramework("TEMP");
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
}
