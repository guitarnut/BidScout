package com.gnut.bidscout.controller.client;

import com.gnut.bidscout.model.UserAccountStatistics;
import com.gnut.bidscout.service.user.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/api")
public class AccountController {

    private final AccountService service;

    @Autowired
    public AccountController(AccountService service) {
        this.service = service;
    }

    @RequestMapping(value = "/account/statistics/view", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public UserAccountStatistics view(
            HttpServletResponse response
    ) {
        return service.getAccountStatistics();
    }
}
