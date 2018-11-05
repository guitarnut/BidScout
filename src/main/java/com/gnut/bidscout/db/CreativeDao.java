package com.gnut.bidscout.db;

import com.gnut.bidscout.model.Creative;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreativeDao extends MongoRepository<Creative, String > {
}
