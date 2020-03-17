package com.kropotov.asrd.services;

import com.kropotov.asrd.entities.SystemTitle;
import com.kropotov.asrd.entities.Topic;
import com.kropotov.asrd.repositories.titles.SystemTitleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SystemTitleService {
    private final SystemTitleRepository systemTitleRepository;

    public SystemTitleService(SystemTitleRepository systemTitleRepository) {
        this.systemTitleRepository = systemTitleRepository;
    }

    public List<SystemTitle> getAll(){
        return (List<SystemTitle>) systemTitleRepository.findAll();
    }

    public List<SystemTitle> getAllByTopic(Topic topic){
        ArrayList<Topic> topicList = new ArrayList<>();
        topicList.add(topic);
        return (List<SystemTitle>) systemTitleRepository.findAllByTopicsId(topic.getId());
    }

    public SystemTitle getById(Long id) {
        return systemTitleRepository.findById(id).get();
    }
}
