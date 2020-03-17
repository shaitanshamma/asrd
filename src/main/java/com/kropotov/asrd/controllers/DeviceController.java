package com.kropotov.asrd.controllers;

import com.kropotov.asrd.entities.Device;
import com.kropotov.asrd.services.DeviceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/devices")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping
    public String displaySystems(Model model) {
        List<Device> devices = deviceService.getAll();
        model.addAttribute("devices", devices);
        return "devices/list-devices";
    }
}
