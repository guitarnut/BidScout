package com.gnut.bidscout.controller.client;

import com.gnut.bidscout.model.AuctionRecord;
import com.gnut.bidscout.service.history.AuctionRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AuctionRecordController {
    private final AuctionRecordService service;

    @Autowired
    public AuctionRecordController(AuctionRecordService service) {
        this.service = service;
    }

    @RequestMapping(value = "/auctionrecord/all", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<AuctionRecord> all(
            Authentication auth
    ) {
        return service.getAllAuctionRecords(auth);
    }

    @RequestMapping(value = "/auctionrecord/{id}/view", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public AuctionRecord view(
            @PathVariable("id") String id,
            Authentication auth
    ) {
        return service.view(auth, id);
    }

    @RequestMapping(value = "/auctionrecord/{id}/delete", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    public void delete(
            @PathVariable("id") String id,
            Authentication auth
    ) {
        service.delete(auth, id);
    }

    @RequestMapping(value = "/auctionrecord/clear", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    public List<AuctionRecord> delete(
            Authentication auth
    ) {
        return service.deleteAll(auth);
    }
}
