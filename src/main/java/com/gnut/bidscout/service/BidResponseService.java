package com.gnut.bidscout.service;

import com.gnut.bidscout.builder.BidResponseBuilder;
import com.gnut.bidscout.model.AuctionImp;
import com.iab.openrtb.request.BidRequest;
import com.iab.openrtb.response.BidResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class BidResponseService {
    private final BidResponseBuilder builder;

    @Autowired
    public BidResponseService(BidResponseBuilder builder) {
        this.builder = builder;
    }

    public BidResponse buildBidResponse(
            BidRequest bidRequest,
            Set<AuctionImp> auctionImp
    ) {
        return builder.buildBidResponse(bidRequest, auctionImp);
    }

    public BidResponse buildNBRBidResponse(
            BidRequest bidRequest,
            BidResponseBuilder.NBR nbr,
            String message
    ) {
        return builder.buildNBRBidResponse(bidRequest, message, nbr);
    }
}
