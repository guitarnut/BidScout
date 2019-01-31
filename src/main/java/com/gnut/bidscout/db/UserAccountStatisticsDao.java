package com.gnut.bidscout.db;

import com.gnut.bidscout.model.UserAccountStatistics;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserAccountStatisticsDao extends MongoRepository<UserAccountStatistics, String> {
    UserAccountStatistics findOneByUser(String id);
}
