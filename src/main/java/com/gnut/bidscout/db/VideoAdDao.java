package com.gnut.bidscout.db;

import com.gnut.bidscout.model.VideoAd;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VideoAdDao extends MongoRepository<VideoAd, String> {
    Optional<VideoAd> findByCreativeIdAndOwner(String creativeId, String owner);
    Optional<VideoAd> findByCreativeId(String creativeId);
}
