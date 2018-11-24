package com.gnut.bidscout.controller;

import com.gnut.bidscout.service.BidRequestService;
import com.iab.openrtb.response.BidResponse;
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

    @RequestMapping(value="/{bidder}/{publisher}", method = RequestMethod.POST, produces = "application/json")
    public BidResponse handleBidRequestPostWithKey(
            @PathVariable(value = "bidder") String bidder,
            @PathVariable(value = "publisher") String publisher,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
       return service.handleRequest(bidder, publisher, request, response);
    }

    @RequestMapping(value="/{bidder}", method = RequestMethod.POST, produces = "application/json")
    public BidResponse handleBidRequestPost(
            @PathVariable(value = "bidder") String bidder,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        return service.handleRequest(bidder, null, request, response);
    }
}
