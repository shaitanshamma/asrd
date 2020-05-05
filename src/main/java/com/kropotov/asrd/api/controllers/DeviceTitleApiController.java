package com.kropotov.asrd.api.controllers;

/*@RestController
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
        SystemTitleDto systemTitle = systemTitleService.getById(systemTitleId);
        return systemTitle.getDeviceTitles();
    }
}*/
