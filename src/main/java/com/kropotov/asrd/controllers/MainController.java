package com.kropotov.asrd.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    // https://getbootstrap.com/docs/4.1/getting-started/introduction/csrf

    @RequestMapping("/")
    public String showHomePage() {
        return "index";
    }
}
