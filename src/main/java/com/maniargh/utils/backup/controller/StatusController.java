package com.maniargh.utils.backup.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping("/status")
public class StatusController {

    private static final Logger LOG = Logger.getLogger("status");

    /**
     * Alive status check
     */
    @RequestMapping(value="/check", method= RequestMethod.GET)
    public String ping() {
        LOG.info("Status PING called");

        return "FileSync alive and kicking";
    }
}
