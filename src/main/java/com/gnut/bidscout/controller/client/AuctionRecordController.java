package com.gnut.bidscout.controller.client;

import com.gnut.bidscout.model.AuctionRecord;
import com.gnut.bidscout.service.history.AuctionRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
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
            HttpServletResponse response
    ) {
        return service.getAllAuctionRecords();
    }
}
