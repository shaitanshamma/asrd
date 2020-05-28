package com.kropotov.asrd.services.springdatajpa.items;

import com.kropotov.asrd.converters.items.ControlSystemToDto;
import com.kropotov.asrd.dto.items.ControlSystemDto;
import com.kropotov.asrd.entities.items.ControlSystem;
import com.kropotov.asrd.entities.titles.SystemTitle;
import com.kropotov.asrd.repositories.SystemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class SystemService {

    private final SystemRepository systemRepository;
    private final ControlSystemToDto controlSystemToDto;

    public List<ControlSystem> getAll() {
        log.info("Get all systems");
        return (List<ControlSystem>) (systemRepository.findAll());
    }

    public Page<ControlSystem> getAll(Pageable pageable) {
        return systemRepository.findAll(pageable);
    }

    public Optional<ControlSystem> getById(Long id) {
        return id == null ? Optional.empty() : systemRepository.findById(id);
    }

    public ControlSystem save(ControlSystem system) {
        return systemRepository.save(system);
    }

    public ControlSystem getByNumberAndTitle(String number, SystemTitle title) {
        return systemRepository.findByNumberAndTitle(number, title);
    }

    @Transactional
    public ControlSystemDto getDtoById(Long id) {
        return controlSystemToDto.convert(getById(id).orElse(new ControlSystem()));
    }

    @Override
    public void delete(Long id) {
        systemRepository.deleteById(id);
    }
}
