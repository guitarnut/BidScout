package com.gnut.bidscout.controller;

import com.gnut.bidscout.service.BidRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/bid")
public class BidRequestController {
    private BidRequestService service;

    @Autowired
    public BidRequestController(BidRequestService service) {
        this.service = service;
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value="/{bidder}/{publisher}", method = RequestMethod.POST, produces = "application/json")
    public String handleBidRequestPost(
            @PathVariable(value = "bidder") String bidder,
            @PathVariable(value = "publisher") String publisher,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
       return service.handleRequest(bidder, publisher, request, response);
    }
}
