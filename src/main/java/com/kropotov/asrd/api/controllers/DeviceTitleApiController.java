package com.kropotov.asrd.api.controllers;

import com.kropotov.asrd.entities.DeviceTitle;
import com.kropotov.asrd.entities.SystemTitle;
import com.kropotov.asrd.services.DeviceTitleService;
import com.kropotov.asrd.services.SystemTitleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/devices/titles")
public class DeviceTitleApiController {

    private final DeviceTitleService deviceTitleService;
    private final SystemTitleService systemTitleService;

    public DeviceTitleApiController(DeviceTitleService deviceTitleService, SystemTitleService systemTitleService) {
        this.deviceTitleService = deviceTitleService;
        this.systemTitleService = systemTitleService;
    }

    @GetMapping
    public Iterable<DeviceTitle> getDeviceTitlesBySystemTitleId(@RequestParam(value = "systemTitleId", required = false) Long systemTitleId) {
        if (systemTitleId == null) {
            return deviceTitleService.getAll();
        }
        SystemTitle systemTitle = systemTitleService.getById(systemTitleId);
        return systemTitle.getDeviceTitles();
    }
}
