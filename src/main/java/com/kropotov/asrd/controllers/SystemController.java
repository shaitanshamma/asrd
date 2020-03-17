package com.kropotov.asrd.controllers;

import com.kropotov.asrd.entities.ControlSystem;
import com.kropotov.asrd.entities.SystemTitle;
import com.kropotov.asrd.services.SystemService;
import com.kropotov.asrd.services.SystemTitleService;
import com.kropotov.asrd.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;


@Controller
@RequestMapping("/systems")
public class SystemController {

    private final SystemService systemService;
    private final UserService userService;
    private final SystemTitleService systemTitleService;

    public SystemController(SystemService systemService, UserService userService, SystemTitleService systemTitleService) {
        this.systemService = systemService;
        this.userService = userService;
        this.systemTitleService = systemTitleService;
    }

    @GetMapping
    public String displaySystems(Model model) {
        List<ControlSystem> systems = systemService.getAll();
        model.addAttribute("systems", systems);
        return "systems/list-systems";
    }

    @GetMapping("/{id}")
    public String displaySystemForm(Model model, @PathVariable("id") Long id) {
        ControlSystem system = systemService.getById(id);

        if (system == null) {
            system = new ControlSystem();
        }
        if (system.getVintage() != null) {
            model.addAttribute("strVintage", system.getVintage().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        }
        if (system.getOtkDate() != null) {
            model.addAttribute("strOtkDate", system.getOtkDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        }
        if (system.getVpDate() != null) {
            model.addAttribute("strVpDate", system.getVpDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        }

        List<SystemTitle> systemTitles = systemTitleService.getAll();

        model.addAttribute("system", system);
        model.addAttribute("systemTitles", systemTitles);

        return "systems/edit-system";
    }

    @PostMapping("/edit")
    public String editSystem(@ModelAttribute("system") ControlSystem system, BindingResult bindingResult, Model model,
                             @RequestParam("strVintage") String strVintage,
                             @RequestParam("strOtkDate") String strOtkDate,
                             @RequestParam("strVpDate") String strVpDate,
                             Principal principal) {
        if (principal == null) {
            model.addAttribute("systemTitles", systemTitleService.getAll());
            model.addAttribute("systemCreationError", "Необходима авторизация");
            return "systems/edit-system";
        }
        /*if (bindingResult.hasErrors()) {
            model.addAttribute("systemCreationError", "BindingResult error!");
            return "systems/edit-system";
        }*/
        /*ControlSystem existing = systemService.getByTitle(system.getTitle());
        if (existing != null && !system.getId().equals(existing.getId())) {
            model.addAttribute("system", system);
            model.addAttribute("systemCreationError", "System title already exists");
            return "systems/edit-system";
        }*/


        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        try {
            system.setVintage(LocalDate.parse(strVintage, dateFormatter));
        } catch (DateTimeParseException e) {
            // TODO
            model.addAttribute("systemTitles", systemTitleService.getAll());
            model.addAttribute("systemCreationError", "Неверный формат даты");
            return "systems/edit-system";
        }
        try {
            system.setOtkDate(LocalDate.parse(strOtkDate, dateFormatter));
        } catch (DateTimeParseException e) {
            // TODO
            model.addAttribute("systemTitles", systemTitleService.getAll());
            model.addAttribute("systemCreationError", "Неверный формат даты");
            return "systems/edit-system";
        }
        try {
            system.setVpDate(LocalDate.parse(strVpDate, dateFormatter));
        } catch (DateTimeParseException e) {
            // TODO
            model.addAttribute("systemTitles", systemTitleService.getAll());
            model.addAttribute("systemCreationError", "Неверный формат даты");
            return "systems/edit-system";
        }

        system.setUser(userService.findByUserName(principal.getName()));

        systemService.saveOrUpdate(system);
        return "redirect:/systems";
    }

}
