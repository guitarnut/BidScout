package com.gnut.bidscout.service;

import com.gnut.bidscout.db.XmlDao;
import com.gnut.bidscout.model.Xml;
import com.iab.openrtb.vast.Vast;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class VastService {

    private final XmlDao xmlDao;

    public VastService(XmlDao xmlDao) {
        this.xmlDao = xmlDao;
    }

    public void saveXml(String account, Xml xml) {
        xml.setOwner(account);
        xmlDao.save(xml);
    }

    public Map<String, String> getAllVastDocuments(String account) {
        List<Xml> allXml = xmlDao.findAllByOwner(account);
        if (allXml != null) {
            Map<String, String> results = new HashMap<>();
            allXml.forEach(x -> {
                results.put(x.getId(), x.getName());
            });
            return results;
        } else {
            return Collections.emptyMap();
        }
    }

    public Vast serveVast(String account, String id) {
        Xml xml = xmlDao.findByOwnerAndId(account, id);
        if (xml != null) {
            return xml.getVast();
        } else {
            return null;
        }
    }

    public Xml getXml(String account, String xmlId) {
        return xmlDao.findByOwnerAndId(account, xmlId);
    }

    public void deleteXml(String account, String xmlId) {
        Xml result = xmlDao.findByOwnerAndId(account, xmlId);
        if (result != null) {
            xmlDao.delete(result);
        }
    }
}
