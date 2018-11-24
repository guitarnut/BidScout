package com.gnut.bidscout.rtb;

import com.gnut.bidscout.model.AuctionRecord;
import com.gnut.bidscout.values.BidRequestError;
import com.google.common.base.Strings;
import com.iab.openrtb.request.BidRequest;
import org.springframework.stereotype.Component;

@Component
public class BidRequestValidator {

    public boolean validateBidRequest(BidRequest bidRequest, AuctionRecord record) {
        String id = Strings.isNullOrEmpty(bidRequest.getId()) ? "Unknown - " : bidRequest.getId() + " - ";
        // Failures
        if (bidRequest.getImp() == null) {
            record.getBidRequestErrors().add(id + BidRequestError.NO_IMPRESSION.value());
            return false;
        }

        // Warnings
        if (Strings.isNullOrEmpty(bidRequest.getUser().getBuyeruid())) {
            record.getBidRequestErrors().add(id + BidRequestError.NO_BUYERUID.value());
        }

        return true;
    }

}
