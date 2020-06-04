package com.kropotov.asrd.services.springdatajpa.items;

import com.kropotov.asrd.entities.items.SystemComponent;
import com.kropotov.asrd.entities.titles.SystemComponentTitle;
import com.kropotov.asrd.repositories.SystemComponentRepository;
import com.kropotov.asrd.services.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SystemComponentService implements CrudService<SystemComponent, Long> {

    private final SystemComponentRepository systemComponentRepository;


    @Override
    public Optional<List<SystemComponent>> getAll() {
        return Optional.ofNullable(systemComponentRepository.findAll());
    }

    @Override
    public Optional<SystemComponent> getById(Long id) {
        return id == null ? Optional.empty() : Optional.empty();
    }

    @Override
    @Transactional
    public SystemComponent save(SystemComponent object) {
        return null;
    }

    public SystemComponent getByNumberAndTitle(String number, SystemComponentTitle title) {
        return systemComponentRepository.findByNumberAndTitle(number, title);
    }

    @Override
    public void deleteById(Long id) {
        systemComponentRepository.deleteById(id);
    }
}
