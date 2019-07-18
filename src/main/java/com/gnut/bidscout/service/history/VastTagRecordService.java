package com.gnut.bidscout.service.history;

import com.gnut.bidscout.db.VastTagRecordDao;
import com.gnut.bidscout.model.AuctionRecord;
import com.gnut.bidscout.model.VastTagRecord;
import com.gnut.bidscout.service.VideoEventService;
import com.gnut.bidscout.service.inventory.ImpressionService;
import com.gnut.bidscout.service.user.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class VastTagRecordService {

    private final VastTagRecordDao vastTagRecordDao;
    private final AccountService accountService;
    private final ImpressionService impressionService;
    private final VideoEventService videoEventService;

    @Autowired
    public VastTagRecordService(
            VastTagRecordDao vastTagRecordDao,
            AccountService accountService,
            ImpressionService impressionService,
            VideoEventService videoEventService
    ) {
        this.vastTagRecordDao = vastTagRecordDao;
        this.accountService = accountService;
        this.impressionService = impressionService;
        this.videoEventService = videoEventService;
    }

    public List<VastTagRecord> getAllVastTagRecords(Authentication auth) {
        return vastTagRecordDao.findAllByOwner(getAccount(auth));
    }

    public VastTagRecord view(String id, Authentication auth) {
        VastTagRecord a = vastTagRecordDao.findByOwnerAndId(getAccount(auth), id);
        return a;
    }

    public void delete(String id, Authentication auth) {
        VastTagRecord a = vastTagRecordDao.findByOwnerAndId(getAccount(auth), id);
        if (a != null) {
            vastTagRecordDao.deleteById(id);
            impressionService.deleteVastTagRecordImpression(a.getRequestId());
            videoEventService.deleteVastTagEvent(a.getRequestId());
            accountService.deleteVastTagRecord(getAccount(auth));
        }
    }

    private String getAccount(Authentication auth) {
        return auth.getAuthorities().iterator().next().getAuthority();
    }

    public List<VastTagRecord> deleteAll(Authentication auth) {
        String account = getAccount(auth);
        List<VastTagRecord> records = vastTagRecordDao.findAllByOwner(account);
        records.forEach(r -> {
            impressionService.deleteVastTagRecordImpression(r.getRequestId());
            videoEventService.deleteVastTagEvent(r.getRequestId());
            accountService.deleteVastTagRecord(account);
        });
        vastTagRecordDao.deleteAllByOwner(account);
        return Collections.emptyList();
    }
}
