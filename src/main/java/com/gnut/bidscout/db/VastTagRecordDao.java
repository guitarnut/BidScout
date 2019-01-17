package com.gnut.bidscout.db;

import com.gnut.bidscout.model.VastTagRecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VastTagRecordDao extends MongoRepository<VastTagRecord, String> {
    VastTagRecord findFirstById(String id);
}
