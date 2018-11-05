package com.gnut.bidscout.service;

import com.gnut.bidscout.db.CreativeDao;
import com.gnut.bidscout.model.Creative;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
}
