package com.gnut.bidscout.service;

import com.gnut.bidscout.db.CampaignDao;
import com.gnut.bidscout.model.*;
import com.google.common.base.Strings;
import com.iab.openrtb.request.BidRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class CampaignService {
    private final SyncService syncService;
    private final CampaignDao campaignDao;
    private final CreativeService creativeService;

    @Autowired
    public CampaignService(SyncService syncService, CampaignDao campaignDao, CreativeService creativeService) {
        this.syncService = syncService;
        this.campaignDao = campaignDao;
        this.creativeService = creativeService;
    }

    public void addCreativeToCampaign(String campaignId, String creativeId) {
        Optional<Campaign> campaign = campaignDao.findById(campaignId);
        if(campaign.isPresent()) {
            Optional<Creative> creative = creativeService.getCreative(creativeId);
            if(creative.isPresent()) {
                List<String> c = campaign.get().getCreatives();
                if(c == null) {
                    c = new ArrayList<>();
                }
                c.add(creative.get().getId());
                campaign.get().setCreatives(c);
                campaignDao.save(campaign.get());
            }
        }
    }

    public Optional<EligibleCampaignData> targetCampaign(String publisher, BidRequest bidRequest, HttpServletRequest request) {
        final Optional<Campaign> campaign = selectCampaign(bidRequest);
        if(campaign.isPresent()) {
            final RequestTargetingData targetingData = generateTargetingData(publisher, bidRequest, request);

            if (campaignIsEligible(targetingData, campaign.get())) {
                final EligibleCampaignData campaignData = new EligibleCampaignData();
                campaignData.setCampaign(campaign.get());
                campaignData.setCreatives(getEligibleCampaignCreatives(targetingData, campaign.get()));
                campaignData.setData(targetingData);
                return Optional.of(campaignData);
            }
        }
        return Optional.empty();
    }

    public Campaign saveCampaign(Campaign campaign) {
        return campaignDao.save(campaign);
    }

    public Map<String, String> getCampaignNames() {
        List<Campaign> campaigns = campaignDao.findAll();
        if(campaigns.isEmpty()) {
            return Collections.emptyMap();
        } else {
            final Map<String, String> results = new HashMap<>();
            campaigns.forEach(c->{
                results.put(c.getId(), c.getName());
            });
            return results;
        }
    }

    private RequestTargetingData generateTargetingData(String publisher, BidRequest bidRequest, HttpServletRequest request) {
        // Todo: Move
        final RequestTargetingData data = new RequestTargetingData();
        final String userId = syncService.getUserCookieValue(request);

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

        if (!Strings.isNullOrEmpty(bidRequest.getUser().getBuyeruid())) {
            data.setBuyeruid(bidRequest.getUser().getBuyeruid());
            data.setUserMatch(true);
        } else if (!Strings.isNullOrEmpty(userId)) {
            data.setUserMatch(true);
        }

        data.setWidths(Arrays.asList(bidRequest.getImp().get(0).getBanner().getW()));
        data.setHeights(Arrays.asList(bidRequest.getImp().get(0).getBanner().getH()));
        data.setSecure(bidRequest.getImp().get(0).getSecure() == 1);
        data.setBidfloor(bidRequest.getImp().get(0).getBidfloor());
        data.setBadv(bidRequest.getBadv());
        data.setBattr(bidRequest.getImp().get(0).getBanner().getBattr());
        data.setBtype(bidRequest.getImp().get(0).getBanner().getBtype());

        return data;
    }

    private Optional<Campaign> selectCampaign(BidRequest bidRequest) {
        // Todo: Target off of request
        List<Campaign> campaigns = campaignDao.findAllByEnabled(true);
        if(campaigns.isEmpty()) {
            return null;
        }
        return Optional.of(campaigns.get(0));
    }

    private boolean campaignIsEligible(RequestTargetingData targetingData, Campaign campaign) {
        return isEligible(targetingData, campaign.getRequirements());
    }

    // Todo: Move
    private Set<Creative> getEligibleCampaignCreatives(RequestTargetingData targetingData, Campaign campaign) {
        final Set<Creative> eligible = new HashSet<>();
        final Iterable<Creative> creatives = creativeService.getCreatives(campaign.getCreatives());
        for (Creative c : creatives) {
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

    // Todo: Move
    private boolean isEligible(RequestTargetingData targetingData, Requirements filter) {
        final Date now = new Date();

        if(filter.isUserMatch() && !targetingData.isUserMatch()) {
            return false;
        }

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
            } else if (!listContainsValue(filter.getBundleBlacklist(), targetingData.getPublisherId())) {
                return false;
            }
        } else if (targetingData.getPlatform().equals(RequestTargetingData.Platform.DESKTOP)) {
            if (!filter.isDesktop()) {
                return false;
            } else if (!listContainsValue(filter.getDomainWhitelist(), targetingData.getPublisherId())) {
                return false;
            } else if (!listContainsValue(filter.getDomainBlacklist(), targetingData.getPublisherId())) {
                return false;
            }
        } else if (targetingData.getPlatform().equals(RequestTargetingData.Platform.MOBILE)) {
            if (!filter.isMobile()) {
                return false;
            } else if (!listContainsValue(filter.getDomainWhitelist(), targetingData.getPublisherId())) {
                return false;
            } else if (!listContainsValue(filter.getDomainBlacklist(), targetingData.getPublisherId())) {
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
        } else if (!listContainsValue(filter.getDomainBlacklist(), targetingData.getPublisherId())) {
            return false;
        }

        // user match, cookie value
        if (filter.isUserMatch() && Strings.isNullOrEmpty(targetingData.getBuyeruid())) {
            return false;
        }

        return true;
    }

    private boolean listContainsValue(List<String> list, String val) {
        if (list.isEmpty()) {
            return true;
        } else {
            return list.contains(val.toLowerCase());
        }
    }
}
