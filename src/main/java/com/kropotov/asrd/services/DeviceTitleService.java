package com.kropotov.asrd.services;

import com.kropotov.asrd.entities.DeviceTitle;
import com.kropotov.asrd.repositories.titles.DeviceTitleRepository;
import org.springframework.stereotype.Service;

@Service
public class DeviceTitleService {

    private final DeviceTitleRepository deviceTitleRepository;

    public DeviceTitleService(DeviceTitleRepository deviceTitleRepository) {
        this.deviceTitleRepository = deviceTitleRepository;
    }

    public Iterable<DeviceTitle> getAll() {
        return deviceTitleRepository.findAll();
    }
}
