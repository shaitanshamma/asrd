package com.kropotov.asrd.services;

import com.kropotov.asrd.dto.SystemUser;
import com.kropotov.asrd.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService extends UserDetailsService, CrudService<User, Long> {
    User findByUserName(String userName);
    void saveDto(SystemUser systemUser);
    long allNewUsersConfirmedEmail();
    void activateUser(Long id);
}
