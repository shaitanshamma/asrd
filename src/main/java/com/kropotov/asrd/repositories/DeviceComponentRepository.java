package com.kropotov.asrd.repositories;

import com.kropotov.asrd.entities.items.DeviceComponent;
import com.kropotov.asrd.entities.titles.DeviceComponentTitle;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DeviceComponentRepository extends CrudRepository<DeviceComponent, Long> {

    List<DeviceComponent> findAll();
//    List<DeviceComponent> saveAll(Iterable<DeviceComponent> deviceComponents);
DeviceComponent findByNumberAndTitle(String number,  DeviceComponentTitle title);
}
