package com.kropotov.asrd.controllers.titles;

import com.kropotov.asrd.entities.titles.Topic;
import com.kropotov.asrd.services.springdatajpa.titles.SystemTitleService;
import com.kropotov.asrd.services.springdatajpa.titles.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/titles/topics")
@Controller
@RequiredArgsConstructor
public class TopicController {

    private static final String TOPIC_FORM = "titles/topics/form";

    private final TopicService topicService;
    private final SystemTitleService systemTitleService;

    @GetMapping
    public String displayDevices(Model model) {
        model.addAttribute("topics", topicService.getAll());
        return "titles/topics/list";
    }

    @GetMapping("/{id}")
    public String displayTopicForm(@PathVariable Long id, Model model) {
        model.addAttribute("topic", topicService.getById(id).orElse(new Topic()));
        model.addAttribute("allSystemTitles", systemTitleService.getAll());
        return TOPIC_FORM;
    }

    @PostMapping("/edit")
    public String editTopic(@Valid @ModelAttribute("topic") Topic topic, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("topicCreationError", "BindingResult error!");
            return TOPIC_FORM;
        }
        Topic existing = topicService.getByTitle(topic.getTitle());
        if (existing != null && !existing.getId().equals(topic.getId())) {
            model.addAttribute("topic", topic);
            model.addAttribute("topicCreationError", "Такая тема уже существует");
            return TOPIC_FORM;
        }
        topicService.save(topic);
        return "redirect:/titles/topics";
    }
}
