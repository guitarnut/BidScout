package com.gnut.bidscout.service;

import com.gnut.bidscout.model.Campaign;
import com.gnut.bidscout.model.Creative;
import com.gnut.bidscout.model.RequestTargetingData;
import com.gnut.bidscout.model.Requirements;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class EligibleService {
    private final CreativeService creativeService;

    @Autowired
    public EligibleService(CreativeService creativeService) {
        this.creativeService = creativeService;
    }

    public Set<Creative> getEligibleCreatives(RequestTargetingData targetingData, Campaign campaign) {
        final Set<Creative> eligible = new HashSet<>();
        final Iterable<Creative> creatives = creativeService.getCreatives(campaign.getCreatives());
        for (Creative c : creatives) {
            if (!c.isEnabled()) {
                continue;
            }

            final Requirements filter = c.getRequirements();

            // size targeting
            if (targetingData.getWidths().contains(c.getW()) && targetingData.getHeights().contains(c.getH())) {
                AtomicInteger matches = new AtomicInteger(0);
                targetingData.getWidths().forEach(w -> {
                    int index = targetingData.getWidths().indexOf(w);
                    if (targetingData.getWidths().get(index) == c.getW() && targetingData.getHeights().get(index) == c.getH()) {
                        matches.getAndIncrement();
                    }
                });
                if (matches.get() == 0) {
                    continue;
                }
            }

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

            eligible.add(c);
        }
        return eligible;
    }

    public boolean isEligible(RequestTargetingData targetingData, Requirements filter) {
        final Date now = new Date();

        // deal targeting
        if (filter.getDealIds() != null && !filter.getDealIds().isEmpty()) {
            final AtomicBoolean dealMatch = new AtomicBoolean(false);
            targetingData.getDealIds().forEach(d -> {
                if (filter.getDealIds().contains(d.getId())) {
                    dealMatch.getAndSet(true);
                }
            });
            if(!dealMatch.get()) {
                return false;
            }
        }

        if (filter.isUserMatch() && !targetingData.isUserMatch()) {
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
