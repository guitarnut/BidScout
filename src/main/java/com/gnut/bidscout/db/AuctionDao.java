package com.gnut.bidscout.db;

import com.gnut.bidscout.model.AuctionRecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionDao extends MongoRepository<AuctionRecord, String> {
    AuctionRecord findOneById(String id);
    AuctionRecord findOneByBidRequestId(String id);
}
