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

    @RequestMapping(value = "/tag/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
    public Vast vastTag(
            @PathVariable(value = "id") String id,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        return service.createVastDocument(request, response, id);
    }
}
