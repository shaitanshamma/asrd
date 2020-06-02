package com.kropotov.asrd.services.springdatajpa.items;

import com.kropotov.asrd.converters.items.DeviceToDto;
import com.kropotov.asrd.dto.items.DeviceDto;
import com.kropotov.asrd.entities.items.Device;
import com.kropotov.asrd.entities.titles.DeviceTitle;
import com.kropotov.asrd.repositories.DeviceRepository;
import com.kropotov.asrd.services.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeviceService implements CrudService<Device, Long> {

    private final DeviceRepository deviceRepository;
    private final DeviceToDto deviceToDto;


    public Page<Device> getAll(Pageable pageable) {
        return deviceRepository.findAll(pageable);
    }

    @Override
    public Optional<List<Device>> getAll() {
        return Optional.ofNullable(deviceRepository.findAll());
    }

    @Override
    public Optional<Device> getById(Long id) {
        return id == null ? Optional.empty() : deviceRepository.findById(id);
    }

    @Override
    @Transactional
    public Device save(Device device) {
        return deviceRepository.save(device);
    }

    public Device getByNumberAndTitle(String number, DeviceTitle title) {
        return deviceRepository.findByNumberAndTitle(number, title);
    }

    @Transactional
    public DeviceDto getDtoById(Long id) {
        return deviceToDto.convert(getById(id).orElse(new Device()));
    }

    @Override
    public void deleteById(Long id) {
        deviceRepository.deleteById(id);
    }
}
