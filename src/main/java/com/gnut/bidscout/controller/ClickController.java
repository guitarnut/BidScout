package com.gnut.bidscout.controller;

import com.gnut.bidscout.service.ClickService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/click")
public class ClickController {
    private ClickService service;

    @Autowired
    public ClickController(ClickService service) {
        this.service = service;
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value="/{bid}/{campaign}", method = RequestMethod.GET)
    public void handleClick(
            @PathVariable(value = "bid") String bid,
            @PathVariable(value = "campaign") String campaign,
            @RequestParam(value = "cb", required = false) String cb,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        service.handleRequest(request, response, bid, campaign, cb);
    }
}
