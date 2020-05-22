package com.kropotov.asrd.services;


import com.kropotov.asrd.entities.SystemUser;
import com.kropotov.asrd.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;


public interface UserService extends UserDetailsService {
    User findByUserName(String userName);
    void save(SystemUser systemUser);
    Optional<User> getById(Long id);
    boolean isManagerRole();
    List<User> getAll();
    void delete(Long id);
}
