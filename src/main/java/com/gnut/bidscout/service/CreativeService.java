package com.gnut.bidscout.service;

import com.gnut.bidscout.db.CreativeDao;
import com.gnut.bidscout.model.Creative;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CreativeService {
    private final CreativeDao creativeDao;

    @Autowired
    public CreativeService(CreativeDao creativeDao) {
        this.creativeDao = creativeDao;
    }

    public Creative saveCreative(Creative creative) {
        return creativeDao.save(creative);
    }

    public Optional<Creative> getCreative(String id) {
        return creativeDao.findById(id);
    }

    public Iterable<Creative> getCreatives(List<String> ids) {
        return creativeDao.findAllById(ids);
    }

    public Map<String, String> getCreativeNames() {
        List<Creative> creatives = creativeDao.findAll();
        if(creatives.isEmpty()) {
            return Collections.emptyMap();
        } else {
            final Map<String, String> results = new HashMap<>();
            creatives.forEach(c->{
                results.put(c.getId(), c.getName());
            });
            return results;
        }
    }
}
