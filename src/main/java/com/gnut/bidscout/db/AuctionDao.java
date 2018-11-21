package com.gnut.bidscout.db;

import com.gnut.bidscout.model.AuctionRecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuctionDao extends MongoRepository<AuctionRecord, String> {
    AuctionRecord findFirstByBidRequestIdAndOwner(String id, String owner);
    List<AuctionRecord> findAllByBidRequestErrorsIsNotNullAndOwner(String owner);
}
