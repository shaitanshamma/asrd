package com.kropotov.asrd.services.springdatajpa.titles;

import com.kropotov.asrd.entities.titles.SystemComponentTitle;
import com.kropotov.asrd.repositories.titles.SystemComponentTitleRepository;
import com.kropotov.asrd.services.TitleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SystemComponentTitleService implements TitleService<SystemComponentTitle, Long> {

    private final SystemComponentTitleRepository systemComponentTitleRepository;

    @Override
    public SystemComponentTitle getByTitle(String title) {
        return systemComponentTitleRepository.findByTitle(title);
    }


    @Override
    public Optional<List<SystemComponentTitle>> getAll() {
        return Optional.ofNullable(systemComponentTitleRepository.findAll());
    }

    @Override
    public Optional<SystemComponentTitle> getById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public SystemComponentTitle save(SystemComponentTitle object) {
        return systemComponentTitleRepository.save(object);
    }

    @Override
    public void deleteById(Long id) {
        systemComponentTitleRepository.deleteById(id);
    }
}
