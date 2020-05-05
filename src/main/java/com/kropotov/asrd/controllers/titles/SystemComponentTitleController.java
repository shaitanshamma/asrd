package com.kropotov.asrd.controllers.titles;

import com.kropotov.asrd.entities.titles.SystemComponentTitle;
import com.kropotov.asrd.services.springdatajpa.titles.SystemComponentTitleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/titles/systemcomponents")
@Controller
@RequiredArgsConstructor
public class SystemComponentTitleController {
    private static final String SYSTEM_COMPONENT_TITLE_FORM = "titles/systemcomponents/form";

    private final SystemComponentTitleService systemComponentTitleService;

    @GetMapping
    public String displaySystemComponentTitles(Model model) {
        model.addAttribute("systemComponentTitles", systemComponentTitleService.getAll());
        return "titles/systemcomponents/list";
    }

    @GetMapping("/{id}")
    public String displaySystemComponentTitleForm(@PathVariable Long id, Model model) {
        model.addAttribute("systemComponentTitle", systemComponentTitleService.getById(id).orElse(new SystemComponentTitle()));
        return SYSTEM_COMPONENT_TITLE_FORM;
    }

    @PostMapping("/edit")
    public String editSystemComponentTitle(@Valid @ModelAttribute("systemComponentTitle") SystemComponentTitle systemComponentTitle, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("titleCreationError", "BindingResult error!");
            return SYSTEM_COMPONENT_TITLE_FORM;
        }
        SystemComponentTitle existing = systemComponentTitleService.getByTitle(systemComponentTitle.getTitle());
        if (existing != null && !existing.getId().equals(systemComponentTitle.getId())) {
            model.addAttribute("systemComponentTitle", systemComponentTitle);
            model.addAttribute("titleCreationError", "Такая тема уже существует");
            return SYSTEM_COMPONENT_TITLE_FORM;
        }
        systemComponentTitleService.save(systemComponentTitle);
        return "redirect:/titles/systemcomponents";
    }
}
