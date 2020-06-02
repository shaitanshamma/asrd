package com.kropotov.asrd.repositories.titles;

import com.kropotov.asrd.entities.titles.DeviceComponentTitle;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DeviceComponentTitleRepository extends CrudRepository<DeviceComponentTitle, Long> {
    DeviceComponentTitle findByTitle(String title);
    List<DeviceComponentTitle> findAll();
}
