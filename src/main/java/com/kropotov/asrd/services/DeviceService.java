package com.kropotov.asrd.services;

import com.kropotov.asrd.entities.Device;
import com.kropotov.asrd.entities.DeviceTitle;
import com.kropotov.asrd.repositories.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService {
    private DeviceRepository deviceRepository;

    @Autowired
    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public List<Device> getAll() {
        return (List<Device>)(deviceRepository.findAll());
    }

    public Device getById(Long id) {
        return deviceRepository.findById(id).orElse(null);
    }

    public Device saveOrUpdate(Device device) {
        return deviceRepository.save(device);
    }

    public Device getByNumberAndTitle(String number, DeviceTitle title) {
        return deviceRepository.findByNumberAndTitle(number, title);
    }
}
