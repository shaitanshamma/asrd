package com.kropotov.asrd.repositories;

import com.kropotov.asrd.entities.items.SystemComponent;
import com.kropotov.asrd.entities.titles.SystemComponentTitle;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SystemComponentRepository extends CrudRepository<SystemComponent, Long> {
    SystemComponent findByNumberAndTitle(String number,  SystemComponentTitle title);
    List<SystemComponent> findAll();
}
