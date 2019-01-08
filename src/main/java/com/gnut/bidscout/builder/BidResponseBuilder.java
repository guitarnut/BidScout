package com.gnut.bidscout.builder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.gnut.bidscout.db.XmlDao;
import com.gnut.bidscout.html.AdMarkup;
import com.gnut.bidscout.model.Campaign;
import com.gnut.bidscout.model.Creative;
import com.gnut.bidscout.model.Xml;
import com.google.common.base.Strings;
import com.iab.openrtb.request.BidRequest;
import com.iab.openrtb.request.Imp;
import com.iab.openrtb.response.Bid;
import com.iab.openrtb.response.BidResponse;
import com.iab.openrtb.response.SeatBid;
import com.iab.openrtb.vast.Vast;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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

    @Autowired
    public BidResponseBuilder(
            AdMarkup adMarkup,
            XmlDao xmlDao
    ) {
        this.adMarkup = adMarkup;
        this.xmlDao = xmlDao;
    }

    public BidResponse buildBidResponse(
            BigDecimal price,
            BidRequest bidRequest,
            Imp selectedImpression,
            Campaign campaign,
            Creative creative,
            String dealId
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
        bid.setPrice(price);

        if (creative.getW() == 0 && creative.getH() == 0) {
            if (creative.getType() == Creative.Type.DISPLAY) {
                if (bidRequest.getImp().get(0).getBanner().getW() != null
                        && bidRequest.getImp().get(0).getBanner().getW() != 0) {
                    bid.setW(bidRequest.getImp().get(0).getBanner().getW());
                    bid.setH(bidRequest.getImp().get(0).getBanner().getH());
                } else if (bidRequest.getImp().get(0).getBanner().getFormat() != null
                        && !bidRequest.getImp().get(0).getBanner().getFormat().isEmpty()) {
                    bid.setW(bidRequest.getImp().get(0).getBanner().getFormat().get(0).getW());
                    bid.setH(bidRequest.getImp().get(0).getBanner().getFormat().get(0).getH());
                }
            }
        } else {
            bid.setW(creative.getW());
            bid.setH(creative.getH());
        }

        if (creative.getType() == Creative.Type.VPAID || creative.getType() == Creative.Type.VAST) {
            if (!Strings.isNullOrEmpty(creative.getXml())) {
                bid.setAdm(creative.getXml());
            } else if (!Strings.isNullOrEmpty(creative.getXmlId())) {
                Xml xml = xmlDao.findByOwnerAndId(creative.getOwner(), creative.getXmlId());
                if (xml != null) {
                    String xmlString = "";
                    try {
                        xmlString = mapper.writeValueAsString(xml.getVast());
                    } catch (JsonProcessingException ex) {
                        // noop
                    }
                    bid.setAdm(xmlString);
                }
            }
        } else {
            bid.setAdm(adMarkup.generateDisplayMarkup(price, bidRequest.getId(), campaign, creative, bid));
        }

        if (!Strings.isNullOrEmpty(dealId)) {
            bid.setDealid(dealId);
        }

        final SeatBid seatBid = new SeatBid();
        seatBid.setSeat(campaign.getSeat());
        seatBid.setBid(Arrays.asList(bid));

        final List<SeatBid> seatBids = new ArrayList<>();
        seatBids.add(seatBid);

        final BidResponse bidResponse = new BidResponse();
        bidResponse.setId(bidRequest.getId());

        bidResponse.setSeatbid(seatBids);
        bidResponse.setBidid(bidRequest.getId());
        bidResponse.setCur("USD");

        bidResponse.setExt(new HashMap<>());
        bidResponse.getExt().put("campaign", campaign.getName());
        bidResponse.getExt().put("creative", creative.getName());

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
