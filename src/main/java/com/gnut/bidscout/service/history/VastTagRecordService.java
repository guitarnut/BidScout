package com.gnut.bidscout.service.history;

import com.gnut.bidscout.db.VastTagRecordDao;
import com.gnut.bidscout.model.AuctionRecord;
import com.gnut.bidscout.model.VastTagRecord;
import com.gnut.bidscout.service.user.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class VastTagRecordService {

    private final VastTagRecordDao vastTagRecordDao;
    private final AccountService accountService;

    @Autowired
    public VastTagRecordService(
            VastTagRecordDao vastTagRecordDao,
            AccountService accountService
    ) {
        this.vastTagRecordDao = vastTagRecordDao;
        this.accountService = accountService;
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
            accountService.deleteVastTagRecord(getAccount(auth));
        }
    }

    private String getAccount(Authentication auth) {
        return auth.getAuthorities().iterator().next().getAuthority();
    }

}
