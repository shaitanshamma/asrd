package com.kropotov.asrd.services;

import com.kropotov.asrd.entities.ControlSystem;
import com.kropotov.asrd.entities.SystemTitle;
import com.kropotov.asrd.repositories.SystemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SystemService {

    private SystemRepository systemRepository;

    @Autowired
    public SystemService(SystemRepository systemRepository) {
        this.systemRepository = systemRepository;
    }

    public List<ControlSystem> getAll() {
        log.info("Get all systems");
        return (List<ControlSystem>)(systemRepository.findAll());
    }

    public ControlSystem getById(Long id) {
        return systemRepository.findById(id).orElse(null);
    }

    public ControlSystem saveOrUpdate(ControlSystem system) {
        return systemRepository.save(system);
    }

    public ControlSystem getByNumberAndTitle(String number, SystemTitle title) {
        return systemRepository.findByNumberAndTitle(number, title);
    }
}
