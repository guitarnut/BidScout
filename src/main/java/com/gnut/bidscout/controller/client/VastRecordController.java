package com.gnut.bidscout.controller.client;

import com.gnut.bidscout.model.AuctionRecord;
import com.gnut.bidscout.model.VastTagRecord;
import com.gnut.bidscout.service.history.VastTagRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/api/vasttagrecord")
public class VastRecordController {
    private final VastTagRecordService service;

    @Autowired
    public VastRecordController(VastTagRecordService service) {
        this.service = service;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<VastTagRecord> all(
            Authentication auth
    ) {
        return service.getAllVastTagRecords(auth);
    }

    @RequestMapping(value = "/{id}/view", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public VastTagRecord view(
            @PathVariable("id") String id,
            Authentication auth
    ) {
        return service.view(id, auth);
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    public void delete(
            @PathVariable("id") String id,
            Authentication auth
    ) {
        service.delete(id, auth);
    }

    @RequestMapping(value = "/clear", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    public List<VastTagRecord> delete(
            Authentication auth
    ) {
        return service.deleteAll(auth);
    }
}
