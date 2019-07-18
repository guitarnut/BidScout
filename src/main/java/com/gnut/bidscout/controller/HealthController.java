package com.gnut.bidscout.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/health")
public class HealthController {

    @RequestMapping(value="/server", method = RequestMethod.GET, produces = "application/json")
    public void healthCheck(
            HttpServletResponse response
    ) {
        response.setStatus(204);
    }
}
