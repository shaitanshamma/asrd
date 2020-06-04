package com.kropotov.asrd.controllers;


import com.kropotov.asrd.dto.SystemUser;
import com.kropotov.asrd.entities.User;
import com.kropotov.asrd.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@Slf4j
@RequestMapping("/register")
public class RegistrationController {
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String displayForm(Model theModel) {
        theModel.addAttribute("systemUser", new SystemUser());
        return "registration-form";
    }

    @PostMapping("/processRegistrationForm")
    public String processRegistrationForm(@Valid @ModelAttribute("systemUser") SystemUser theSystemUser, BindingResult theBindingResult, Model theModel) {
        log.info("Processing registration form for: " + theSystemUser.getUserName());
        if (theBindingResult.hasErrors()) {
            theModel.addAttribute("systemUser", theSystemUser);
            return "registration-form";
        }
        if (userService.findByUserName(theSystemUser.getUserName()) != null) {
            theModel.addAttribute("systemUser", theSystemUser);
            theModel.addAttribute("registrationError", "Такое имя пользователя уже существует!");
            log.info("User name already exists.");
            return "registration-form";
        }
        userService.saveDto(theSystemUser);
        log.info("Successfully created user: " + theSystemUser.getUserName());
        return "registration-confirmation";
    }
}
