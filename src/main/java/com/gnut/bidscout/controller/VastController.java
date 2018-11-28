package com.gnut.bidscout.controller;

import com.gnut.bidscout.service.VastService;
import com.iab.openrtb.vast.Vast;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/vast")
public class VastController {
    private VastService service;

    @Autowired
    public VastController(VastService service) {
        this.service = service;
    }

    @RequestMapping(value = "/{bidder}/{id}", method = RequestMethod.POST, produces = "application/xml")
    public Vast getVastPost(
            @PathVariable(value = "bidder") String bidder,
            @PathVariable(value = "id") String id,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        return service.serveVast(bidder, id);
    }

    @RequestMapping(value = "/{bidder}/{id}", method = RequestMethod.GET, produces = "application/xml")
    public Vast getVastGet(
            @PathVariable(value = "bidder") String bidder,
            @PathVariable(value = "id") String id,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        return service.serveVast(bidder, id);
    }
}
