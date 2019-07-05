package com.gnut.bidscout.controller.client;

import com.gnut.bidscout.model.AuctionRecord;
import com.gnut.bidscout.model.Campaign;
import com.gnut.bidscout.model.VastTagRecord;
import com.gnut.bidscout.service.history.VastTagRecordService;
import com.gnut.bidscout.service.inventory.VastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/api")
public class VastRecordController {
    private final VastTagRecordService service;

    @Autowired
    public VastRecordController(VastTagRecordService service) {
        this.service = service;
    }

    @RequestMapping(value = "/vasttagrecord/all", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<VastTagRecord> all(
            HttpServletResponse response
    ) {
        return service.getAllVastTagRecords();
    }

    @RequestMapping(value = "/vasttagrecord/{id}/view", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public VastTagRecord view(
            @PathVariable("id") String id,
            HttpServletResponse response
    ) {
        return service.view(id);
    }

    @RequestMapping(value = "/vasttagrecord/{id}/delete", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    public void delete(
            @PathVariable("id") String id,
            HttpServletResponse response
    ) {
        service.delete(id);
    }
}
