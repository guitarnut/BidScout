package com.gnut.bidscout.controller;

import com.gnut.bidscout.service.ClickService;
import com.gnut.bidscout.service.ImpressionService;
import com.gnut.bidscout.service.VideoEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/event")
public class EventController {
    private final ImpressionService impressionService;
    private final VideoEventService videoEventService;
    private final ClickService clickService;

    @Autowired
    public EventController(ImpressionService impressionService, VideoEventService videoEventService, ClickService clickService) {
        this.impressionService = impressionService;
        this.videoEventService = videoEventService;
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

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value="/video/{event}/{id}/{bid}/{campaign}/{creative}", method = RequestMethod.GET)
    public void handleVideoAuctionEvent(
            @PathVariable(value = "id") String id,
            @PathVariable(value = "event") String event,
            @PathVariable(value = "bid") String bid,
            @PathVariable(value = "campaign") String campaign,
            @PathVariable(value = "creative") String creative,
            @RequestParam(value = "cb", required = false) String cb,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        response.setStatus(204);
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value="/video/{event}/{id}", method = RequestMethod.GET)
    public void handleVastTagEvent(
            @PathVariable(value = "id") String id,
            @PathVariable(value = "event") String event,
            @RequestParam(value = "cb", required = false) long cb,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        videoEventService.recordVastTagEvent(id, event, cb, request);
        response.setStatus(204);
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value="/imp/{id}/{bidprice}/{cp}", method = RequestMethod.GET)
    public void handleVastImp(
            @PathVariable(value = "id") String id,
            @PathVariable(value = "bid") String bid,
            @PathVariable(value = "bidprice") String bidprice,
            @PathVariable(value = "cp") String cp,
            @RequestParam(value = "cb") String cb,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        response.setStatus(204);
    }
}
