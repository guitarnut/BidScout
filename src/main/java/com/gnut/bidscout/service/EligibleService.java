package com.gnut.bidscout.service;

import com.gnut.bidscout.model.Campaign;
import com.gnut.bidscout.model.Creative;
import com.gnut.bidscout.model.RequestTargetingData;
import com.gnut.bidscout.model.Requirements;
import com.gnut.bidscout.values.TargetFailure;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
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

        if (creatives == null) {
            targetingData.setTargetingFailureReason(TargetFailure.CREATIVES_ALIGNED.value());
            return eligible;
        }

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
                    targetingData.setTargetingFailureReason(TargetFailure.SIZE_MATCH.value());
                    continue;
                }
            }

            if (!isEligible(targetingData, filter, Optional.of(c))) {
                continue;
            }

            if (c.getMaxBid() < targetingData.getBidfloor()) {
                if (c.getRequirements().getDealIds() != null && targetingData.getDealIds() != null) {
                    final AtomicBoolean dealFloorBeat = new AtomicBoolean(false);
                    targetingData.getDealIds().forEach(d -> {
                        if (c.getRequirements().getDealIds().contains(d.getId())) {
                            if (c.getMaxBid() >= d.getBidfloor()) {
                                dealFloorBeat.getAndSet(true);
                            }
                        }
                    });
                    if (!dealFloorBeat.get()) {
                        targetingData.setTargetingFailureReason(TargetFailure.MAX_BID_BELOW_FLOOR_AND_DEAL_FLOOR.value());
                        continue;
                    }
                } else {
                    targetingData.setTargetingFailureReason(TargetFailure.MAX_BID_BELOW_FLOOR.value());
                    continue;
                }
            }

            if (!targetingData.getBadv().isEmpty()) {
                final AtomicInteger count = new AtomicInteger(0);
                c.getAdDomain().forEach(d -> {
                    if (targetingData.getBadv().contains(d)) {
                        count.getAndIncrement();
                    }
                });
                if (count.get() > 0) {
                    targetingData.setTargetingFailureReason(TargetFailure.BADV.value());
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
                    targetingData.setTargetingFailureReason(TargetFailure.BATTR.value());
                    continue;
                }
            }

            eligible.add(c);
        }
        return eligible;
    }

    public boolean isEligible(RequestTargetingData targetingData, Requirements filter, Optional<Creative> creative) {
        final Date now = new Date();

        // deal targeting
        if (filter.getDealIds() != null && !filter.getDealIds().isEmpty()) {
            final AtomicBoolean dealMatch = new AtomicBoolean(false);
            targetingData.getDealIds().forEach(d -> {
                if (filter.getDealIds().contains(d.getId())) {
                    dealMatch.getAndSet(true);
                }
            });
            if (!dealMatch.get()) {
                targetingData.setTargetingFailureReason(TargetFailure.DEAL_ID.value());
                return false;
            }
        }

        if (filter.isUserMatch() && !targetingData.isUserMatch()) {
            targetingData.setTargetingFailureReason(TargetFailure.USER_MATCH.value());
            return false;
        }

        if (filter.getStartDate() != null && filter.getStartDate().getTime() < now.getTime()) {
            targetingData.setTargetingFailureReason(TargetFailure.FLIGHT_NOT_STARTED.value());
            return false;
        } else if (filter.getEndDate() != null && filter.getEndDate().getTime() > now.getTime()) {
            targetingData.setTargetingFailureReason(TargetFailure.FLIGHT_ENDED.value());
            return false;
        }

        // platform targeting
        if (targetingData.getPlatform().equals(RequestTargetingData.Platform.INAPP)) {
            if (!filter.isInapp()) {
                targetingData.setTargetingFailureReason(TargetFailure.PLATFORM_IN_APP.value());
                return false;
            } else if (!listContainsValue(filter.getBundleWhitelist(), targetingData.getBundleId())) {
                targetingData.setTargetingFailureReason(TargetFailure.BUNDLE_WHITELIST.value());
                return false;
            } else if (!listContainsValue(filter.getBundleBlacklist(), targetingData.getBundleId())) {
                targetingData.setTargetingFailureReason(TargetFailure.BUNDLE_BLACKLIST.value());
                return false;
            }
        } else if (targetingData.getPlatform().equals(RequestTargetingData.Platform.DESKTOP)) {
            if (!filter.isDesktop()) {
                targetingData.setTargetingFailureReason(TargetFailure.PLATFORM_DESKTOP.value());
                return false;
            } else if (!listContainsValue(filter.getDomainWhitelist(), targetingData.getDomain())) {
                targetingData.setTargetingFailureReason(TargetFailure.DOMAIN_WHITELIST.value());
                return false;
            } else if (!listContainsValue(filter.getDomainBlacklist(), targetingData.getDomain())) {
                targetingData.setTargetingFailureReason(TargetFailure.DOMAIN_BLACKLIST.value());
                return false;
            }
        } else if (targetingData.getPlatform().equals(RequestTargetingData.Platform.MOBILE)) {
            if (!filter.isMobile()) {
                targetingData.setTargetingFailureReason(TargetFailure.PLATFORM_MOBILE.value());
                return false;
            } else if (!listContainsValue(filter.getDomainWhitelist(), targetingData.getDomain())) {
                targetingData.setTargetingFailureReason(TargetFailure.DOMAIN_WHITELIST.value());
                return false;
            } else if (!listContainsValue(filter.getDomainBlacklist(), targetingData.getDomain())) {
                targetingData.setTargetingFailureReason(TargetFailure.DOMAIN_BLACKLIST.value());
                return false;
            }
        } else if (targetingData.getPlatform().equals(RequestTargetingData.Platform.CTV)) {
            if (!filter.isCtv()) {
                targetingData.setTargetingFailureReason(TargetFailure.PLATFORM_CTV.value());
                return false;
            } else {
                return false;
            }
        }

        // publisher white and black lists
        if (!listContainsValue(filter.getPublisherWhitelist(), targetingData.getPublisherId())) {
            targetingData.setTargetingFailureReason(TargetFailure.PUBLISHER_WHITELIST.value());
            return false;
        } else if (!listContainsValue(filter.getDomainBlacklist(), targetingData.getPublisherId())) {
            targetingData.setTargetingFailureReason(TargetFailure.PUBLISHER_BLACKLIST.value());
            return false;
        }

        // user match, cookie value
        if (filter.isUserMatch() && Strings.isNullOrEmpty(targetingData.getBuyeruid())) {
            targetingData.setTargetingFailureReason(TargetFailure.USER_MATCH.value());
            return false;
        }

        if (creative.isPresent()) {
            if (targetingData.getBattr() != null) {
                for (int v : targetingData.getBattr()) {
                    if (creative.get().getAttr().contains(v)) {
                        targetingData.setTargetingFailureReason(TargetFailure.BATTR.value());
                        return false;
                    }
                }
            }
            if (targetingData.getBadv() != null) {
                for (String v : targetingData.getBadv()) {
                    if (creative.get().getAdDomain().contains(v)) {
                        targetingData.setTargetingFailureReason(TargetFailure.BADV.value());
                        return false;
                    }
                }
            }
            if (targetingData.getBcat() != null) {
                for (String v : targetingData.getBcat()) {
                    for (String cat : creative.get().getIabCategories()) {
                        if (cat.contains(v) || cat.equals(v)) {
                            targetingData.setTargetingFailureReason(TargetFailure.IAB_CATEGORY.value());
                            return false;
                        }
                    }
                }
            }
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
