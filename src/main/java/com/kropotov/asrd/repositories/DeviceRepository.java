package com.kropotov.asrd.repositories;

import com.kropotov.asrd.entities.Device;
import com.kropotov.asrd.entities.DeviceTitle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends CrudRepository<Device, Long> {
    Device findByNumberAndTitle(String number, DeviceTitle title);
}
