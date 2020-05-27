package com.kropotov.asrd.repositories;

import com.kropotov.asrd.entities.items.Device;
import com.kropotov.asrd.entities.titles.DeviceTitle;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends PagingAndSortingRepository<Device, Long> {
    Device findByNumberAndTitle(String number, DeviceTitle title);
}
