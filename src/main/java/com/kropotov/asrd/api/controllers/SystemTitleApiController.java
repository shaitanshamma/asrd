package com.kropotov.asrd.api.controllers;

import com.kropotov.asrd.entities.titles.SystemTitle;
import com.kropotov.asrd.entities.titles.Topic;
import com.kropotov.asrd.services.SystemTitleService;
import com.kropotov.asrd.services.TopicService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/systems/titles")
//@RequestMapping("/invoices/titles")
public class SystemTitleApiController {

    private final SystemTitleService systemTitleService;
    private final TopicService topicService;

    public SystemTitleApiController(SystemTitleService systemTitleService, TopicService topicService) {
        this.systemTitleService = systemTitleService;
        this.topicService = topicService;
    }

    /*@GetMapping
    public List<SystemTitle> getSystemTitles() {
        return systemTitleService.getAll();
    }*/

    @GetMapping
    public List<SystemTitle> getSystemTitlesByTopic(@RequestParam(value = "topicId", required = false) Long topicId) {
        if (topicId == null) {
            return systemTitleService.getAll();
        }
        Topic topic = topicService.getById(topicId).orElse(new Topic());
        return topic.getSystemTitles();
    }
}
