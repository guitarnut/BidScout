package com.gnut.bidscout.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class ImpressionCache {

    private final LoadingCache<String, Integer> impressions;

    public ImpressionCache() {
        impressions = Caffeine.newBuilder()
                .maximumSize(100_000)
                .refreshAfterWrite(15, TimeUnit.MINUTES)
                .expireAfterWrite(30, TimeUnit.MINUTES)
                .build(k -> setInitialValue());
    }

    public int addImpression(String id) {
        int count = 1;
        if (impressions.get(id) != null) {
            count = impressions.get(id) + 1;
        }
        impressions.put(id, count);
        return count;
    }

    private int setInitialValue() {
        return 0;
    }
}
