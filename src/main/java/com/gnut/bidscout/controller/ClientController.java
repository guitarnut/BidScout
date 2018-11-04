package com.gnut.bidscout.controller;

import com.gnut.bidscout.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class ClientController {

    private final ClientService service;

    @Autowired
    public ClientController(ClientService service) {
        this.service = service;
    }

    @CrossOrigin(value = "*", maxAge = 3600)
    @RequestMapping(value = "/bid/{id}", method = RequestMethod.GET, produces = "application/json")
    public String getBid(
            @PathVariable("id") String id,
            HttpServletResponse response
    ) {
        return service.getBid(id);
    }
}
