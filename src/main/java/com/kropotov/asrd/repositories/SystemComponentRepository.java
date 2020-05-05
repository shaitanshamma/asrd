package com.kropotov.asrd.repositories;

import com.kropotov.asrd.entities.items.SystemComponent;
import com.kropotov.asrd.entities.titles.SystemComponentTitle;
import org.springframework.data.repository.CrudRepository;

public interface SystemComponentRepository extends CrudRepository<SystemComponent, Long> {
    SystemComponent findByNumberAndTitle(String number,  SystemComponentTitle title);
}
