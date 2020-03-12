package com.kropotov.asrd.repositories;

import com.kropotov.asrd.entities.Device;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends CrudRepository<Device, Long> {
}
