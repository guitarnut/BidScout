package com.gnut.bidscout.db;

import com.gnut.bidscout.model.EventRecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRecordDao extends MongoRepository<EventRecord, String> {
    List<EventRecord> findAllByTagRequestId(String id);
}
