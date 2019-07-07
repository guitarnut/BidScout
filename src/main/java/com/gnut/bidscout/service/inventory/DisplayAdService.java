package com.gnut.bidscout.service.inventory;

import com.gnut.bidscout.db.DisplayAdDao;
import com.gnut.bidscout.model.Creative;
import com.gnut.bidscout.model.DisplayAd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Component
public class DisplayAdService {

    private final DisplayAdDao displayAdDao;
    private final CreativeService creativeService;

    @Autowired
    public DisplayAdService(DisplayAdDao displayAdDao, CreativeService creativeService) {
        this.displayAdDao = displayAdDao;
        this.creativeService = creativeService;
    }

    public DisplayAd saveDisplayAd(Authentication auth, String id, DisplayAd displayAd) {
        Optional<DisplayAd> existing = displayAdDao.findByCreativeIdAndOwner(id, getAccount(auth));
        if (existing.isPresent()) {
           displayAd.setId(existing.get().getId());
        }
        displayAd.setCreativeId(id);
        displayAd.setOwner(getAccount(auth));
        creativeService.setCreativeType(id, Creative.Type.DISPLAY);
        return displayAdDao.save(displayAd);
    }

    public void deleteDisplayAd(Authentication auth, String id) {
        Optional<DisplayAd> displayAd = displayAdDao.findByCreativeIdAndOwner(id, getAccount(auth));
        if (displayAd.isPresent()) {
            displayAdDao.delete(displayAd.get());
        }
    }

    public DisplayAd getDisplayAd(Authentication auth, String id) {
        Optional<DisplayAd> displayAd = displayAdDao.findByCreativeIdAndOwner(id, getAccount(auth));
        if (displayAd.isPresent()) {
            return displayAd.get();
        } else {
            return null;
        }
    }

    public Optional<DisplayAd> getDisplayAd(String id) {
        return displayAdDao.findByCreativeId(id);
    }

    private String getAccount(Authentication auth) {
        return auth.getAuthorities().iterator().next().getAuthority();
    }
}
