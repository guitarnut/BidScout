package com.gnut.bidscout.controller.client;

import com.gnut.bidscout.model.DisplayAd;
import com.gnut.bidscout.service.inventory.DisplayAdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class DisplayAdController {

    private final DisplayAdService displayAdService;

    @Autowired
    public DisplayAdController(DisplayAdService displayAdService) {
        this.displayAdService = displayAdService;
    }

    @RequestMapping(value = "/display/{id}/save", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public DisplayAd create(
            @RequestBody DisplayAd displayAd,
            @PathVariable("id") String id,
            Authentication auth
    ) {
        return displayAdService.saveDisplayAd(auth, id, displayAd);
    }

    @RequestMapping(value = "/display/{id}/delete", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    public void delete(
            @PathVariable("id") String id,
            Authentication auth
    ) {
        displayAdService.deleteDisplayAd(auth, id);
    }

    @RequestMapping(value = "/display/{id}/view", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public DisplayAd view(
            @PathVariable("id") String id,
            Authentication auth
    ) {
        return displayAdService.getDisplayAd(auth, id);
    }
}
