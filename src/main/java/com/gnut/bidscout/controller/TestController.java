package com.gnut.bidscout.controller;

import com.gnut.bidscout.service.inventory.VastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test")
public class TestController {
    private final VastService service;

    @Autowired
    public TestController(VastService service) {
        this.service = service;
    }
}
