package com.gnut.bidscout.service;

import com.gnut.bidscout.builder.BidResponseBuilder;
import com.gnut.bidscout.model.Campaign;
import com.gnut.bidscout.model.Creative;
import com.iab.openrtb.request.BidRequest;
import com.iab.openrtb.request.Imp;
import com.iab.openrtb.response.BidResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BidResponseService {
    private final BidResponseBuilder builder;

    @Autowired
    public BidResponseService(BidResponseBuilder builder) {
        this.builder = builder;
    }

    public BidResponse buildBidResponse(
            BigDecimal price,
            BidRequest bidRequest,
            Imp selectedImpression,
            Campaign campaign,
            Creative creative
    ) {
        return builder.buildBidResponse(price, bidRequest, selectedImpression, campaign, creative);
    }
}
