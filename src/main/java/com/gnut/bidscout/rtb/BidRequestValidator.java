package com.gnut.bidscout.rtb;

import com.gnut.bidscout.model.AuctionRecord;
import com.gnut.bidscout.values.BidRequestError;
import com.iab.openrtb.request.BidRequest;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class BidRequestValidator {

    public boolean validateBidRequest(BidRequest bidRequest, AuctionRecord record) {
        if(bidRequest.getImp() == null) {
            record.getBidRequestErrors().add(BidRequestError.NO_IMPRESSION.value());
            return false;
        }

        return true;
    }

}
