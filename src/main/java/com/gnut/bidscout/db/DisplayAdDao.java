package com.gnut.bidscout.db;

import com.gnut.bidscout.model.DisplayAd;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DisplayAdDao extends MongoRepository<DisplayAd, String> {
    Optional<DisplayAd> findByCreativeId(String creativeId);
}
