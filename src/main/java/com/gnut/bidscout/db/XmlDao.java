package com.gnut.bidscout.db;

import com.gnut.bidscout.model.Xml;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface XmlDao extends MongoRepository<Xml, String> {
    Xml findByOwnerAndId(String owner, String id);
    List<Xml> findAllByOwner(String owner);

}
