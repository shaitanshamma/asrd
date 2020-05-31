package com.kropotov.asrd.repositories.titles;

import com.kropotov.asrd.entities.titles.Topic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends CrudRepository<Topic, Long> {
    Topic findByTitle(String title);
    List<Topic> findAll();
}
