package com.gnut.bidscout.service.inventory;

import com.gnut.bidscout.db.DisplayAdDao;
import com.gnut.bidscout.model.DisplayAd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Component
public class DisplayAdService {

    private final DisplayAdDao displayAdDao;

    @Autowired
    public DisplayAdService(DisplayAdDao displayAdDao) {
        this.displayAdDao = displayAdDao;
    }

    public DisplayAd saveDisplayAd(HttpServletResponse response, String id, DisplayAd displayAd) {
        Optional<DisplayAd> existing = displayAdDao.findByCreativeId(id);
        if (existing.isPresent()) {
           displayAd.setId(existing.get().getId());
        }
        displayAd.setCreativeId(id);
        return displayAdDao.save(displayAd);
    }

    public void deleteDisplayAd(HttpServletResponse response, String id) {
        Optional<DisplayAd> displayAd = displayAdDao.findByCreativeId(id);
        if (displayAd.isPresent()) {
            displayAdDao.delete(displayAd.get());
        }
    }

    public DisplayAd getDisplayAd(HttpServletResponse response, String id) {
        Optional<DisplayAd> displayAd = displayAdDao.findByCreativeId(id);
        if (displayAd.isPresent()) {
            return displayAd.get();
        } else {
            return null;
        }
    }

    public Optional<DisplayAd> getDisplayAd(String id) {
        return displayAdDao.findByCreativeId(id);
    }
}
