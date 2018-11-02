package com.gnut.bidscout.model;

import java.util.Set;

public class EligibleCampaignData {
    private Campaign campaign;
    private Set<Creative> creatives;
    private RequestTargetingData data;

    public Campaign getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

    public Set<Creative> getCreatives() {
        return creatives;
    }

    public void setCreatives(Set<Creative> creatives) {
        this.creatives = creatives;
    }

    public RequestTargetingData getData() {
        return data;
    }

    public void setData(RequestTargetingData data) {
        this.data = data;
    }
}
