package com.gnut.bidscout.controller;

import com.gnut.bidscout.model.Campaign;
import com.gnut.bidscout.model.Creative;
import com.gnut.bidscout.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ClientController {

    private final ClientService service;

    @Autowired
    public ClientController(ClientService service) {
        this.service = service;
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/bid/{id}", method = RequestMethod.GET, produces = "application/json")
    public String getBid(
            @PathVariable("id") String id,
            HttpServletResponse response
    ) {
        return service.getBid(id);
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/campaign/create", method = RequestMethod.POST, produces = "application/json")
    public String saveCampaign(
            @RequestBody Campaign campaign,
            HttpServletResponse response
    ) {
        return service.saveCampaign(campaign);
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/campaign/all", method = RequestMethod.POST, produces = "application/json")
    public Map<String, String> getCampaigns(
            HttpServletResponse response
    ) {
        return service.getCampaignNames();
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/campaign/add/creative", method = RequestMethod.POST, produces = "application/json")
    public void addCreativeToCampaign(
            @RequestParam String campaignId,
            @RequestParam String creativeId,
            HttpServletResponse response
    ) {
        service.addCreativeToCampaign(campaignId, creativeId);
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/campaign/get/{id}", method = RequestMethod.POST, produces = "application/json")
    public String getCampaign(
            @PathVariable("id") String campaignId,
            HttpServletResponse response
    ) {
        return service.getCampaign(campaignId);
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/creative/create", method = RequestMethod.POST, produces = "application/json")
    public String saveCampaign(
            @RequestBody Creative creative,
            HttpServletResponse response
    ) {
        return service.saveCreative(creative);
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/creative/all", method = RequestMethod.POST, produces = "application/json")
    public Map<String, String> getCreatives(
            HttpServletResponse response
    ) {
        return service.getCreativeNames();
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/creative/get/{id}", method = RequestMethod.POST, produces = "application/json")
    public String getCreative(
            @PathVariable("id") String creativeId,
            HttpServletResponse response
    ) {
        return service.getCreative(creativeId);
    }
}
