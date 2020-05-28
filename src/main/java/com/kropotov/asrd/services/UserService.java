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
    List<User> getAll();
    void deleteById(Long id);
    long allNewUsersConfirmedEmail();
    void activateUser(Long id);
}
