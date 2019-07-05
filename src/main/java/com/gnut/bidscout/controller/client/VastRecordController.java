package com.gnut.bidscout.controller.client;

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
}
