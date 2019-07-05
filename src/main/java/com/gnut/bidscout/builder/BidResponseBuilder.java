package com.gnut.bidscout.builder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.gnut.bidscout.db.XmlDao;
import com.gnut.bidscout.html.AdMarkup;
import com.gnut.bidscout.model.Ad;
import com.gnut.bidscout.model.AuctionImp;
import com.gnut.bidscout.model.Creative;
import com.gnut.bidscout.model.Xml;
import com.gnut.bidscout.service.inventory.VastService;
import com.google.common.base.Strings;
import com.iab.openrtb.request.BidRequest;
import com.iab.openrtb.response.Bid;
import com.iab.openrtb.response.BidResponse;
import com.iab.openrtb.response.SeatBid;
import com.iab.openrtb.vast.Vast;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class BidResponseBuilder {
    public enum NBR {
        UNKNOWN_ERROR(0),
        TECHNICAL_ERROR(1),
        INVALID_REQUEST(2),
        KNOWN_WEB_SPIDER(3),
        SUSPECTED_NON_HUMAN_TRAFFIC(4),
        CLOUD_DATA_CENTER_OR_PROXY_IP(5),
        UNSUPPORTED_DEVICE(6),
        BLOCKED_PUBLISHER_OR_SITE(7),
        UNMATCHED_USER(8),
        DAILY_READER_CAP_MET(9),
        DAILY_DOMAIN_CAP_MET(10);

        private int code;

        NBR(int v) {
            this.code = v;
        }

        public int value() {
            return code;
        }
    }

    private static final JsonNodeFactory factory = JsonNodeFactory.instance;
    private static final XmlMapper mapper = new XmlMapper();
    private final AdMarkup adMarkup;
    private final XmlDao xmlDao;
    private final VastService vastService;

    @Autowired
    public BidResponseBuilder(
            AdMarkup adMarkup,
            XmlDao xmlDao,
            VastService vastService
    ) {
        this.adMarkup = adMarkup;
        this.xmlDao = xmlDao;
        this.vastService = vastService;
    }

    public BidResponse buildBidResponse(
            BidRequest bidRequest,
            Set<AuctionImp> auctionImp
    ) {
        if (auctionImp.isEmpty()) {
            return null;
        }

        final List<Bid> bids = new ArrayList<>();

        auctionImp.forEach(imp -> {
            final Bid bid = new Bid();
            final Ad ad = imp.getCreative().getType() == Creative.Type.DISPLAY ? imp.getDisplayAd() : imp.getVideoAd();

            final Creative creative = imp.getCreative();
            bid.setId(imp.getImpression().getId());
            bid.setImpid(imp.getImpression().getId());
            bid.setAdid(ad.getAdId());
            bid.setAdomain(ad.getAdDomain());
            bid.setCat(ad.getIabCategories());
            bid.setAttr(ad.getAttr());
            bid.setCid(imp.getCampaign().getCid());
            bid.setCrid(ad.getCrid());
            bid.setPrice(imp.getPrice());

            // Todo: Use interface?
            int w = (creative.getType() == Creative.Type.DISPLAY) ? imp.getDisplayAd().getWidth() : imp.getVideoAd().getWidth();
            int h = (creative.getType() == Creative.Type.DISPLAY) ? imp.getDisplayAd().getHeight() : imp.getVideoAd().getHeight();

            if (w == 0 && h == 0 && creative.getType() == Creative.Type.DISPLAY) {
                if (bidRequest.getImp().get(0).getBanner().getW() != null
                        && bidRequest.getImp().get(0).getBanner().getW() != 0) {
                    bid.setW(bidRequest.getImp().get(0).getBanner().getW());
                    bid.setH(bidRequest.getImp().get(0).getBanner().getH());
                } else if (bidRequest.getImp().get(0).getBanner().getFormat() != null
                        && !bidRequest.getImp().get(0).getBanner().getFormat().isEmpty()) {
                    bid.setW(bidRequest.getImp().get(0).getBanner().getFormat().get(0).getW());
                    bid.setH(bidRequest.getImp().get(0).getBanner().getFormat().get(0).getH());
                }
            } else {
                bid.setW(w);
                bid.setH(h);
            }

            if (creative.getType() == Creative.Type.VAST) {
                Vast vast = vastService.createVastDocument(imp.getVideoAd());
                if (vast != null) {
                    vastService.addLinearTrackingEventsToCreativeXML(vast, bidRequest.getId());
                    vastService.addVideoClickToCreativeXML(vast, bidRequest.getId());
                    vastService.addImpressionToCreativeXML(
                            vast,
                            imp.getCampaign().getOwner(),
                            bidRequest.getId(),
                            imp.getImpression().getId(),
                            imp.getCampaign().getId(),
                            imp.getCreative().getId(),
                            String.valueOf(imp.getPrice())
                    );

                    String xmlString = "";
                    try {
                        xmlString = mapper.writeValueAsString(vast);
                    } catch (JsonProcessingException ex) {
                        // noop
                    }
                    bid.setAdm(xmlString);
                }
            } else {
                bid.setAdm(adMarkup.generateDisplayMarkup(
                        imp.getPrice(),
                        bidRequest.getId(),
                        imp.getCampaign(),
                        creative,
                        imp.getDisplayAd(),
                        bid
                ));
            }

            if (!Strings.isNullOrEmpty(imp.getSelectedDeal())) {
                bid.setDealid(imp.getSelectedDeal());
            }

            bid.getExt().put("campaign", imp.getCampaign().getName());
            bid.getExt().put("creative", creative.getName());
            bids.add(bid);
        });

        if (bids.isEmpty()) {
            return null;
        }

        final SeatBid seatBid = new SeatBid();
        seatBid.setSeat(auctionImp.iterator().next().getCampaign().getSeat());
        seatBid.setBid(bids);

        final List<SeatBid> seatBids = new ArrayList<>();
        seatBids.add(seatBid);

        final BidResponse bidResponse = new BidResponse();
        bidResponse.setId(bidRequest.getId());

        bidResponse.setSeatbid(seatBids);
        bidResponse.setBidid(bidRequest.getId());
        bidResponse.setCur("USD");

        bidResponse.setExt(new HashMap<>());

        return bidResponse;
    }

    public BidResponse buildNBRBidResponse(
            BidRequest bidRequest,
            String message,
            NBR nbr
    ) {
        final BidResponse bidResponse = new BidResponse();
        if (bidRequest == null || Strings.isNullOrEmpty(bidRequest.getId())) {
            bidResponse.setId("1");
        } else {
            bidResponse.setId(bidRequest.getId());
        }

        bidResponse.setNbr(nbr.value());
        bidResponse.setExt(new HashMap<>());
        bidResponse.getExt().put("cause", message);

        return bidResponse;
    }
}
