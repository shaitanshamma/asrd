package com.kropotov.asrd.controllers.titles;


import com.kropotov.asrd.entities.titles.DeviceTitle;
import com.kropotov.asrd.services.springdatajpa.titles.DeviceTitleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/titles/devices")
@Controller
@RequiredArgsConstructor
public class DeviceTitleController {
    private static final String DEVICE_TITLE_FORM = "titles/devices/form";

    private final DeviceTitleService deviceTitleService;

    @GetMapping
    public String displayDeviceTitles(Model model) {
        model.addAttribute("deviceTitles", deviceTitleService.getAll());
        return "titles/devices/list";
    }

    @GetMapping("/{id}")
    public String displayDeviceTitleForm(@PathVariable Long id, Model model) {
        model.addAttribute("deviceTitle", deviceTitleService.getById(id).orElse(new DeviceTitle()));
        return DEVICE_TITLE_FORM;
    }

    @PostMapping("/edit")
    public String editDeviceTitle(@Valid @ModelAttribute("deviceTitle") DeviceTitle deviceTitle, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("titleCreationError", "BindingResult error!");
            return DEVICE_TITLE_FORM;
        }
        DeviceTitle existing = deviceTitleService.getByTitle(deviceTitle.getTitle());
        if (existing != null && !existing.getId().equals(deviceTitle.getId())) {
            model.addAttribute("deviceTitle", deviceTitle);
            model.addAttribute("titleCreationError", "Такая тема уже существует");
            return DEVICE_TITLE_FORM;
        }
        deviceTitleService.save(deviceTitle);
        return "redirect:/titles/devices";
    }
}
