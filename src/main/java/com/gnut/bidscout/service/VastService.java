package com.gnut.bidscout.service;

import com.gnut.bidscout.db.XmlDao;
import com.gnut.bidscout.model.Xml;
import com.iab.openrtb.vast.Vast;
import org.springframework.stereotype.Component;

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
    }
}
