package com.gnut.bidscout.model;

import com.iab.openrtb.request.BidRequest;
import com.iab.openrtb.request.Imp;
import com.iab.openrtb.response.Bid;

import java.math.BigDecimal;

public class AuctionImp {
    private BidRequest bidRequest;
    private Imp impression;
    private Campaign campaign;
    private Creative creative;
    private DisplayAd displayAd;
    private VideoAd videoAd;
    private Bid bid;
    private BigDecimal price;
    private String selectedDeal;

    public BidRequest getBidRequest() {
        return bidRequest;
    }

    public void setBidRequest(BidRequest bidRequest) {
        this.bidRequest = bidRequest;
    }

    public Imp getImpression() {
        return impression;
    }

    public void setImpression(Imp impression) {
        this.impression = impression;
    }

    public Campaign getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

    public Creative getCreative() {
        return creative;
    }

    public void setCreative(Creative creative) {
        this.creative = creative;
    }

    public DisplayAd getDisplayAd() {
        return displayAd;
    }

    public void setDisplayAd(DisplayAd displayAd) {
        this.displayAd = displayAd;
    }

    public VideoAd getVideoAd() {
        return videoAd;
    }

    public void setVideoAd(VideoAd videoAd) {
        this.videoAd = videoAd;
    }

    public Bid getBid() {
        return bid;
    }

    public void setBid(Bid bid) {
        this.bid = bid;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getSelectedDeal() {
        return selectedDeal;
    }

    public void setSelectedDeal(String selectedDeal) {
        this.selectedDeal = selectedDeal;
    }
}
