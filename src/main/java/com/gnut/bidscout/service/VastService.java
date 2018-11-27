package com.gnut.bidscout.service;

import com.gnut.bidscout.db.XmlDao;
import com.gnut.bidscout.model.Xml;
import com.iab.openrtb.vast.Vast;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VastService {

    private final XmlDao xmlDao;

    public VastService(XmlDao xmlDao) {
        this.xmlDao = xmlDao;
    }

    public void saveXml(String account, Vast vast) {
        Xml xml = new Xml();
        xml.setOwner(account);
        xml.setVast(vast);
        xmlDao.save(xml);
    }

    public Vast getXml() {
        List<Xml> result = xmlDao.findAll();
        return result.get(0).getVast();
    }
}
