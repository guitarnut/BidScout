package com.gnut.bidscout.controller;

import com.gnut.bidscout.service.ClickService;
import com.gnut.bidscout.service.ImpressionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/event")
public class EventController {
    private ImpressionService impressionService;
    private ClickService clickService;

    @Autowired
    public EventController(
            ImpressionService impressionService,
            ClickService clickService
    ) {
        this.impressionService = impressionService;
        this.clickService = clickService;
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value="/imp/{id}/{bid}/{campaign}/{creative}/{bidprice}/{cp}", method = RequestMethod.GET)
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
        impressionService.handleRequest(id, request, response, bid, campaign, creative, bidprice, cp, cb);
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value="/click/{id}/{bid}/{campaign}/{creative}", method = RequestMethod.GET)
    public void handleClick(
            @PathVariable(value = "id") String id,
            @PathVariable(value = "bid") String bid,
            @PathVariable(value = "campaign") String campaign,
            @PathVariable(value = "creative") String creative,
            @RequestParam(value = "cb", required = false) String cb,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        clickService.handleRequest(id, request, response, bid, campaign, creative, cb);
    }
}
