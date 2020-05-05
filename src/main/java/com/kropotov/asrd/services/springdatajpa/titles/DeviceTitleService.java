package com.kropotov.asrd.services.springdatajpa.titles;

import com.kropotov.asrd.converters.titles.DeviceTitleToDto;
import com.kropotov.asrd.entities.titles.DeviceTitle;
import com.kropotov.asrd.repositories.titles.DeviceTitleRepository;
import com.kropotov.asrd.services.TitleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeviceTitleService implements TitleService<DeviceTitle, Long> {

    private final DeviceTitleRepository deviceTitleRepository;
    private final DeviceTitleToDto deviceTitleToDto;

    @Override
    public List<DeviceTitle> getAll() {
        List<DeviceTitle> list = new ArrayList<>();
        deviceTitleRepository.findAll().forEach(list::add);
        return list;
    }

    @Override
    public Optional<DeviceTitle> getById(Long id) {
        return deviceTitleRepository.findById(id);
    }

    @Override
    @Transactional
    public DeviceTitle save(DeviceTitle object) {
        return deviceTitleRepository.save(object);
    }

    @Override
    public DeviceTitle getByTitle(String title) {
        return deviceTitleRepository.findByTitle(title);
    }

}
