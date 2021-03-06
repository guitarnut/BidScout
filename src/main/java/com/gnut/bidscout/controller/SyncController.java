package com.gnut.bidscout.controller;

import com.gnut.bidscout.service.SyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/sync")
public class SyncController {

    private final SyncService service;

    @Autowired
    public SyncController(SyncService service) {
        this.service = service;
    }

    @CrossOrigin(value = "*", maxAge = 3600)
    @RequestMapping(value = "/user/{id}/{campaign}/{creative}", method = RequestMethod.GET, produces = "text/html")
    public void syncUser(
            HttpServletRequest request,
            HttpServletResponse response,
            @PathVariable(value = "id") String id,
            @PathVariable(value = "campaign") String campaign,
            @PathVariable(value = "creative") String creative
            ) {
        service.sync(request, response, id, campaign, creative);
    }
}
