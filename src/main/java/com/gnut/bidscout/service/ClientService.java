package com.gnut.bidscout.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gnut.bidscout.db.AuctionDao;
import com.gnut.bidscout.db.ClickDao;
import com.gnut.bidscout.db.ImpressionDao;
import com.gnut.bidscout.model.AuctionRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ClientService {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final AuctionDao auctionDao;
    private final ImpressionDao impressionDao;
    private final ClickDao clickDao;

    static {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @Autowired
    public ClientService(AuctionDao auctionDao, ImpressionDao impressionDao, ClickDao clickDao) {
        this.auctionDao = auctionDao;
        this.impressionDao = impressionDao;
        this.clickDao = clickDao;
    }

    public String getBid(String id) {
        AuctionRecord record = auctionDao.findFirstByBidRequestId(id);
        if (record != null) {
            try {
                return objectMapper.writeValueAsString(record);
            } catch (IOException ex) {
                //
            }
        }
        return "";
    }
}
