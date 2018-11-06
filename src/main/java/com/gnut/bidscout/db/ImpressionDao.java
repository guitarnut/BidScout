package com.gnut.bidscout.db;

import com.gnut.bidscout.model.ImpressionRecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImpressionDao extends MongoRepository<ImpressionRecord, String> {
}