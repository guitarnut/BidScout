package com.gnut.bidscout.service;

import com.gnut.bidscout.model.AuctionImp;
import com.gnut.bidscout.model.RequestTargetingData;
import com.google.common.base.Strings;
import com.iab.openrtb.request.BidRequest;
import com.iab.openrtb.request.Imp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Component
public class TargetingService {
    private final SyncService syncService;

    @Autowired
    public TargetingService(SyncService syncService) {
        this.syncService = syncService;
    }

    public RequestTargetingData generateTargetingData(AuctionImp auctionImp, HttpServletRequest request) {
        final RequestTargetingData data = new RequestTargetingData();
        final String userId = syncService.getUserCookieValue(request);
        final BidRequest bidRequest = auctionImp.getBidRequest();
        final Imp imp = auctionImp.getImpression();

        if (bidRequest.getApp() != null) {
            data.setPlatform(RequestTargetingData.Platform.INAPP);
            data.setDomain(bidRequest.getApp().getDomain());
            data.setPublisherId(bidRequest.getApp().getPublisher().getId());
        } else {
            data.setPlatform(RequestTargetingData.Platform.DESKTOP);
            data.setDomain(bidRequest.getSite().getDomain());
            data.setPublisherId(bidRequest.getSite().getPublisher().getId());
        }

        if (!Strings.isNullOrEmpty(bidRequest.getUser().getBuyeruid())) {
            data.setBuyeruid(bidRequest.getUser().getBuyeruid());
            data.setUserMatch(true);
        } else if (!Strings.isNullOrEmpty(userId)) {
            data.setUserMatch(true);
        }

        data.setWidths(Arrays.asList(imp.getBanner().getW()));
        data.setHeights(Arrays.asList(imp.getBanner().getH()));
        data.setSecure(request.isSecure());
        data.setBidfloor(imp.getBidfloor());
        data.setBadv(bidRequest.getBadv());
        data.setBattr(imp.getBanner().getBattr());
        data.setBtype(imp.getBanner().getBtype());
        data.setBadv(bidRequest.getBcat());

        if(imp.getPmp() != null) {
            data.setDealIds(imp.getPmp().getDeals());
        }

        return data;
    }
}
