package com.kropotov.asrd.controllers;


import com.kropotov.asrd.entities.*;

import com.kropotov.asrd.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/devices")
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceService deviceService;
    private final DeviceTitleService deviceTitleService;
    private final UserService userService;
    private final TopicService topicService;
    private final DeviceComponentService deviceComponentService;
    private final SystemService systemService;
    private final DeviceComponentTitleService deviceComponentTitleService;

    @GetMapping
    public String displayDevices(Model model) {
        List<Device> devices = deviceService.getAll();
        model.addAttribute("topicTitleList", topicService.getAll());
        model.addAttribute("device", new Device());
        model.addAttribute("devices", devices);
        return "devices/list-devices";
    }
    // TODO нужен рефакторинг
    @GetMapping("/{id}")
    public String displayDeviceForm(Model model, @PathVariable("id") Long id,
                                    @RequestParam(value = "deviceTitle", required = false) Long deviceTitleId) throws Exception {
        Device device = deviceService.getById(id);

        if (device == null) {
            device = new Device();
            if (deviceTitleId != null) {
                device.setTitle(deviceTitleService.getById(deviceTitleId));
            } else {
                // TODO - кинуть сообщение об ошибке (убрать заглушку)
                throw new Exception();
            }
        }
        List<DeviceComponent> components = new ArrayList<>();
        for (DeviceComponentTitle newTitle : device.getTitle().getComponentTitles()) {
            components.add(new DeviceComponent(newTitle, ""));
        }
        if (device.getComponents() == null || device.getComponents().size() == 0) {
            device.setComponents(components);
        } else if (components.size() != device.getComponents().size()) {
            // TODO добавить проверку на тот случай если по каким-то причинам список айдишников имен компонентов не совпадает с новым списком имен айдишников компонентов
            Set<DeviceComponent> set = new HashSet<>(device.getComponents());
            set.addAll(components);
            device.setComponents(new ArrayList<>(set));
        }

        // TODO подумать как сделать динамическое формирование состава компонентов прибора

        if (device.getVintage() != null) {
            model.addAttribute("strVintage", device.getVintage().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        }
        if (device.getOtkDate() != null) {
            model.addAttribute("strOtkDate", device.getOtkDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        }
        if (device.getVpDate() != null) {
            model.addAttribute("strVpDate", device.getVpDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        }

        List<ControlSystem> systems = new ArrayList<>();
        for (SystemTitle s : device.getTitle().getSystemTitles()) {
            systems.addAll(s.getSystems());
        }

        model.addAttribute("systems", systems);
        model.addAttribute("device", device);

        return "devices/edit-device";
    }

    // TODO нужен рефакторинг
    @PostMapping("/edit")
    public String editDevice(@ModelAttribute("device") Device device, BindingResult bindingResult, Model model,
                             @RequestParam(value = "strVintage", required = false) String strVintage,
                             @RequestParam(value = "strOtkDate", required = false) String strOtkDate,
                             @RequestParam(value = "strVpDate", required = false) String strVpDate,
                             Principal principal) {
        if (principal == null) {
            model.addAttribute("device", device);
            model.addAttribute("deviceCreationError", "Необходима авторизация");
            return "devices/edit-device";
        }
        User user = userService.findByUserName(principal.getName());
        /*if (bindingResult.hasErrors()) {
            model.addAttribute("deviceCreationError", "BindingResult error!");
            return "devices/edit-device";
        }*/
        /*Device existing = deviceService.getByTitle(device.getTitle());
        if (existing != null && !device.getId().equals(existing.getId())) {
            model.addAttribute("device", device);
            model.addAttribute("deviceCreationError", "Прибор стакими именем уже сузществует");
            return "devices/edit-deviceя";
        }*/


        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        try {
            if (!strVintage.equals("")) {
                device.setVintage(LocalDate.parse(strVintage, dateFormatter));
            }
        } catch (DateTimeParseException e) {
            // TODO
            model.addAttribute("deviceTitles", deviceTitleService.getAll());
            model.addAttribute("deviceCreationError", "Неверный формат даты");
            return "devices/edit-device";
        }
        try {
            if (!strOtkDate.equals("")) {
                device.setOtkDate(LocalDate.parse(strOtkDate, dateFormatter));
            }
        } catch (DateTimeParseException e) {
            // TODO
            model.addAttribute("deviceTitles", deviceTitleService.getAll());
            model.addAttribute("deviceCreationError", "Неверный формат даты");
            return "devices/edit-device";
        }
        try {
            if (!strVpDate.equals("")) {
                device.setVpDate(LocalDate.parse(strVpDate, dateFormatter));
            }
        } catch (DateTimeParseException e) {
            // TODO
            model.addAttribute("deviceTitles", deviceTitleService.getAll());
            model.addAttribute("deviceCreationError", "Неверный формат даты");
            return "devices/edit-device";
        }

        device.setUser(userService.findByUserName(principal.getName()));

        device = deviceService.saveOrUpdate(device);
        for (DeviceComponent d : device.getComponents()) {
            d.setTitle(deviceComponentTitleService.getById(d.getTitle().getId()));
            d.setUser(user);
            d.setDevice(device);
        }
        deviceComponentService.saveAll(device.getComponents());
        return "redirect:/devices";
    }
}
