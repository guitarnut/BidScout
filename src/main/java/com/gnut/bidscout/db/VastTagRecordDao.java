package com.gnut.bidscout.db;

import com.gnut.bidscout.model.VastTagRecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VastTagRecordDao extends MongoRepository<VastTagRecord, String> {
    VastTagRecord findFirstByTagRequestId(String id);
    VastTagRecord findFirstById(String id);
    VastTagRecord findFirstByOwnerAndId(String account, String requestId);
    List<VastTagRecord> findAllByOwner(String owner);
}
