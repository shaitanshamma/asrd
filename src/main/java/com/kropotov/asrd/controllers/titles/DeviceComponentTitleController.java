package com.kropotov.asrd.controllers.titles;

import com.kropotov.asrd.entities.titles.DeviceComponentTitle;
import com.kropotov.asrd.services.springdatajpa.titles.DeviceComponentTitleService;
import com.kropotov.asrd.services.springdatajpa.titles.DeviceTitleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/titles/devicecomponents")
@Controller
@RequiredArgsConstructor
public class DeviceComponentTitleController {
    private static final String DEVICE_COMPONENT_TITLE_FORM = "titles/devicecomponents/form";

    private final DeviceComponentTitleService deviceComponentTitleService;
    private final DeviceTitleService deviceTitleService;

    @GetMapping
    public String displayDeviceComponentTitles(Model model) {
        model.addAttribute("deviceComponentTitles", deviceComponentTitleService.getAll());
        return "titles/devicecomponents/list";
    }

    @GetMapping("/{id}")
    public String displayDeviceComponentTitleForm(@PathVariable Long id, Model model) {
        model.addAttribute("deviceComponentTitle", deviceComponentTitleService.getById(id).orElse(new DeviceComponentTitle()));
        return DEVICE_COMPONENT_TITLE_FORM;
    }

    @PostMapping("/edit")
    public String editDeviceComponentTitle(@Valid @ModelAttribute("deviceComponentTitle") DeviceComponentTitle deviceComponentTitle, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("titleCreationError", "BindingResult error!");
            return DEVICE_COMPONENT_TITLE_FORM;
        }
        DeviceComponentTitle existing = deviceComponentTitleService.getByTitle(deviceComponentTitle.getTitle());
        if (existing != null && !existing.getId().equals(deviceComponentTitle.getId())) {
            model.addAttribute("deviceComponentTitle", deviceComponentTitle);
            model.addAttribute("titleCreationError", "Такая СЧ в приборе уже существует");
            return DEVICE_COMPONENT_TITLE_FORM;
        }
        //TODO заглушка потом убрать
        deviceComponentTitle.setDeviceTitle(deviceTitleService.getById(1L).orElse(null));
        deviceComponentTitleService.save(deviceComponentTitle);
        return "redirect:/titles/devicecomponents";
    }
}
