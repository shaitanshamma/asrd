package com.kropotov.asrd.services;

import com.kropotov.asrd.entities.titles.Topic;
import com.kropotov.asrd.repositories.titles.TopicRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TopicService {

    private final TopicRepository topicRepository;

    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public Iterable<Topic> getAll() {

        return topicRepository.findAll();
    }

    public Optional<Topic> getById(Long id) {
        return topicRepository.findById(id);
    }
}
