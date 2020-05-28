package com.kropotov.asrd.repositories;

import com.kropotov.asrd.entities.StatusUser;
import org.springframework.data.repository.CrudRepository;

public interface StatusUserRepository extends CrudRepository<StatusUser, Long> {
    StatusUser findOneByName(String name);
}
