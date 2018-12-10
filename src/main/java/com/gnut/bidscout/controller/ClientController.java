package com.gnut.bidscout.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gnut.bidscout.model.*;
import com.gnut.bidscout.service.ClientService;
import com.iab.openrtb.vast.Vast;
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

    @RequestMapping(value = "/bid/{account}/{id}", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public AuctionRecord getBid(
            @PathVariable("id") String id,
            @PathVariable("account") String account,
            HttpServletResponse response
    ) {
        return service.getBid(account, id);
    }

    @RequestMapping(value = "/bid/all/{account}", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Map<String, String> deleteCreative(
            @PathVariable("account") String account,
            HttpServletResponse response
    ) {
        return service.getAuctionRecordList(account);
    }

    @RequestMapping(value = "/bid/delete/{account}/{id}", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public void deleteBid(
            @PathVariable("id") String id,
            @PathVariable("account") String account,
            HttpServletResponse response
    ) {
        service.deleteBid(account, id);
    }

    @RequestMapping(value = "/bid/deleteall/{account}", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public void deleteAllBids(
            @PathVariable("account") String account,
            HttpServletResponse response
    ) {
        service.deleteAllBids(account);
    }

    @RequestMapping(value = "/biderrors/{account}", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public List<List<String>> getBidErrors(
            @PathVariable("account") String account,
            HttpServletResponse response
    ) {
        return service.getBidErrors(account);
    }

    @RequestMapping(value = "/impressions/{account}/{id}", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public List<ImpressionRecord> getImpressions(
            @PathVariable("id") String id,
            @PathVariable("account") String account,
            HttpServletResponse response
    ) {
        return service.getImpressions(account, id);
    }

    @RequestMapping(value = "/clicks/{account}/{id}", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public List<ClickRecord> getClicks(
            @PathVariable("id") String id,
            @PathVariable("account") String account,
            HttpServletResponse response
    ) {
        return service.getClicks(account, id);
    }

    @RequestMapping(value = "/campaign/create/{account}", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Campaign saveCampaign(
            @PathVariable("account") String account,
            @RequestBody Campaign campaign,
            HttpServletResponse response
    ) {
        return service.saveCampaign(account, campaign);
    }

    @RequestMapping(value = "/campaign/all/{account}", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Map<String, String> getCampaigns(
            @PathVariable("account") String account,
            HttpServletResponse response
    ) {
        return service.getCampaignNames(account);
    }

    @RequestMapping(value = "/campaign/add/creative/{account}/{campaignId}/{creativeId}", method = RequestMethod.POST, produces = "application/json")
    public void addCreativeToCampaign(
            @PathVariable("account") String account,
            @PathVariable("campaignId") String campaignId,
            @PathVariable("creativeId") String creativeId,
            HttpServletResponse response
    ) {
        service.addCreativeToCampaign(account, campaignId, creativeId);
    }

    @RequestMapping(value = "/campaign/remove/creative/{account}/{campaignId}/{creativeId}", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Campaign removeCreativeFromCampaign(
            @PathVariable("account") String account,
            @PathVariable("campaignId") String campaignId,
            @PathVariable("creativeId") String creativeId,
            HttpServletResponse response
    ) {
        return service.removeCreativeFromCampaign(account, campaignId, creativeId);
    }

    @RequestMapping(value = "/campaign/get/{account}/{id}", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Campaign getCampaign(
            @PathVariable("account") String account,
            @PathVariable("id") String campaignId,
            HttpServletResponse response
    ) {
        return service.getCampaign(account, campaignId);
    }

    @RequestMapping(value = "/campaign/getby/{account}/{property}/{value}", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Campaign getCampaignByValue(
            @PathVariable("account") String account,
            @PathVariable("property") String property,
            @PathVariable("value") String value,
            HttpServletResponse response
    ) {
        return service.getCampaignByProperty(account, property, value);
    }

    @RequestMapping(value = "/creative/create/{account}", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Creative saveCreative(
            @RequestBody Creative creative,
            @PathVariable("account") String account,
            HttpServletResponse response
    ) {
        return service.saveCreative(account, creative);
    }

    @RequestMapping(value = "/creative/all/{account}", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Map<String, String> getCreatives(
            @PathVariable("account") String account,
            HttpServletResponse response
    ) {
        return service.getCreativeNames(account);
    }

    @RequestMapping(value = "/creative/all/{account}/{id}", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Map<String, String> getCreativesByCampaign(
            @PathVariable("id") String campaignId,
            @PathVariable("account") String account,
            HttpServletResponse response
    ) {
        return service.getCreativeNamesByCampaign(account, campaignId);
    }

    @RequestMapping(value = "/creative/get/{account}/{id}", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Creative getCreative(
            @PathVariable("id") String creativeId,
            @PathVariable("account") String account,
            HttpServletResponse response
    ) {
        return service.getCreative(account, creativeId);
    }

    @RequestMapping(value = "/xml/create/{account}", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public void saveXml(
            @PathVariable("account") String account,
            @RequestBody Xml xml,
            HttpServletResponse response
    ) {
        service.saveXml(account, xml);
        response.setStatus(204);
    }

    @RequestMapping(value = "/xml/all/{account}", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Map<String, String> getAllXml(
            @PathVariable("account") String account,
            HttpServletResponse response
    ) {
        return service.getAllXml(account);
    }

    @RequestMapping(value = "/campaign/delete/{account}/{id}", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public void deleteCampaign(
            @PathVariable("id") String creativeId,
            @PathVariable("account") String account,
            HttpServletResponse response
    ) {
        service.deleteCampaign(account, creativeId);
        response.setStatus(204);
    }

    @RequestMapping(value = "/creative/delete/{account}/{id}", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public void deleteCreative(
            @PathVariable("id") String creativeId,
            @PathVariable("account") String account,
            HttpServletResponse response
    ) {
        service.deleteCreative(account, creativeId);
        response.setStatus(204);
    }
}
