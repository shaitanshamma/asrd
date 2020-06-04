package com.kropotov.asrd.repositories;

import com.kropotov.asrd.entities.StatusUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StatusUserRepository extends JpaRepository<StatusUser, Long> {
    StatusUser findOneByName(String name);
}
