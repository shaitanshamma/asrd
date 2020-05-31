package com.kropotov.asrd.repositories.titles;

import com.kropotov.asrd.entities.titles.SystemTitle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SystemTitleRepository extends CrudRepository<SystemTitle, Long> {
    Iterable<SystemTitle> findAllByTopicsId(Long id);
    SystemTitle findByTitle(String title);
    List<SystemTitle> findAll();
}
