package com.gnut.bidscout.db;

import com.gnut.bidscout.model.ClickRecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClickDao extends MongoRepository<ClickRecord, String> {
}
