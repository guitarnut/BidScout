package com.gnut.bidscout.controller.client;

import com.gnut.bidscout.model.Creative;
import com.gnut.bidscout.model.Limits;
import com.gnut.bidscout.model.Requirements;
import com.gnut.bidscout.service.inventory.CreativeService;
import org.springframework.beans.factory.annotation.Autowired;
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
            HttpServletResponse response
    ) {
        return creativeService.createCreative(response, creative);
    }

    @RequestMapping(value = "/creative/all", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<Creative> all(
            HttpServletResponse response
    ) {
        return creativeService.getCreatives();
    }

    @RequestMapping(value = "/creative/{id}/view", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Creative create(
            @PathVariable("id") String id,
            HttpServletResponse response
    ) {
        return creativeService.getCreative(id);
    }

    @RequestMapping(value = "/creative/{id}/save", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Creative save(
            @PathVariable("id") String id,
            @RequestBody Creative creative,
            HttpServletResponse response
    ) {
        return creativeService.saveCreative(id, creative);
    }

    @RequestMapping(value = "/creative/{id}/delete", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    public void delete(
            @PathVariable("id") String id,
            HttpServletResponse response
    ) {
        creativeService.deleteCreative(id);
    }

    @RequestMapping(value = "/creative/{id}/targeting/save", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Requirements saveTargeting(
            @PathVariable("id") String id,
            @RequestBody Requirements requirements,
            HttpServletResponse response
    ) {
        return creativeService.saveCreativeRequirements(id, requirements);
    }

    @RequestMapping(value = "/creative/{id}/targeting/view", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Requirements saveTargeting(
            @PathVariable("id") String id,
            HttpServletResponse response
    ) {
        return creativeService.getCreativeRequirements(id);
    }

    @RequestMapping(value = "/creative/{id}/pacing/save", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Limits savePacing(
            @PathVariable("id") String id,
            @RequestBody Limits limits,
            HttpServletResponse response
    ) {
        return creativeService.saveCreativeLimits(id, limits);
    }

    @RequestMapping(value = "/creative/{id}/pacing/view", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Limits savePacing(
            @PathVariable("id") String id,
            HttpServletResponse response
    ) {
        return creativeService.getCreativeLimits(id);
    }

    @RequestMapping(value = "/creative/{id}/statistics/reset", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Creative resetStatistics(
            @PathVariable("id") String id,
            HttpServletResponse response
    ) {
        return creativeService.resetStatistics(id);
    }
}
