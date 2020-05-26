package com.kropotov.asrd.repositories;

import com.kropotov.asrd.entities.StatusUser;
import com.kropotov.asrd.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusUserRepository extends JpaRepository<StatusUser, Long> {
    StatusUser findOneByName(String name);
}
