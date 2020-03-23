package com.kropotov.asrd.repositories;

import com.kropotov.asrd.entities.DeviceComponent;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DeviceComponentRepository extends CrudRepository<DeviceComponent, Long> {

//    List<DeviceComponent> saveAll(Iterable<DeviceComponent> deviceComponents);
}
