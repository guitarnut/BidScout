package com.gnut.bidscout.service;

import com.gnut.bidscout.model.*;
import com.google.common.base.Strings;
import com.iab.openrtb.request.BidRequest;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class CampaignService {

    public Optional<EligibleCampaignData> targetCampaign(String publisher, BidRequest bidRequest) {
        final Campaign campaign = selectCampaign(bidRequest);
        final RequestTargetingData targetingData = generateTargetingData(publisher, bidRequest);

        if (campaignIsEligible(targetingData, campaign)) {
            final EligibleCampaignData campaignData = new EligibleCampaignData();
            campaignData.setCampaign(campaign);
            campaignData.setCreatives(getEligibleCampaignCreatives(targetingData, campaign));
            campaignData.setData(targetingData);
            return Optional.of(campaignData);
        } else {
            return Optional.empty();
        }
    }

    private RequestTargetingData generateTargetingData(String publisher, BidRequest bidRequest) {
        final RequestTargetingData data = new RequestTargetingData();

        data.setPublisher(publisher);

        if (bidRequest.getApp() != null) {
            data.setPlatform(RequestTargetingData.Platform.INAPP);
            data.setDomain(bidRequest.getApp().getDomain());
            data.setPublisherId(bidRequest.getApp().getPublisher().getId());
        } else {
            data.setPlatform(RequestTargetingData.Platform.DESKTOP);
            data.setDomain(bidRequest.getSite().getDomain());
            data.setPublisherId(bidRequest.getSite().getPublisher().getId());
        }

        data.setBuyeruid(bidRequest.getUser().getBuyeruid());
        data.setWidths(Arrays.asList(bidRequest.getImp().get(0).getBanner().getW()));
        data.setHeights(Arrays.asList(bidRequest.getImp().get(0).getBanner().getH()));
        data.setSecure(bidRequest.getImp().get(0).getSecure() == 1);
        data.setBidfloor(bidRequest.getImp().get(0).getBidfloor());
        data.setBadv(bidRequest.getBadv());
        data.setBattr(bidRequest.getImp().get(0).getBanner().getBattr());
        data.setBtype(bidRequest.getImp().get(0).getBanner().getBtype());

        return data;
    }

    private Campaign selectCampaign(BidRequest bidRequest) {
        Requirements requirements = new Requirements();
        requirements.setInapp(true);

        Creative creative = new Creative();
        creative.setAdDomain(Arrays.asList("mcdonalds.com"));
        creative.setAdId("mc_01");
        creative.setCreativeUrl("http://foo.com/img.jpg");
        creative.setCrid("mc100_oc");
        creative.setW(300);
        creative.setH(250);
        creative.setAttr(Arrays.asList(1));
        creative.setBtype(Arrays.asList(1));
        creative.setIabCategories(Arrays.asList("IAB1-1"));
        creative.setMinBid(0.01f);
        creative.setMaxBid(5.00f);
        creative.setRequirements(requirements);

        Campaign campaign = new Campaign();
        campaign.setCid("campaign_1");
        campaign.setSeat("16_13324");
        campaign.setCreatives(new HashSet<>());
        campaign.getCreatives().add(creative);
        campaign.setImpression("http://foo.com/imp");
        campaign.setRequirements(requirements);

        Set<String> publishers = new HashSet<>();
        publishers.add("bfio");
        campaign.setPublishers(publishers);

        return campaign;
    }

    private boolean campaignIsEligible(RequestTargetingData targetingData, Campaign campaign) {
        return campaign.getPublishers().contains(targetingData.getPublisher().toLowerCase())
                && isEligible(targetingData, campaign.getRequirements());
    }

    private Set<Creative> getEligibleCampaignCreatives(RequestTargetingData targetingData, Campaign campaign) {
        final Set<Creative> eligible = new HashSet<>();

        for (Creative c : campaign.getCreatives()) {
            final Requirements filter = c.getRequirements();

            if (!isEligible(targetingData, filter)) {
                continue;
            }

            if (c.getMaxBid() < targetingData.getBidfloor()) {
                continue;
            }

            if (!targetingData.getBadv().isEmpty()) {
                final AtomicInteger count = new AtomicInteger(0);
                c.getAdDomain().forEach(d -> {
                    if (targetingData.getBadv().contains(d)) {
                        count.getAndIncrement();
                    }
                });
                if (count.get() > 0) {
                    continue;
                }
            }

            if (!targetingData.getBattr().isEmpty()) {
                final AtomicInteger count = new AtomicInteger(0);
                c.getAttr().forEach(a -> {
                    if (targetingData.getBattr().contains(a)) {
                        count.getAndIncrement();
                    }
                });
                if (count.get() > 0) {
                    continue;
                }
            }

            // final, size targeting
            if (targetingData.getWidths().contains(c.getW()) && targetingData.getHeights().contains(c.getH())) {
                targetingData.getWidths().forEach(w -> {
                    int index = targetingData.getWidths().indexOf(w);
                    if (targetingData.getWidths().get(index) == c.getW() && targetingData.getHeights().get(index) == c.getH()) {
                        eligible.add(c);
                    }
                });
            }
        }
        return eligible;
    }

    private boolean isEligible(RequestTargetingData targetingData, Requirements filter) {
        final Date now = new Date();

        if (filter.getStartDate() != null && filter.getStartDate().getTime() < now.getTime()) {
            return false;
        } else if (filter.getEndDate() != null && filter.getEndDate().getTime() > now.getTime()) {
            return false;
        }

        // platform targeting
        if (targetingData.getPlatform().equals(RequestTargetingData.Platform.INAPP)) {
            if (!filter.isInapp()) {
                return false;
            } else if (!listContainsValue(filter.getBundleWhitelist(), targetingData.getPublisherId())) {
                return false;
            } else if (!listDoesNotContainValue(filter.getBundleBlacklist(), targetingData.getPublisherId())) {
                return false;
            }
        } else if (targetingData.getPlatform().equals(RequestTargetingData.Platform.DESKTOP)) {
            if (!filter.isDesktop()) {
                return false;
            } else if (!listContainsValue(filter.getDomainWhitelist(), targetingData.getPublisherId())) {
                return false;
            } else if (!listDoesNotContainValue(filter.getDomainBlacklist(), targetingData.getPublisherId())) {
                return false;
            }
        } else if (targetingData.getPlatform().equals(RequestTargetingData.Platform.MOBILE)) {
            if (!filter.isMobile()) {
                return false;
            } else if (!listContainsValue(filter.getDomainWhitelist(), targetingData.getPublisherId())) {
                return false;
            } else if (!listDoesNotContainValue(filter.getDomainBlacklist(), targetingData.getPublisherId())) {
                return false;
            }
        } else if (targetingData.getPlatform().equals(RequestTargetingData.Platform.CTV)) {
            if (!filter.isCtv()) {
                return false;
            } else {
                return false;
            }
        }

        // publisher white and black lists
        if (!listContainsValue(filter.getPublisherWhitelist(), targetingData.getPublisherId())) {
            return false;
        } else if (!listDoesNotContainValue(filter.getDomainBlacklist(), targetingData.getPublisherId())) {
            return false;
        }

        // user match, cookie value
        if (filter.isUserMatch() && Strings.isNullOrEmpty(targetingData.getBuyeruid())) {
            return false;
        }

        return true;
    }

    private boolean listContainsValue(String list, String val) {
        if (Strings.isNullOrEmpty(list)) {
            return true;
        } else {
            return list.contains(val.toLowerCase());
        }
    }

    private boolean listDoesNotContainValue(String list, String val) {
        if (Strings.isNullOrEmpty(list)) {
            return true;
        } else {
            return list.contains(val.toLowerCase());
        }
    }
}
