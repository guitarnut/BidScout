package com.gnut.bidscout.controller;

import com.iab.openrtb.vast.Error;
import com.iab.openrtb.vast.Vast;
import com.iab.openrtb.vast.ad.AdVerifications;
import com.iab.openrtb.vast.ad.adverifications.FlashResource;
import com.iab.openrtb.vast.ad.adverifications.Verification;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping(value = "/test")
public class TestController {

    @RequestMapping(value = "/xml", method = RequestMethod.GET, produces = "application/xml")
    @ResponseBody
    public AdVerifications test() {
        Vast v = new Vast();
        Error e = new Error();
        e.setValue("foo");
        v.setError(e);
        Verification ver = new Verification();
        FlashResource flashResource = new FlashResource();
        flashResource.setApiFramework("api");
        flashResource.setValue("<p>le flash</p>");
        ver.setFlashResource(flashResource);
        AdVerifications a = new AdVerifications();
        a.setVerifications(Arrays.asList(ver));
        return a;
    }
}
