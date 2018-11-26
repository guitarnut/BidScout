package com.gnut.bidscout.db;

import com.gnut.bidscout.model.Xml;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface XmlDao extends MongoRepository<Xml, String> {
}
