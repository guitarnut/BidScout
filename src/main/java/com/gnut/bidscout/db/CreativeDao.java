package com.gnut.bidscout.db;

import com.gnut.bidscout.model.Creative;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreativeDao extends MongoRepository<Creative, String > {
    Creative findByIdAndOwner(String id, String account);
    Creative findByNameAndOwner(String name, String owner);
    List<Creative> findAllByOwner(String account);
}
