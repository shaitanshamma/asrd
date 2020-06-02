package com.kropotov.asrd.repositories.titles;

import com.kropotov.asrd.entities.titles.DeviceTitle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceTitleRepository extends CrudRepository<DeviceTitle, Long> {
    DeviceTitle findByTitle(String title);
    List<DeviceTitle> findAll();
}
