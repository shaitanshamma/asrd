package com.kropotov.asrd.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.util.MimeTypeUtils.TEXT_PLAIN_VALUE;

@RestController
public class HealthController {

    // адрес, тип http запроса, тип возвращаемого значения
    @RequestMapping(value = "/isAlive", method = RequestMethod.GET, produces = TEXT_PLAIN_VALUE)
    public String isAlive() {
        return "Hello, I'm alive";
    }
}
