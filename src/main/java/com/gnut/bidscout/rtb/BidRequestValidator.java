package com.gnut.bidscout.rtb;

import com.gnut.bidscout.model.AuctionRecord;
import com.gnut.bidscout.values.BidRequestError;
import com.google.common.base.Strings;
import com.iab.openrtb.request.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class BidRequestValidator {
    private final List<String> INVALID_IPS = Arrays.asList("0:0:0:0:0:0:0:1", "127.0.0.1");
    private final String INVALID_DOMAIN = "localhost";

    public boolean validateBidRequest(BidRequest bidRequest, AuctionRecord record) {
        // Failures
        validateBidRequestObject(bidRequest, record);

        if (bidRequest.getImp() != null && !bidRequest.getImp().isEmpty()) {
            for (Imp i : bidRequest.getImp()) {
                validateImpObject(i, record);
            }
        }

        if (bidRequest.getSite() != null) {
            validateSiteObject(bidRequest.getSite(), record);
        } else if (bidRequest.getApp() != null) {
            validateAppObject(bidRequest.getApp(), record);
        }

        if (!record.getBidRequestErrors().isEmpty()) {
            return false;
        } else {
            // Warnings
            checkBidRequestObjectForWarnings(bidRequest, record);
            checkUserObjectForWarnings(bidRequest.getUser(), record);
            checkDeviceObjectForWarnings(bidRequest.getDevice(), record);

            for (Imp i : bidRequest.getImp()) {
                checkImpObjectForWarnings(i, record);
                if (i.getBanner() != null) {
                    checkBannerObjectForWarnings(i.getBanner(), record);
                } else if (i.getVideo() != null) {
                    checkVideoObjectForWarnings(i.getVideo(), record);
                }
            }

            if (bidRequest.getApp() != null) {
                checkAppObjectForWarnings(bidRequest.getApp(), record);
            } else if (bidRequest.getSite() != null) {
                checkSiteObjectForWarnings(bidRequest.getSite(), record);
            }

            return true;
        }
    }

    private void validateBidRequestObject(BidRequest b, AuctionRecord record) {
        if (Strings.isNullOrEmpty(b.getId())) {
            record.getBidRequestErrors().add(BidRequestError.BID_REQUEST_ID_MISSING.value());
        }

        if (b.getDevice() == null) {
            record.getBidRequestErrors().add(BidRequestError.NO_IMPRESSION.value());
        }

        if (b.getSite() == null && b.getApp() == null) {
            record.getBidRequestErrors().add(BidRequestError.NO_APP_OR_SITE.value());
        }

        if (b.getSite() != null && b.getApp() != null) {
            record.getBidRequestErrors().add(BidRequestError.APP_AND_SITE.value());
        }

        if (b.getUser() == null) {
            record.getBidRequestErrors().add(BidRequestError.NO_USER.value());
        }

        if (b.getImp() == null || b.getImp().isEmpty()) {
            record.getBidRequestErrors().add(BidRequestError.NO_IMPRESSION.value());
        }
    }

    private void validateImpObject(Imp i, AuctionRecord record) {
        if (Strings.isNullOrEmpty(i.getId())) {
            record.getBidRequestErrors().add(BidRequestError.NO_IMPRESSION_ID.value());
        } else if (i.getBanner() == null && i.getVideo() == null) {
            record.getBidRequestErrors().add(BidRequestError.NO_BANNER_OR_VIDEO.value());
        } else if (i.getBanner() != null && i.getVideo() != null) {
            record.getBidRequestErrors().add(BidRequestError.BANNER_AND_VIDEO.value());
        } else if (i.getBanner() != null) {
            validateBannerObject(i.getBanner(), record);
        } else if (i.getVideo() != null) {
            validateVideoObject(i.getVideo(), record);
        }
    }

    private void checkBidRequestObjectForWarnings(BidRequest b, AuctionRecord record) {
        if (b.getCur() == null || b.getCur().isEmpty()) {
            record.getBidRequestErrors().add(BidRequestError.NO_CURRENCY.value());
        }
    }

    private void checkImpObjectForWarnings(Imp i, AuctionRecord record) {
        if (i.getBidfloor() == null || i.getBidfloor() == 0) {
            record.getBidRequestErrors().add(BidRequestError.NO_IMPRESSION_BID_FLOOR.value());
        }
    }

    private void validateBannerObject(Banner b, AuctionRecord record) {
        if (Strings.isNullOrEmpty(b.getId())) {
            record.getBidRequestErrors().add(BidRequestError.NO_BANNER_ID.value());
        }

        if (b.getW() == null || b.getW() == 0 || b.getH() == null || b.getH() == 0) {
            record.getBidRequestErrors().add(BidRequestError.INVALID_BANNER_SIZE.value());
        }
    }

    private void checkBannerObjectForWarnings(Banner b, AuctionRecord record) {

    }

    private void validateVideoObject(Video v, AuctionRecord record) {

    }

    private void checkVideoObjectForWarnings(Video v, AuctionRecord record) {

    }

    private void validateSiteObject(Site s, AuctionRecord record) {
        if (Strings.isNullOrEmpty(s.getId())) {
            record.getBidRequestErrors().add(BidRequestError.NO_SITE_ID.value());
        }
        if (s.getPublisher() == null) {
            record.getBidRequestErrors().add(BidRequestError.NO_PUBLISHER.value());
        }
    }

    private void checkSiteObjectForWarnings(Site s, AuctionRecord record) {
        if (Strings.isNullOrEmpty(s.getDomain())) {
            record.getBidRequestErrors().add(BidRequestError.NO_SITE_DOMAIN.value());
        } else if (s.getDomain().toLowerCase().contains(INVALID_DOMAIN)) {
            record.getBidRequestErrors().add(BidRequestError.INVALID_SITE_DOMAIN.value());
        }
    }

    private void validateAppObject(App a, AuctionRecord record) {
        if (Strings.isNullOrEmpty(a.getId())) {
            record.getBidRequestErrors().add(BidRequestError.NO_APP_ID.value());
        }
        if (a.getPublisher() == null) {
            record.getBidRequestErrors().add(BidRequestError.NO_PUBLISHER.value());
        }
    }

    private void checkAppObjectForWarnings(App a, AuctionRecord record) {
        if (Strings.isNullOrEmpty(a.getDomain())) {
            record.getBidRequestErrors().add(BidRequestError.NO_APP_DOMAIN.value());
        } else if (a.getDomain().toLowerCase().contains(INVALID_DOMAIN)) {
            record.getBidRequestErrors().add(BidRequestError.INVALID_APP_DOMAIN.value());
        }
        if (Strings.isNullOrEmpty(a.getBundle())) {
            record.getBidRequestErrors().add(BidRequestError.NO_APP_BUNDLE.value());
        }
        if (Strings.isNullOrEmpty(a.getStoreurl())) {
            record.getBidRequestErrors().add(BidRequestError.NO_APP_STORE_URL.value());
        }
    }

    private void checkDeviceObjectForWarnings(Device d, AuctionRecord record) {
        if (Strings.isNullOrEmpty(d.getIp())) {
            record.getBidRequestErrors().add(BidRequestError.NO_DEVICE_IP.value());
        } else {
            for (String ip : INVALID_IPS) {
                if (d.getIp().contains(ip)) {
                    record.getBidRequestErrors().add(BidRequestError.DEVICE_LOCALHOST_IP.value());
                }
            }
        }
        if (Strings.isNullOrEmpty(d.getUa())) {
            record.getBidRequestErrors().add(BidRequestError.NO_DEVICE_USER_AGENT.value());
        }
        if (Strings.isNullOrEmpty(d.getIfa())) {
            record.getBidRequestErrors().add(BidRequestError.NO_DEVICE_IDFA.value());
        }
    }

    private void checkUserObjectForWarnings(User u, AuctionRecord record) {
        if (Strings.isNullOrEmpty(u.getId())) {
            record.getBidRequestErrors().add(BidRequestError.NO_USER_ID.value());
        }

        if (Strings.isNullOrEmpty(u.getBuyeruid())) {
            record.getBidRequestErrors().add(BidRequestError.NO_BUYERUID.value());
        }
    }
}
