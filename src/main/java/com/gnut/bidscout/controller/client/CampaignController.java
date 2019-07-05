package com.gnut.bidscout.controller.client;

import com.gnut.bidscout.model.Campaign;
import com.gnut.bidscout.model.Limits;
import com.gnut.bidscout.model.Requirements;
import com.gnut.bidscout.service.inventory.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CampaignController {

    private final CampaignService campaignService;

    @Autowired
    public CampaignController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @RequestMapping(value = "/campaign/create", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Campaign create(
            @RequestBody Campaign campaign,
            HttpServletResponse response
    ) {
        return campaignService.createCampaign(response, campaign);
    }

    @RequestMapping(value = "/campaign/all", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<Campaign> all(
            HttpServletResponse response
    ) {
        return campaignService.getCampaigns();
    }

    @RequestMapping(value = "/campaign/{id}/view", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Campaign create(
            @PathVariable("id") String id,
            HttpServletResponse response
    ) {
        return campaignService.getCampaign(id);
    }

    @RequestMapping(value = "/campaign/{id}/save", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Campaign save(
            @PathVariable("id") String id,
            @RequestBody Campaign campaign,
            HttpServletResponse response
    ) {
        return campaignService.saveCampaign(id, campaign);
    }

    @RequestMapping(value = "/campaign/{id}/delete", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    public void delete(
            @PathVariable("id") String id,
            HttpServletResponse response
    ) {
        campaignService.deleteCampaign(id);
    }

    @RequestMapping(value = "/campaign/{id}/targeting/save", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Requirements saveTargeting(
            @PathVariable("id") String id,
            @RequestBody Requirements requirements,
            HttpServletResponse response
    ) {
        return campaignService.saveCampaignRequirements(id, requirements);
    }

    @RequestMapping(value = "/campaign/{id}/targeting/view", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Requirements saveTargeting(
            @PathVariable("id") String id,
            HttpServletResponse response
    ) {
        return campaignService.getCampaignRequirements(id);
    }

    @RequestMapping(value = "/campaign/{id}/pacing/save", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Limits savePacing(
            @PathVariable("id") String id,
            @RequestBody Limits limits,
            HttpServletResponse response
    ) {
        return campaignService.saveCampaignLimits(id, limits);
    }

    @RequestMapping(value = "/campaign/{id}/pacing/view", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Limits savePacing(
            @PathVariable("id") String id,
            HttpServletResponse response
    ) {
        return campaignService.getCampaignLimits(id);
    }

    @RequestMapping(value = "/campaign/{id}/creative/add", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public void addCreative(
            @PathVariable("id") String id,
            @RequestParam("id") String creative,
            HttpServletResponse response
    ) {
        campaignService.addCreative(response, id, creative);
    }

    @RequestMapping(value = "/campaign/{id}/creative/remove", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public void removeCreative(
            @PathVariable("id") String id,
            @RequestParam("id") String creative,
            HttpServletResponse response
    ) {
        campaignService.removeCreative(response, id, creative);
    }

    @RequestMapping(value = "/campaign/{id}/statistics/reset", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Campaign resetStatistics(
            @PathVariable("id") String id,
            HttpServletResponse response
    ) {
        return campaignService.resetStatistics(id);
    }
}
