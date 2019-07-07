package com.gnut.bidscout.controller.client;

import com.gnut.bidscout.model.Creative;
import com.gnut.bidscout.model.Limits;
import com.gnut.bidscout.model.Requirements;
import com.gnut.bidscout.service.inventory.CreativeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CreativeController {

    private final CreativeService creativeService;

    @Autowired
    public CreativeController(CreativeService creativeService) {
        this.creativeService = creativeService;
    }

    @RequestMapping(value = "/creative/create", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Creative create(
            @RequestBody Creative creative,
            Authentication auth,
            HttpServletResponse response
    ) {
        return creativeService.createCreative(auth, response, creative);
    }

    @RequestMapping(value = "/creative/all", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<Creative> all(
            Authentication auth
    ) {
        return creativeService.getCreatives(auth);
    }

    @RequestMapping(value = "/creative/{id}/view", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Creative create(
            @PathVariable("id") String id,
            Authentication auth
    ) {
        return creativeService.getCreative(auth, id);
    }

    @RequestMapping(value = "/creative/{id}/save", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Creative save(
            @PathVariable("id") String id,
            @RequestBody Creative creative,
            Authentication auth
    ) {
        return creativeService.saveCreative(auth, id, creative);
    }

    @RequestMapping(value = "/creative/{id}/delete", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    public void delete(
            @PathVariable("id") String id,
            Authentication auth
    ) {
        creativeService.deleteCreative(id, auth);
    }

    @RequestMapping(value = "/creative/{id}/targeting/save", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Requirements saveTargeting(
            @PathVariable("id") String id,
            @RequestBody Requirements requirements,
            Authentication auth
    ) {
        return creativeService.saveCreativeRequirements(auth, id, requirements);
    }

    @RequestMapping(value = "/creative/{id}/targeting/view", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Requirements saveTargeting(
            @PathVariable("id") String id,
            Authentication auth
    ) {
        return creativeService.getCreativeRequirements(auth, id);
    }

    @RequestMapping(value = "/creative/{id}/pacing/save", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Limits savePacing(
            @PathVariable("id") String id,
            @RequestBody Limits limits,
            Authentication auth
    ) {
        return creativeService.saveCreativeLimits(auth, id, limits);
    }

    @RequestMapping(value = "/creative/{id}/pacing/view", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Limits savePacing(
            @PathVariable("id") String id,
            Authentication auth
    ) {
        return creativeService.getCreativeLimits(auth, id);
    }

    @RequestMapping(value = "/creative/{id}/statistics/reset", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Creative resetStatistics(
            @PathVariable("id") String id,
            Authentication auth
    ) {
        return creativeService.resetStatistics(auth, id);
    }
}
