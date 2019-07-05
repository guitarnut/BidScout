package com.gnut.bidscout.db;

import com.gnut.bidscout.model.Campaign;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampaignDao extends MongoRepository<Campaign, String> {
    List<Campaign> findAllByOwner(String owner);
    Campaign findByIdAndOwner(String id, String owner);
    Campaign findByName(String name);
    List<Campaign> findAllByEnabledAndOwnerAndId(boolean val, String account, String id);
    Campaign findByCreativesContainsAndOwner(String id, String account);
}
