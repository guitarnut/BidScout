package com.gnut.bidscout.controller.client;

import com.gnut.bidscout.model.Campaign;
import com.gnut.bidscout.model.Limits;
import com.gnut.bidscout.model.Requirements;
import com.gnut.bidscout.service.inventory.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/api/campaign")
public class CampaignController {

    private final CampaignService campaignService;

    @Autowired
    public CampaignController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Campaign create(
            @RequestBody Campaign campaign,
            Authentication auth,
            HttpServletResponse response
    ) {
        return campaignService.createCampaign(auth, response, campaign);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<Campaign> all(
            Authentication auth
    ) {
        return campaignService.getCampaigns(auth);
    }

    @RequestMapping(value = "/{id}/view", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Campaign create(
            @PathVariable("id") String id,
            Authentication auth
    ) {
        return campaignService.getCampaign(auth, id);
    }

    @RequestMapping(value = "/{id}/save", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Campaign save(
            @PathVariable("id") String id,
            @RequestBody Campaign campaign,
            Authentication auth
    ) {
        return campaignService.saveCampaign(auth, id, campaign);
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    public void delete(
            @PathVariable("id") String id,
            Authentication auth
    ) {
        campaignService.deleteCampaign(auth, id);
    }

    @RequestMapping(value = "/{id}/targeting/save", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Requirements saveTargeting(
            @PathVariable("id") String id,
            @RequestBody Requirements requirements,
            Authentication auth
    ) {
        return campaignService.saveCampaignRequirements(auth, id, requirements);
    }

    @RequestMapping(value = "/{id}/targeting/view", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Requirements saveTargeting(
            @PathVariable("id") String id,
            Authentication auth
    ) {
        return campaignService.getCampaignRequirements(auth, id);
    }

    @RequestMapping(value = "/{id}/pacing/save", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Limits savePacing(
            @PathVariable("id") String id,
            @RequestBody Limits limits,
            Authentication auth
    ) {
        return campaignService.saveCampaignLimits(auth, id, limits);
    }

    @RequestMapping(value = "/{id}/pacing/view", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Limits savePacing(
            @PathVariable("id") String id,
            Authentication auth
    ) {
        return campaignService.getCampaignLimits(auth, id);
    }

    @RequestMapping(value = "/{id}/creative/add", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public void addCreative(
            @PathVariable("id") String id,
            @RequestParam("id") String creative,
            Authentication auth,
            HttpServletResponse response
    ) {
        campaignService.addCreative(auth, response, id, creative);
    }

    @RequestMapping(value = "/{id}/creative/remove", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public void removeCreative(
            @PathVariable("id") String id,
            @RequestParam("id") String creative,
            Authentication auth,
            HttpServletResponse response
    ) {
        campaignService.removeCreative(auth, response, id, creative);
    }

    @RequestMapping(value = "/{id}/statistics/reset", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Campaign resetStatistics(
            @PathVariable("id") String id,
            Authentication auth
    ) {
        return campaignService.resetStatistics(auth, id);
    }
}
