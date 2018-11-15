package com.gnut.bidscout.controller;

import com.gnut.bidscout.service.ImpressionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/imp")
public class ImpressionController {
    private ImpressionService service;

    @Autowired
    public ImpressionController(ImpressionService service) {
        this.service = service;
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value="/{id}/{bid}/{campaign}/{creative}/{bidprice}/{cp}", method = RequestMethod.GET)
    public void handleImp(
            @PathVariable(value = "id") String id,
            @PathVariable(value = "bid") String bid,
            @PathVariable(value = "campaign") String campaign,
            @PathVariable(value = "creative") String creative,
            @PathVariable(value = "bidprice") String bidprice,
            @PathVariable(value = "cp") String cp,
            @RequestParam(value = "cb") String cb,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        service.handleRequest(id, request, response, bid, campaign, creative, bidprice, cp, cb);
    }
}
