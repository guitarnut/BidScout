package com.gnut.bidscout.db;

import com.gnut.bidscout.model.EventRecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRecordDao extends MongoRepository<EventRecord, String> {
}
