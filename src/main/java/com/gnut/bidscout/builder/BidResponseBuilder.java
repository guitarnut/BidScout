package com.gnut.bidscout.builder;

import com.gnut.bidscout.html.AdMarkup;
import com.gnut.bidscout.model.Campaign;
import com.gnut.bidscout.model.Creative;
import com.iab.openrtb.request.BidRequest;
import com.iab.openrtb.request.Imp;
import com.iab.openrtb.response.Bid;
import com.iab.openrtb.response.BidResponse;
import com.iab.openrtb.response.SeatBid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class BidResponseBuilder {

    private final AdMarkup adMarkup;

    @Autowired
    public BidResponseBuilder(AdMarkup adMarkup) {
        this.adMarkup = adMarkup;
    }

    public BidResponse buildBidResponse(
            BigDecimal price,
            BidRequest bidRequest,
            Imp selectedImpression,
            Campaign campaign,
            Creative creative
    ) {
        final Bid bid = new Bid();
        bid.setId(selectedImpression.getId());
        bid.setImpid(selectedImpression.getId());
        bid.setAdid(creative.getAdId());
        bid.setAdomain(creative.getAdDomain());
        bid.setCat(creative.getIabCategories());
        bid.setAttr(creative.getAttr());
        bid.setCid(campaign.getCid());
        bid.setCrid(creative.getCrid());
        bid.setW(creative.getW());
        bid.setH(creative.getH());
        bid.setPrice(price);
        bid.setAdm(adMarkup.generateMarkup(price, bidRequest.getId(), campaign, creative));

        final SeatBid seatBid = new SeatBid();
        seatBid.setSeat(campaign.getSeat());
        seatBid.setBid(Arrays.asList(bid));

        final List<SeatBid> seatBids = new ArrayList<>();
        seatBids.add(seatBid);

        final BidResponse bidResponse = new BidResponse();
        bidResponse.setId(bidRequest.getId());

        if(Math.random()* 10 > 7) {
            bidResponse.setNbr(4);
        } else {
            bidResponse.setSeatbid(seatBids);
            bidResponse.setBidid(bidRequest.getId());
            bidResponse.setCur("USD");
        }

        return bidResponse;
    }
}
