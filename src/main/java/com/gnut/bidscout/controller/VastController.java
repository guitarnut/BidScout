package com.gnut.bidscout.controller;

import com.gnut.bidscout.service.inventory.VastService;
import com.iab.openrtb.vast.Vast;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/serve/{bidder}/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public Vast serveVastPost(
            @PathVariable(value = "bidder") String bidder,
            @PathVariable(value = "id") String id,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        return service.serveVast(bidder, id, request);
    }

    @RequestMapping(value = "/serve/{bidder}/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
    public Vast serveVastGet(
            @PathVariable(value = "bidder") String bidder,
            @PathVariable(value = "id") String id,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        return service.serveVast(bidder, id, request);
    }

    @RequestMapping(value = "/view/{bidder}/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_XML_VALUE)
    public Vast getVast(
            @PathVariable(value = "bidder") String bidder,
            @PathVariable(value = "id") String id,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        return service.getVast(bidder, id);
    }

//    @RequestMapping(value = "/email", method = RequestMethod.GET)
//    public void getVast(
//            HttpServletRequest request,
//            HttpServletResponse response
//    ) {
//        service.sendEmail();
//    }
}
