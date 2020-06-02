package com.kropotov.asrd.api.controllers;

import com.kropotov.asrd.entities.items.DeviceComponent;
import com.kropotov.asrd.entities.titles.DeviceComponentTitle;
import com.kropotov.asrd.entities.titles.DeviceTitle;
import com.kropotov.asrd.entities.titles.SystemTitle;
import com.kropotov.asrd.services.springdatajpa.titles.DeviceTitleService;
import com.kropotov.asrd.services.springdatajpa.titles.SystemTitleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/devices")
@RequiredArgsConstructor
public class DeviceApiController {

    private final DeviceTitleService deviceTitleService;
    private final SystemTitleService systemTitleService;


    @GetMapping("/titles")
    public Iterable<DeviceTitle> getDeviceTitlesBySystemTitleId(@RequestParam(value = "systemTitleId", required = false) Long systemTitleId) {
        if (systemTitleId == null) {
            return deviceTitleService.getAll().get();
        }
        SystemTitle systemTitle = systemTitleService.getById(systemTitleId).orElse(null);
        return systemTitle.getDeviceTitles();
    }

    @GetMapping("/components")
    public Iterable<DeviceComponent> getDeviceComponentsByDeviceTitleId(@RequestParam("deviceTitleId") Long deviceTitleId) {

        List<DeviceComponentTitle> componentTitles = deviceTitleService.getById(deviceTitleId).orElse(null).getComponentTitles();
        List<DeviceComponent> components = new ArrayList<>();
        for (DeviceComponentTitle d: componentTitles) {
            DeviceComponent deviceComponent = new DeviceComponent();
            deviceComponent.setTitle(d);
            components.add(deviceComponent);
        }
        return components;
    }
}
