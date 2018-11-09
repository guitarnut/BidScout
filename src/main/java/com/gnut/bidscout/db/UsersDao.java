package com.gnut.bidscout.db;

import com.gnut.bidscout.model.Users;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UsersDao extends MongoRepository<Users, String> {
    Users findByUsername(String username);
}
