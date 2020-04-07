package com.kropotov.asrd.repositories;

import com.kropotov.asrd.entities.items.DeviceComponent;
import org.springframework.data.repository.CrudRepository;

public interface DeviceComponentRepository extends CrudRepository<DeviceComponent, Long> {

//    List<DeviceComponent> saveAll(Iterable<DeviceComponent> deviceComponents);
}
