package com.kropotov.asrd.controllers;

import com.kropotov.asrd.dto.EmailConfirmPassword;
import com.kropotov.asrd.dto.SystemUser;
import com.kropotov.asrd.services.EmailConfirmation;
import com.kropotov.asrd.services.UserService;
import com.kropotov.asrd.services.email.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {
    private final UserService userService;
    private final EmailService emailService;
    private final EmailConfirmation emailConfirmation;

    @GetMapping("/login")
    public String showMyLoginPage(Model model) {
        model.addAttribute("emailConfirmPassword", new EmailConfirmPassword());
        return "login";
    }

    @GetMapping("/login/new")
    public String showMyLoginPageNew() {
        emailConfirmation.removeAll();
        return "redirect:/login";
    }

    @PostMapping("/emailConfirm")
    public String confirmEmailUser(@ModelAttribute("emailConfirmPassword") EmailConfirmPassword emailConfirmPassword,
                                   Model theModel,
                                   BindingResult theBindingResult)
            throws NoSuchProviderException, NoSuchAlgorithmException {
        SystemUser systemUser = new SystemUser();
        if(theBindingResult.hasErrors()) {
            theModel.addAttribute("emailConfirmPassword", emailConfirmPassword);
            return "redirect:/login";
        }
        else if (!emailConfirmation.isEmpty()) {
            if (emailConfirmation.findByPassAndEmail(emailConfirmPassword.getPasswordConfirm(), emailConfirmPassword.getEmailConfirm())) {
                systemUser.setEmail(emailConfirmPassword.getEmailConfirm());
                theModel.addAttribute("systemUser", systemUser);
                return "registration-form";
            }
            else {
                theModel.addAttribute("msgSend", true);
                theModel.addAttribute("codeBad", true);
                theModel.addAttribute("systemUser", systemUser);
                log.info("email = {}, confirmPassword = {}", emailConfirmPassword.getEmailConfirm(), emailConfirmPassword.getPasswordConfirm());
                return "/login";
            }
        } else {
            Integer passGen = userService.randomNumberGenerator();
            emailConfirmation.addValuePassAndEmail(passGen, emailConfirmPassword.getEmailConfirm());
            emailService.sendMessage(userService.createEmailMessage(emailConfirmPassword.getEmailConfirm(), passGen));
            log.info("Massage sending. Password {}", passGen);
            log.info("email = {}, confirmPassword = {}", emailConfirmPassword.getEmailConfirm(), emailConfirmPassword.getPasswordConfirm());
            theModel.addAttribute("emailConfirmPassword", emailConfirmPassword);
            theModel.addAttribute("msgSend", true);
            return "/login";
        }
    }
}
