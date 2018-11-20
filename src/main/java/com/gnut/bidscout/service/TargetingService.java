package com.gnut.bidscout.service;

import com.gnut.bidscout.model.RequestTargetingData;
import com.google.common.base.Strings;
import com.iab.openrtb.request.BidRequest;
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

    public RequestTargetingData generateTargetingData(String publisher, BidRequest bidRequest, HttpServletRequest request) {
        final RequestTargetingData data = new RequestTargetingData();
        final String userId = syncService.getUserCookieValue(request);

        data.setPublisher(publisher);

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

        data.setWidths(Arrays.asList(bidRequest.getImp().get(0).getBanner().getW()));
        data.setHeights(Arrays.asList(bidRequest.getImp().get(0).getBanner().getH()));
        data.setSecure(bidRequest.getImp().get(0).getSecure() == 1);
        data.setBidfloor(bidRequest.getImp().get(0).getBidfloor());
        data.setBadv(bidRequest.getBadv());
        data.setBattr(bidRequest.getImp().get(0).getBanner().getBattr());
        data.setBtype(bidRequest.getImp().get(0).getBanner().getBtype());

        if(bidRequest.getImp().get(0).getPmp() != null) {
            data.setDealIds(bidRequest.getImp().get(0).getPmp().getDeals());
        }

        return data;
    }
}
