package com.kropotov.asrd.repositories;

import com.kropotov.asrd.entities.docs.File;

import org.springframework.data.repository.CrudRepository;

public interface FileRepository extends CrudRepository<File, Long> {
}
