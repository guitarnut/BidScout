package com.gnut.bidscout.service.inventory;

import com.gnut.bidscout.db.VideoAdDao;
import com.gnut.bidscout.model.Creative;
import com.gnut.bidscout.model.VideoAd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class VideoAdService {

    private final VideoAdDao videoAdDao;
    private final CreativeService creativeService;

    @Autowired
    public VideoAdService(VideoAdDao videoAdDao, CreativeService creativeService) {
        this.videoAdDao = videoAdDao;
        this.creativeService = creativeService;
    }

    public VideoAd createVideoAd(Authentication auth, String id, VideoAd videoAd) {
        Optional<VideoAd> existing = videoAdDao.findByCreativeIdAndOwner(id, getAccount(auth));
        if (existing.isPresent()) {
            videoAd.setId(existing.get().getId());
        }
        videoAd.setCreativeId(id);
        videoAd.setOwner(getAccount(auth));
        creativeService.setCreativeType(id, Creative.Type.VAST);
        return videoAdDao.save(videoAd);
    }

    public void deleteVideoAd(Authentication auth, String id) {
        Optional<VideoAd> videoAd = videoAdDao.findByCreativeIdAndOwner(id, getAccount(auth));
        if (videoAd.isPresent()) {
            videoAdDao.delete(videoAd.get());
        }
    }

    public VideoAd getVideoAd(Authentication auth, String id) {
        Optional<VideoAd> videoAd = videoAdDao.findByCreativeIdAndOwner(id, getAccount(auth));
        if (videoAd.isPresent()) {
            return videoAd.get();
        } else {
            return null;
        }
    }

    public Optional<VideoAd> getVideoAd(String id) {
        return videoAdDao.findByCreativeId(id);
    }

    private String getAccount(Authentication auth) {
        return auth.getAuthorities().iterator().next().getAuthority();
    }
}
