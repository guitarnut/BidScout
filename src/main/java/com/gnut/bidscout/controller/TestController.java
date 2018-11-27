package com.gnut.bidscout.controller;

import com.gnut.bidscout.service.VastService;
import com.iab.openrtb.vast.Vast;
import com.iab.openrtb.vast.ad.AdVerifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test")
public class TestController {
    private final VastService service;

    @Autowired
    public TestController(VastService service) {
        this.service = service;
    }

    @RequestMapping(value = "/xml", method = RequestMethod.GET, produces = "application/xml")
    @ResponseBody
    public Vast test() {
        return service.getXml();
    }
}
