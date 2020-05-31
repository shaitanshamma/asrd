package com.kropotov.asrd.repositories;

import com.kropotov.asrd.entities.docs.ActInputControl;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ActInputControlRepository extends PagingAndSortingRepository<ActInputControl, Long> {
    List<ActInputControl> findAll();
}
