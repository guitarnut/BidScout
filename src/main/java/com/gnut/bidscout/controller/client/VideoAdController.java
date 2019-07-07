package com.gnut.bidscout.controller.client;

import com.gnut.bidscout.model.VideoAd;
import com.gnut.bidscout.service.inventory.VideoAdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class VideoAdController {

    private final VideoAdService videoAdService;

    @Autowired
    public VideoAdController(VideoAdService videoAdService) {
        this.videoAdService = videoAdService;
    }

    @RequestMapping(value = "/video/{id}/save", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public VideoAd create(
            @RequestBody VideoAd videoAd,
            @PathVariable("id") String id,
            Authentication auth
    ) {
        return videoAdService.createVideoAd(auth, id, videoAd);
    }

    @RequestMapping(value = "/video/{id}/delete", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    public void delete(
            @PathVariable("id") String id,
            Authentication auth
    ) {
        videoAdService.deleteVideoAd(auth, id);
    }

    @RequestMapping(value = "/video/{id}/view", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public VideoAd view(
            @PathVariable("id") String id,
            Authentication auth
    ) {
        return videoAdService.getVideoAd(auth, id);
    }
}
