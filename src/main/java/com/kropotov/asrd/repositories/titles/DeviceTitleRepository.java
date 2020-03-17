package com.kropotov.asrd.repositories.titles;

import com.kropotov.asrd.entities.DeviceTitle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceTitleRepository extends CrudRepository<DeviceTitle, Long> {
}
