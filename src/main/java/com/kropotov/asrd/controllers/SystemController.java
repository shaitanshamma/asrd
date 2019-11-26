package com.kropotov.asrd.controllers;

import com.kropotov.asrd.entities.ControlSystem;
import com.kropotov.asrd.entities.SystemTitle;
import com.kropotov.asrd.services.SystemService;
import com.kropotov.asrd.services.SystemTitleService;
import com.kropotov.asrd.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/system")
public class SystemController {
    private SystemService systemService;
    private UserService userService;
    private SystemTitleService systemTitleService;

    @Autowired
    public void setSystemService(SystemService systemService) {
        this.systemService = systemService;
    }
    @Autowired
    public void setSystemTitleService(SystemTitleService systemTitleService) {
        this.systemTitleService = systemTitleService;
    }
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public String systemPage(Model model) {
        List<ControlSystem> systems = systemService.getAllControlSystems();
        model.addAttribute("systems", systems);
        return "system-page";
    }

    @GetMapping("/edit/{id}")
    public String addSystemPage(Model model, @PathVariable("id") Long id) {
        ControlSystem system = systemService.findById(id);
        if (system == null) {
            system = new ControlSystem();
            system.setUser(userService.findByUserName("amdin"));
        }
        model.addAttribute("system", system);
        model.addAttribute("titleSystems", systemTitleService.getAllTitles());
         return "edit-system";
    }

    @PostMapping("/edit")
    public String addSystem(@Valid @ModelAttribute("system") ControlSystem system, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("systemCreationError", "BindingResult error!");
            return "edit-system";
        }
        ControlSystem existing = systemService.findByTitle(system.getTitle());
        if (existing != null && !system.getId().equals(existing.getId())) {
            model.addAttribute("system", system);
            model.addAttribute("systemCreationError", "System title already exists");
            return "edit-system";
        }
        systemService.saveOrUpdate(system);
        return "redirect:/system/all";
    }

}
