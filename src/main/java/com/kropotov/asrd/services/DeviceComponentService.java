package com.kropotov.asrd.services;

import com.kropotov.asrd.entities.items.DeviceComponent;
import com.kropotov.asrd.repositories.DeviceComponentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeviceComponentService {

    private final DeviceComponentRepository deviceComponentRepository;

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

}
