package com.kropotov.asrd.services;

import com.kropotov.asrd.dto.SystemUser;
import com.kropotov.asrd.entities.User;
import com.kropotov.asrd.services.email.EmailMessage;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;


public interface UserService extends UserDetailsService, CrudService<User, Long> {
    User findByUserName(String userName);
    User saveDto(SystemUser systemUser) throws NoSuchProviderException, NoSuchAlgorithmException;
    long allNewUsersConfirmedEmail();
    void activateUser(Long id);
    EmailMessage createEmailMessage(String email, Integer confirmingPassword);
    int randomNumberGenerator() throws NoSuchProviderException, NoSuchAlgorithmException;
}
