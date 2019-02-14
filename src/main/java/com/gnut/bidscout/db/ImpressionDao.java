package com.gnut.bidscout.db;

import com.gnut.bidscout.model.ImpressionRecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImpressionDao extends MongoRepository<ImpressionRecord, String> {
    List<ImpressionRecord> findAllByOwnerAndBidRequestId(String id, String bid);
    List<ImpressionRecord> findAllByVastTagRequestId(String tag);
}
