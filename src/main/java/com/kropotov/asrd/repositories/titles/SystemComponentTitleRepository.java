package com.kropotov.asrd.repositories.titles;

import com.kropotov.asrd.entities.titles.SystemComponentTitle;
import org.springframework.data.repository.CrudRepository;

public interface SystemComponentTitleRepository extends CrudRepository<SystemComponentTitle, Long> {
    SystemComponentTitle findByTitle(String title);
}
