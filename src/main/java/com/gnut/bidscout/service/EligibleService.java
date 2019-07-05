package com.gnut.bidscout.service;

import com.gnut.bidscout.db.DisplayAdDao;
import com.gnut.bidscout.db.VideoAdDao;
import com.gnut.bidscout.model.*;
import com.gnut.bidscout.values.TargetFailure;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class EligibleService {
    private final DisplayAdDao displayAdDao;
    private final VideoAdDao videoAdDao;

    @Autowired
    public EligibleService(DisplayAdDao displayAdDao, VideoAdDao videoAdDao) {
        this.displayAdDao = displayAdDao;
        this.videoAdDao = videoAdDao;
    }

    private Optional<Ad> getAd(Creative.Type type, String creativeId) {
        if (type == Creative.Type.DISPLAY) {
            Optional<DisplayAd> displayAd = displayAdDao.findByCreativeId(creativeId);
            if (displayAd.isPresent()) {
                return Optional.of(displayAd.get());
            }
        } else if (type == Creative.Type.VAST) {
            Optional<VideoAd> videoAd = videoAdDao.findByCreativeId(creativeId);
            if (videoAd.isPresent()) {
                return Optional.of(videoAd.get());
            }
        }
        return Optional.empty();
    }

    public Set<Creative> getEligibleCreatives(
            RequestTargetingData targetingData,
            Campaign campaign,
            AuctionRecord record,
            Iterable<Creative> creatives
    ) {
        final Set<Creative> eligible = new HashSet<>();
        if (campaign.getCreatives() == null || creatives == null) {
            record.getTargetingFailures().put(campaign.getName(), TargetFailure.CREATIVES_ALIGNED.value());
            return eligible;
        }

        for (Creative c : creatives) {
            if (!c.isEnabled()) {
                continue;
            }

            final Optional<Ad> ad = getAd(c.getType(), c.getId());

            if (!ad.isPresent()) {
                continue;
            }

            final Requirements filter = c.getRequirements();

            // size targeting
            if (targetingData.getWidths().contains(ad.get().getWidth()) && targetingData.getHeights().contains(ad.get().getHeight())
                    || ad.get().getWidth() == 0 && ad.get().getHeight() == 0) {
                AtomicInteger matches = new AtomicInteger(0);
                targetingData.getWidths().forEach(w -> {
                    int index = targetingData.getWidths().indexOf(w);
                    if (targetingData.getWidths().get(index) == ad.get().getWidth() && targetingData.getHeights().get(index) == ad.get().getHeight()) {
                        matches.getAndIncrement();
                    } else if (ad.get().getWidth() == 0 && ad.get().getHeight() == 0) {
                        matches.getAndIncrement();
                    }
                });
                if (matches.get() == 0) {
                    record.getTargetingFailures().put(campaign.getName(), TargetFailure.SIZE_MATCH.value());
                    continue;
                }
            } else {
                record.getTargetingFailures().put(campaign.getName(), TargetFailure.SIZE_MATCH.value());
                continue;
            }

            if (!isEligible(targetingData, filter, Optional.empty(), Optional.of(c), record)) {
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
                        record.getTargetingFailures().put(c.getName(), TargetFailure.MAX_BID_BELOW_FLOOR_AND_DEAL_FLOOR.value());
                        continue;
                    }
                } else {
                    record.getTargetingFailures().put(c.getName(), TargetFailure.MAX_BID_BELOW_FLOOR.value());
                    continue;
                }
            }

            if (!targetingData.getBadv().isEmpty()) {
                final AtomicInteger count = new AtomicInteger(0);
                ad.get().getAdDomain().forEach(d -> {
                    if (targetingData.getBadv().contains(d)) {
                        count.getAndIncrement();
                    }
                });
                if (count.get() > 0) {
                    record.getTargetingFailures().put(c.getName(), TargetFailure.BADV.value());
                    continue;
                }
            }

            if (!targetingData.getBattr().isEmpty()) {
                final AtomicInteger count = new AtomicInteger(0);
                ad.get().getAttr().forEach(a -> {
                    if (targetingData.getBattr().contains(a)) {
                        count.getAndIncrement();
                    }
                });
                if (count.get() > 0) {
                    record.getTargetingFailures().put(c.getName(), TargetFailure.BATTR.value());
                    continue;
                }
            }

            eligible.add(c);
        }
        return eligible;
    }

    public boolean isEligible(
            RequestTargetingData targetingData,
            Requirements filter,
            Optional<Campaign> campaign,
            Optional<Creative> creative,
            AuctionRecord record
    ) {
        final Date now = new Date();
        final String targetName;
        final Statistics statistics;
        final Limits limits;

        if (campaign.isPresent()) {
            targetName = campaign.get().getName();
            statistics = campaign.get().getStatistics();
            limits = campaign.get().getLimits();
        } else {
            targetName = creative.get().getName();
            statistics = creative.get().getStatistics();
            limits = creative.get().getLimits();
        }

        if (limitsExceeded(targetName, statistics, limits, record)) {
            return false;
        }

        // deal targeting
        if (filter.getDealIds() != null && !filter.getDealIds().isEmpty()) {
            final AtomicBoolean dealMatch = new AtomicBoolean(false);
            targetingData.getDealIds().forEach(d -> {
                if (filter.getDealIds().contains(d.getId())) {
                    dealMatch.getAndSet(true);
                }
            });
            if (!dealMatch.get()) {
                record.getTargetingFailures().put(targetName, TargetFailure.DEAL_ID.value());
                return false;
            }
        }

        if (filter.isUserMatch() && !targetingData.isUserMatch()) {
            record.getTargetingFailures().put(targetName, TargetFailure.USER_MATCH.value());
            return false;
        }

        if (filter.getStartDate() != null && filter.getStartDate().getTime() < now.getTime()) {
            record.getTargetingFailures().put(targetName, TargetFailure.FLIGHT_NOT_STARTED.value());
            return false;
        } else if (filter.getEndDate() != null && filter.getEndDate().getTime() > now.getTime()) {
            record.getTargetingFailures().put(targetName, TargetFailure.FLIGHT_ENDED.value());
            return false;
        }

        // platform targeting
        if (targetingData.getPlatform().equals(RequestTargetingData.Platform.INAPP)) {
            if (!filter.isInapp()) {
                record.getTargetingFailures().put(targetName, TargetFailure.PLATFORM_IN_APP.value());
                return false;
            } else if (!listContainsValue(filter.getBundleWhitelist(), targetingData.getBundleId())) {
                record.getTargetingFailures().put(targetName, TargetFailure.BUNDLE_WHITELIST.value());
                return false;
            } else if (!listContainsValue(filter.getBundleBlacklist(), targetingData.getBundleId())) {
                record.getTargetingFailures().put(targetName, TargetFailure.BUNDLE_BLACKLIST.value());
                return false;
            }
        } else if (targetingData.getPlatform().equals(RequestTargetingData.Platform.DESKTOP)) {
            if (!filter.isDesktop()) {
                record.getTargetingFailures().put(targetName, TargetFailure.PLATFORM_DESKTOP.value());
                return false;
            } else if (!listContainsValue(filter.getDomainWhitelist(), targetingData.getDomain())) {
                record.getTargetingFailures().put(targetName, TargetFailure.DOMAIN_WHITELIST.value());
                return false;
            } else if (!listContainsValue(filter.getDomainBlacklist(), targetingData.getDomain())) {
                record.getTargetingFailures().put(targetName, TargetFailure.DOMAIN_BLACKLIST.value());
                return false;
            }
        } else if (targetingData.getPlatform().equals(RequestTargetingData.Platform.MOBILE)) {
            if (!filter.isMobile()) {
                record.getTargetingFailures().put(targetName, TargetFailure.PLATFORM_MOBILE.value());
                return false;
            } else if (!listContainsValue(filter.getDomainWhitelist(), targetingData.getDomain())) {
                record.getTargetingFailures().put(targetName, TargetFailure.DOMAIN_WHITELIST.value());
                return false;
            } else if (!listContainsValue(filter.getDomainBlacklist(), targetingData.getDomain())) {
                record.getTargetingFailures().put(targetName, TargetFailure.DOMAIN_BLACKLIST.value());
                return false;
            }
        } else if (targetingData.getPlatform().equals(RequestTargetingData.Platform.CTV)) {
            if (!filter.isCtv()) {
                record.getTargetingFailures().put(targetName, TargetFailure.PLATFORM_CTV.value());
                return false;
            } else {
                return false;
            }
        }

        // publisher white and black lists
        if (!listContainsValue(filter.getPublisherWhitelist(), targetingData.getPublisherId())) {
            record.getTargetingFailures().put(targetName, TargetFailure.PUBLISHER_WHITELIST.value());
            return false;
        } else if (!listContainsValue(filter.getDomainBlacklist(), targetingData.getPublisherId())) {
            record.getTargetingFailures().put(targetName, TargetFailure.PUBLISHER_BLACKLIST.value());
            return false;
        }

        // user match, cookie value
        if (filter.isUserMatch() && Strings.isNullOrEmpty(targetingData.getBuyeruid())) {
            record.getTargetingFailures().put(targetName, TargetFailure.USER_MATCH.value());
            return false;
        }

        if (creative.isPresent()) {
            Optional<Ad> ad = getAd(creative.get().getType(), creative.get().getId());

            if (targetingData.getBattr() != null) {
                for (int v : targetingData.getBattr()) {
                    if (ad.get().getAttr().contains(v)) {
                        record.getTargetingFailures().put(targetName, TargetFailure.BATTR.value());
                        return false;
                    }
                }
            }
            if (targetingData.getBadv() != null) {
                for (String v : targetingData.getBadv()) {
                    if (ad.get().getAdDomain().contains(v)) {
                        record.getTargetingFailures().put(targetName, TargetFailure.BADV.value());
                        return false;
                    }
                }
            }
            if (targetingData.getBcat() != null) {
                for (String v : targetingData.getBcat()) {
                    for (String cat : ad.get().getIabCategories()) {
                        if (cat.contains(v) || cat.equals(v)) {
                            record.getTargetingFailures().put(targetName, TargetFailure.IAB_CATEGORY.value());
                            return false;
                        }
                    }
                }
            }
            if (filter.isSecure() && !targetingData.isSecure()) {
                record.getTargetingFailures().put(targetName, TargetFailure.NOT_SECURE.value());
                return false;
            }
        }

        return true;
    }

    private boolean limitsExceeded(String name, Statistics statistics, Limits limits, AuctionRecord record) {
        int limitsMet = 0;
        if (limits.getBidLimit() > 0 && statistics.getBids() == limits.getBidLimit()) {
            record.getTargetingFailures().put(name, TargetFailure.BID_LIMIT_REACHED.value());
            limitsMet++;
        }
        if (limits.getImpressionLimit() > 0 && statistics.getImpressions() == limits.getImpressionLimit()) {
            record.getTargetingFailures().put(name, TargetFailure.IMPRESSION_LIMIT_REACHED.value());
            limitsMet++;
        }
        if (limits.getRequestLimit() > 0 && statistics.getRequests() == limits.getRequestLimit()) {
            record.getTargetingFailures().put(name, TargetFailure.REQUEST_LIMIT_REACHED.value());
            limitsMet++;
        }
        if (limits.getRevenueLimit() > 0 && statistics.getRevenue() == limits.getRevenueLimit()) {
            record.getTargetingFailures().put(name, TargetFailure.REVENUE_LIMIT_REACHED.value());
            limitsMet++;
        }
        return limitsMet > 0;
    }

    private boolean listContainsValue(List<String> list, String val) {
        if (list.isEmpty()) {
            return true;
        } else {
            return list.contains(val.toLowerCase());
        }
    }
}
