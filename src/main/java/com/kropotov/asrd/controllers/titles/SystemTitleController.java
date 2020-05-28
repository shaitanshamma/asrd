package com.kropotov.asrd.controllers.titles;

import com.kropotov.asrd.entities.titles.SystemTitle;
import com.kropotov.asrd.services.UserService;
import com.kropotov.asrd.services.springdatajpa.titles.SystemTitleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/titles/systems")
@Controller
@RequiredArgsConstructor
public class SystemTitleController {
    private static final String SYSTEM_TITLE_FORM = "titles/systems/form";

    private final SystemTitleService systemTitleService;

    @GetMapping
    public String displayDevices(Model model) {
        model.addAttribute("systemTitles", systemTitleService.getAll());
        return "titles/systems/list";
    }

    @GetMapping("/{id}")
    public String displaySystemTitleForm(@PathVariable Long id, Model model) {
        model.addAttribute("systemTitle", systemTitleService.getById(id).orElse(new SystemTitle()));
        return SYSTEM_TITLE_FORM;
    }

    @PostMapping("/edit")
    public String editSystemTitle(@Valid @ModelAttribute("systemTitle") SystemTitle systemTitle, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("titleCreationError", "BindingResult error!");
            return SYSTEM_TITLE_FORM;
        }
        SystemTitle existing = systemTitleService.getByTitle(systemTitle.getTitle());
        if (existing != null && !existing.getId().equals(systemTitle.getId())) {
            model.addAttribute("systemTitle", systemTitle);
            model.addAttribute("titleCreationError", "Система с таким именем уже существует");
            return SYSTEM_TITLE_FORM;
        }
        systemTitleService.save(systemTitle);
        return "redirect:/titles/systems";
    }
}
