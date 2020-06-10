package com.kropotov.asrd.services.springdatajpa.titles;

import com.kropotov.asrd.entities.titles.DeviceComponentTitle;
import com.kropotov.asrd.repositories.titles.DeviceComponentTitleRepository;
import com.kropotov.asrd.services.TitleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeviceComponentTitleService implements TitleService<DeviceComponentTitle, Long> {

    private final DeviceComponentTitleRepository deviceComponentTitleRepository;


    @Override
    public Optional<List<DeviceComponentTitle>> getAll() {
        return Optional.ofNullable(deviceComponentTitleRepository.findAll());
    }

    @Override
    public Optional<DeviceComponentTitle> getById(Long id) {
        return deviceComponentTitleRepository.findById(id);
    }

    @Override
    public DeviceComponentTitle save(DeviceComponentTitle object) {
        return deviceComponentTitleRepository.save(object);
    }

    @Override
    public DeviceComponentTitle getByTitle(String title) {
        return deviceComponentTitleRepository.findByTitle(title);
    }

    @Override
    public void deleteById(Long id) {
        deviceComponentTitleRepository.deleteById(id);
    }
}
