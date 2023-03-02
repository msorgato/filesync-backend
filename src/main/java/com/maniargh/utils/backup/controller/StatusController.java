package com.maniargh.utils.backup.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping("/status")
public class StatusController {

    private static final Logger LOG = Logger.getLogger("status");

    @RequestMapping(value="/ping", method= RequestMethod.GET)
    public void ping() {
        LOG.info("Status PING called");
    }
}
