package com.kropotov.asrd.controllers;

import com.kropotov.asrd.entities.ControlSystem;
import com.kropotov.asrd.services.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class SystemControllerTest {

    private final SystemService systemService;

    @Autowired
    public SystemControllerTest(SystemService systemService) {
        this.systemService = systemService;
    }


    @RequestMapping(value = "/systemTest/all", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public List<ControlSystem> getAllSystems() {
        return systemService.getAllControlSystems();
    }
}
