package com.kropotov.asrd.controllers;

import com.kropotov.asrd.converters.UserToSimple;
import com.kropotov.asrd.converters.docs.DtoToActInputControl;
import com.kropotov.asrd.dto.docs.ActInputControlDto;
import com.kropotov.asrd.entities.docs.ActInputControl;
import com.kropotov.asrd.services.ActInputControlService;
import com.kropotov.asrd.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("acts")
@RequiredArgsConstructor
public class ActInputControlController {
    private final ActInputControlService actService;
    private final UserService userService;
    private final DtoToActInputControl dtoToActInputControl;
    private final UserToSimple userToSimple;

    @GetMapping("/{id}/show")
    public String displayById(@PathVariable Long id, Model model) {
        if (actService.getById(id).isPresent()) {
            model.addAttribute("act", actService.getDtoById(id));
            return "acts/show";
        } else {
            return "redirect:/acts";
        }
    }

    @GetMapping("/{id}/update")
    public String updateAct(@PathVariable Long id, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        model.addAttribute("act", actService.getDtoById(id));
        return "acts/edit-act";
    }

    @GetMapping
    public String displayAll(Model model) {
        model.addAttribute("acts", actService.getAll());
        return "acts/list-acts";
    }

    @PostMapping("/edit")
    public String saveOrUpdate(@ModelAttribute ActInputControlDto actDto, Principal principal) {

        if (principal == null) {
            return "redirect:/login";
        }
        actDto.setPath("plug");
        actDto.setUser(userToSimple.convert(userService.findByUserName(principal.getName())));
        ActInputControl detachedAct = dtoToActInputControl.convert(actDto);
        actService.save(detachedAct);
        return "redirect:/acts";
    }
}
