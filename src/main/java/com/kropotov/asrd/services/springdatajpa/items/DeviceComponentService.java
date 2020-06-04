package com.kropotov.asrd.services.springdatajpa.items;

import com.kropotov.asrd.entities.items.DeviceComponent;
import com.kropotov.asrd.entities.titles.DeviceComponentTitle;
import com.kropotov.asrd.repositories.DeviceComponentRepository;
import com.kropotov.asrd.services.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeviceComponentService implements CrudService<DeviceComponent, Long> {

    private final DeviceComponentRepository deviceComponentRepository;


    @Override
    public Optional<List<DeviceComponent>> getAll() {
        return Optional.ofNullable(deviceComponentRepository.findAll());

    }

    @Override
    public Optional<DeviceComponent> getById(Long id) {
        return id == null ? Optional.empty() : deviceComponentRepository.findById(id);
    }

    public DeviceComponent getByNumberAndTitle(String number, DeviceComponentTitle title) {
        return deviceComponentRepository.findByNumberAndTitle(number, title);
    }

    @Override
    public DeviceComponent save(DeviceComponent deviceComponent) {
        return deviceComponentRepository.save(deviceComponent);
    }

    public List<DeviceComponent> saveAll(Iterable<DeviceComponent> deviceComponents) {
        Iterable<DeviceComponent> iterable = deviceComponentRepository.saveAll(deviceComponents);
        List<DeviceComponent> list = new ArrayList<>();

        for (DeviceComponent e : iterable) {
            list.add(e);
        }

        return list;
//        return StreamSupport
//                .stream(deviceComponentRepository.saveAll(deviceComponents).spliterator(), false)
//                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        deviceComponentRepository.deleteById(id);
    }
}
