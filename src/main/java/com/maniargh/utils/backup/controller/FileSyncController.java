package com.maniargh.utils.backup.controller;

import com.maniargh.utils.backup.service.FileSyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/file-sync")
public class FileSyncController {

    @Autowired
    FileSyncService fsService;


}
