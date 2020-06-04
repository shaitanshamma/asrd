package com.kropotov.asrd.services.springdatajpa.titles;

import com.kropotov.asrd.entities.titles.SystemTitle;
import com.kropotov.asrd.entities.titles.Topic;
import com.kropotov.asrd.repositories.titles.SystemTitleRepository;
import com.kropotov.asrd.services.TitleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SystemTitleService implements TitleService<SystemTitle, Long> {

    private final SystemTitleRepository systemTitleRepository;


    @Override
    public Optional<List<SystemTitle>> getAll() {
        return Optional.ofNullable(systemTitleRepository.findAll());
    }

    @Override
    public Optional<SystemTitle> getById(Long id) {
        return systemTitleRepository.findById(id);
    }

    @Override
    public SystemTitle save(SystemTitle object) {
        return systemTitleRepository.save(object);
    }

    @Override
    public SystemTitle getByTitle(String title) {
        return systemTitleRepository.findByTitle(title);
    }

    public List<SystemTitle> getAllByTopic(Topic topic) {
        ArrayList<Topic> topicList = new ArrayList<>();
        topicList.add(topic);
        return (List<SystemTitle>) systemTitleRepository.findAllByTopicsId(topic.getId());
    }

    @Override
    public void deleteById(Long id) {
        systemTitleRepository.deleteById(id);
    }
}
