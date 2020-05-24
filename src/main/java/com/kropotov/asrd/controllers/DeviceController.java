package com.kropotov.asrd.controllers;


import com.kropotov.asrd.controllers.util.PageValues;
import com.kropotov.asrd.controllers.util.PageWrapper;
import com.kropotov.asrd.converters.UserToSimple;
import com.kropotov.asrd.converters.items.DeviceToDto;
import com.kropotov.asrd.converters.items.DtoToDevice;
import com.kropotov.asrd.dto.items.DeviceDto;
import com.kropotov.asrd.entities.items.Device;
import com.kropotov.asrd.services.UserService;
import com.kropotov.asrd.services.springdatajpa.items.DeviceComponentService;
import com.kropotov.asrd.services.springdatajpa.items.DeviceService;
import com.kropotov.asrd.services.springdatajpa.items.SystemService;
import com.kropotov.asrd.services.springdatajpa.titles.DeviceComponentTitleService;
import com.kropotov.asrd.services.springdatajpa.titles.DeviceTitleService;
import com.kropotov.asrd.services.springdatajpa.titles.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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
    private final UserToSimple userToSimple;
    private final DeviceToDto deviceToDto;
    private final DtoToDevice dtoToDevice;

    @GetMapping
    public String displayDevices(Model model, Pageable pageable) {
        pageable = PageValues.getPageableOrDefault(pageable);
        PageWrapper<Device> page = new PageWrapper<>(deviceService.getAll(pageable.previousOrFirst()), "/devices");

        PageValues.addDefaultAttributes(model, page, topicService);
        return "devices/list-devices";
    }

    @GetMapping("/{id}/show")
    public String displayById(@PathVariable Long id, Model model) {
        if (deviceService.getById(id).isPresent()) {
            model.addAttribute("device", deviceService.getDtoById(id));
            return "devices/show";
        } else {
            return "redirect:/devices";
        }
    }

    @GetMapping("/{id}/update")
    public String displayDeviceForm(Model model, Principal principal, @PathVariable("id") Long id,
                                    @RequestParam(value = "deviceTitle", required = false) Long deviceTitleId) throws Exception {
        if (principal == null) {
            return "redirect:/login";
        }

        DeviceDto deviceDto;
        if (deviceTitleId != null) {
            Device device = new Device();
            device.setTitle(deviceTitleService.getById(deviceTitleId).get());
            deviceDto = deviceToDto.convert(device);
        } else {
            deviceDto = deviceService.getDtoById(id);
        }

        model.addAttribute("systemTitles", deviceDto.getDeviceTitle().getSystemTitles());
        model.addAttribute("device", deviceDto);

        return "devices/edit-device";
    }

    // TODO нужен рефакторинг
    @PostMapping("/edit")
    public String editDevice(@ModelAttribute("device") DeviceDto deviceDto, BindingResult bindingResult, Model model,
                             Principal principal) {
        if (principal == null) {
            model.addAttribute("device", deviceDto);
            model.addAttribute("deviceCreationError", "Необходима авторизация");
            return "devices/edit-device";
        }
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

        deviceDto.setUser(userToSimple.convert(userService.findByUserName(principal.getName())));
        deviceService.save(dtoToDevice.convert(deviceDto));
        return "redirect:/devices";
    }
}
