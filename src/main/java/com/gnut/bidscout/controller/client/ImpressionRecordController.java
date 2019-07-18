package com.gnut.bidscout.controller.client;

import com.gnut.bidscout.model.ImpressionRecord;
import com.gnut.bidscout.service.inventory.ImpressionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/impressionrecord")
public class ImpressionRecordController {
    private final ImpressionService service;

    @Autowired
    public ImpressionRecordController(ImpressionService service) {
        this.service = service;
    }

    @RequestMapping(value = "/{id}/all", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<ImpressionRecord> all(
            @PathVariable("id") String id,
            Authentication auth
    ) {
        return service.getImpressions(auth, id);
    }

    @RequestMapping(value = "/{id}/vast/all", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<ImpressionRecord> allVast(
            @PathVariable("id") String id,
            Authentication auth
    ) {
        return service.getVastImpressions(auth, id);
    }
}
