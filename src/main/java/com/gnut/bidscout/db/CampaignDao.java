package com.gnut.bidscout.db;

import com.gnut.bidscout.model.Campaign;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampaignDao extends MongoRepository<Campaign, String> {
}
