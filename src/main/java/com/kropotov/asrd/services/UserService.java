package com.kropotov.asrd.services;


import com.kropotov.asrd.entities.SystemUser;
import com.kropotov.asrd.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService extends UserDetailsService {
    User findByUserName(String userName);
    void save(SystemUser systemUser);
}
