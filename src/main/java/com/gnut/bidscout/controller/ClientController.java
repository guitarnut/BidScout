package com.gnut.bidscout.controller;

import com.gnut.bidscout.model.*;
import com.gnut.bidscout.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@PreAuthorize("hasRole('ROLE_USER')")
public class ClientController {

    private final ClientService service;

    @Autowired
    public ClientController(ClientService service) {
        this.service = service;
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/bid/{account}/{id}", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody public AuctionRecord getBid(
            @PathVariable("id") String id,
            @PathVariable("account") String account,
            HttpServletResponse response
    ) {
        return service.getBid(account, id);
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/biderrors/{account}", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody public List<List<String>> getBidErrors(
            @PathVariable("account") String account,
            HttpServletResponse response
    ) {
        return service.getBidErrors(account);
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/impressions/{account}/{id}", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody public List<ImpressionRecord> getImpressions(
            @PathVariable("id") String id,
            @PathVariable("account") String account,
            HttpServletResponse response
    ) {
        return service.getImpressions(account, id);
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/clicks/{account}/{id}", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody public List<ClickRecord> getClicks(
            @PathVariable("id") String id,
            @PathVariable("account") String account,
            HttpServletResponse response
    ) {
        return service.getClicks(account, id);
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/campaign/create/{account}", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody public String saveCampaign(
            @PathVariable("account") String account,
            @RequestBody Campaign campaign,
            HttpServletResponse response
    ) {
        return service.saveCampaign(account, campaign);
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/campaign/all/{account}", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody public Map<String, String> getCampaigns(
            @PathVariable("account") String account,
            HttpServletResponse response
    ) {
        return service.getCampaignNames(account);
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/campaign/add/creative/{account}/{campaignId}/{creativeId}", method = RequestMethod.POST, produces = "application/json")
    public void addCreativeToCampaign(
            @PathVariable("account") String account,
            @PathVariable("campaignId") String campaignId,
            @PathVariable("creativeId") String creativeId,
            HttpServletResponse response
    ) {
        service.addCreativeToCampaign(account, campaignId, creativeId);
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/campaign/remove/creative/{account}/{campaignId}/{creativeId}", method = RequestMethod.POST, produces = "application/json")
    public Campaign removeCreativeFromCampaign(
            @PathVariable("account") String account,
            @PathVariable("campaignId") String campaignId,
            @PathVariable("creativeId") String creativeId,
            HttpServletResponse response
    ) {
        return service.removeCreativeFromCampaign(account, campaignId, creativeId);
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/campaign/get/{account}/{id}", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody public String getCampaign(
            @PathVariable("account") String account,
            @PathVariable("id") String campaignId,
            HttpServletResponse response
    ) {
        return service.getCampaign(account, campaignId);
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/campaign/getby/{account}/{property}/{value}", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody public Campaign getCampaignByValue(
            @PathVariable("account") String account,
            @PathVariable("property") String property,
            @PathVariable("value") String value,
            HttpServletResponse response
    ) {
        return service.getCampaignByProperty(account, property, value);
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/creative/create/{account}", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody public Creative saveCreative(
            @RequestBody Creative creative,
            @PathVariable("account") String account,
            HttpServletResponse response
    ) {
        return service.saveCreative(account, creative);
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/creative/all/{account}", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody public Map<String, String> getCreatives(
            @PathVariable("account") String account,
            HttpServletResponse response
    ) {
        return service.getCreativeNames(account);
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/creative/all/{account}/{id}", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody public Map<String, String> getCreativesByCampaign(
            @PathVariable("id") String campaignId,
            @PathVariable("account") String account,
            HttpServletResponse response
    ) {
        return service.getCreativeNamesByCampaign(account, campaignId);
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/creative/get/{account}/{id}", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody public String getCreative(
            @PathVariable("id") String creativeId,
            @PathVariable("account") String account,
            HttpServletResponse response
    ) {
        return service.getCreative(account, creativeId);
    }
}
