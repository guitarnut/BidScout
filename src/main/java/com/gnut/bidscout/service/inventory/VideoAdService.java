package com.gnut.bidscout.service.inventory;

import com.gnut.bidscout.db.VideoAdDao;
import com.gnut.bidscout.model.Creative;
import com.gnut.bidscout.model.VideoAd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
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

    public VideoAd createVideoAd(HttpServletResponse response, String id, VideoAd videoAd) {
        Optional<VideoAd> existing = videoAdDao.findByCreativeId(id);
        if (existing.isPresent()) {
            videoAd.setId(existing.get().getId());
        }
        videoAd.setCreativeId(id);
        creativeService.setCreativeType(id, Creative.Type.VAST);
        return videoAdDao.save(videoAd);
    }

    public void deleteVideoAd(HttpServletResponse response, String id) {
        Optional<VideoAd> videoAd = videoAdDao.findByCreativeId(id);
        if (videoAd.isPresent()) {
            videoAdDao.delete(videoAd.get());
        }
    }

    public VideoAd getVideoAd(HttpServletResponse response, String id) {
        Optional<VideoAd> videoAd = videoAdDao.findByCreativeId(id);
        if (videoAd.isPresent()) {
            return videoAd.get();
        } else {
            return null;
        }
    }

    public Optional<VideoAd> getVideoAd(String id) {
        return videoAdDao.findByCreativeId(id);
    }
}
