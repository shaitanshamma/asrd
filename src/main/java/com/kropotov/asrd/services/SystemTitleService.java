package com.kropotov.asrd.services;

import com.kropotov.asrd.entities.SystemTitle;
import com.kropotov.asrd.repositories.SystemTitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemTitleService {
    private SystemTitleRepository systemTitleRepository;

    @Autowired
    public void setSystemTitleRepository(SystemTitleRepository systemTitleRepository) {
        this.systemTitleRepository = systemTitleRepository;
    }

    public List<SystemTitle> getAll(){
        return (List<SystemTitle>) systemTitleRepository.findAll();
    }
}
