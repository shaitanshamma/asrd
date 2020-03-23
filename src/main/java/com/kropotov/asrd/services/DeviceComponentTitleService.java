package com.kropotov.asrd.services;

import com.kropotov.asrd.entities.DeviceComponentTitle;
import com.kropotov.asrd.repositories.titles.DeviceComponentTitleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeviceComponentTitleService {

    private final DeviceComponentTitleRepository deviceComponentTitleRepository;

    public DeviceComponentTitle getById(Long id) {
        return deviceComponentTitleRepository.findById(id).get();
    }

}
