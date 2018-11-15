package com.gnut.bidscout.db;

import com.gnut.bidscout.model.ClickRecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClickDao extends MongoRepository<ClickRecord, String> {
    List<ClickRecord> findAllByOwnerAndBidRequestId(String id, String bid);
}
