package com.kropotov.asrd.repositories.titles;

import com.kropotov.asrd.entities.titles.SystemTitle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemTitleRepository extends CrudRepository<SystemTitle, Long> {
    Iterable<SystemTitle> findAllByTopicsId(Long id);
    SystemTitle findByTitle(String title);
}
