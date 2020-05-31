package com.kropotov.asrd.controllers;

import com.kropotov.asrd.controllers.util.PageValues;
import com.kropotov.asrd.controllers.util.PageWrapper;
import com.kropotov.asrd.converters.UserToSimple;
import com.kropotov.asrd.converters.items.ControlSystemToDto;
import com.kropotov.asrd.converters.items.DtoToControlSystem;
import com.kropotov.asrd.dto.items.ControlSystemDto;
import com.kropotov.asrd.entities.items.ControlSystem;
import com.kropotov.asrd.services.UserService;
import com.kropotov.asrd.services.springdatajpa.items.SystemService;
import com.kropotov.asrd.services.springdatajpa.titles.SystemTitleService;
import com.kropotov.asrd.services.springdatajpa.titles.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@Controller
@RequestMapping("/systems")
@RequiredArgsConstructor
public class SystemController {

    private final SystemService systemService;
    private final UserService userService;
    private final SystemTitleService systemTitleService;
    private final DtoToControlSystem dtoToControlSystem;
    private final ControlSystemToDto controlSystemToDto;
    private final TopicService topicService;
    private final UserToSimple userToSimple;

    @GetMapping
    public String displaySystems(Model model, Pageable pageable) {
        pageable = PageValues.getPageableOrDefault(pageable);
        PageWrapper<ControlSystem> page = new PageWrapper<>(systemService.getAll(pageable.previousOrFirst()), "/systems");

        PageValues.addContentToModel(model, page);
        model.addAttribute("topicTitleList", topicService.getAll().get());

        return "systems/list-systems";
    }

    @GetMapping("/{id}/show")
    public String displayById(@PathVariable Long id, Model model) {
        if (systemService.getById(id).isPresent()) {
            model.addAttribute("system", systemService.getDtoById(id));
            return "systems/show";
        } else {
            return "redirect:/systems";
        }
    }

    @GetMapping("/{id}/update")
    public String displaySystemForm(@PathVariable("id") Long id,
                                    @RequestParam(value = "systemTitle", required = false) Long systemTitleId,
                                    Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        ControlSystemDto systemDto;

        if (systemTitleId != null) {
            ControlSystem system = new ControlSystem();
            system.setTitle(systemTitleService.getById(systemTitleId).get());
            systemDto = controlSystemToDto.convert(system);
        } else {
            systemDto = systemService.getDtoById(id);
        }
        model.addAttribute("system", systemDto);

        return "systems/edit-system";
    }

    @PostMapping("/edit")
    public String editSystem(@ModelAttribute("system") ControlSystemDto systemDto, BindingResult bindingResult, Model model,
                             Principal principal) {
        if (principal == null) {
            model.addAttribute("system", systemDto);
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

        systemDto.setUser(userToSimple.convert(userService.findByUserName(principal.getName())));
        ControlSystem detachedSystem = dtoToControlSystem.convert(systemDto);
        systemService.save(detachedSystem);

        return "redirect:/systems";
    }

}
