package com.gnut.bidscout.db;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImpressionDao extends MongoRepository<ImpressionDao, String> {
}
