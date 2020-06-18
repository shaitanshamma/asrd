package com.kropotov.asrd.services.springdatajpa.security;

import com.kropotov.asrd.dto.SystemUser;
import com.kropotov.asrd.entities.Role;
import com.kropotov.asrd.entities.User;
import com.kropotov.asrd.repositories.RoleRepository;
import com.kropotov.asrd.repositories.StatusUserRepository;
import com.kropotov.asrd.repositories.UserRepository;
import com.kropotov.asrd.services.UserService;
import com.kropotov.asrd.services.email.EmailMessage;
import com.kropotov.asrd.services.email.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private final StatusUserRepository statusUserRepository;
    private final EmailService emailService;


    @Value("${algorithm}")
    private String algorithm;
    @Value("${provider}")
    private String provider;
    @Value("${bound}")
    private int bound;
    @Value("${correctiveValue}")
    private int correctiveValue;

    // TODO Алексей Токарев Тут возникает циклическая зависимость без сеттера.
    // Лучше вынести преобразования к dto в отдельный конвертер чтобы не было циклических зависимостей
    @Autowired
    public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findByUserName(String userName) {
        return userRepository.findOneByUserName(userName);
    }


    @Override
    @Transactional
    public User saveDto(SystemUser systemUser) {
        User user = new User();
        user.setId(systemUser.getId());
        user.setUserName(systemUser.getUserName());
        user.setPassword(passwordEncoder.encode(systemUser.getPassword()));
        user.setFirstName(systemUser.getFirstName());
        user.setLastName(systemUser.getLastName());
        user.setPatronymic(systemUser.getPatronymic());
        user.setEmail(systemUser.getEmail());
        if (systemUser.getRoles() == null) {
            user.setRoles(Collections.singletonList(roleRepository.findOneByName("ROLE_USER")));
        } else user.setRoles(systemUser.getRoles());
        user.setWorkPhone(systemUser.getWorkPhone());
        user.setMobilePhone(systemUser.getMobilePhone());
       if (systemUser.getStatusUser() == null) {
           user.setStatusUser(statusUserRepository.findOneByName("confirmed"));
       }  else user.setStatusUser(systemUser.getStatusUser());
        userRepository.save(user);
        return user;
    }

    @Override
    public Optional<List<User>> getAll() {
        return Optional.ofNullable(userRepository.findAll());
    }

    @Override
    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }

    // TODO Алексей Токарев
    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws AuthenticationException {
        User user = userRepository.findOneByUserName(userName);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        else if(user.getStatusUser().getName().equals("confirmed")) {
            throw new BadCredentialsException("User \"" + user.getUserName() + "\"  confirmed");
        }
        else if(user.getStatusUser().getName().equals("inactive")) {
            throw new BadCredentialsException("User \"" + user.getUserName() + "\"  not activated");
        }
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public long allNewUsersConfirmedEmail() {
        return new ArrayList<>(userRepository.findAll())
                .stream().filter((u) -> u.getStatusUser().getName().contains("confirmed")).count();
    }

    @Override
    public void activateUser(Long id) {
        User user = userRepository.findById(id).orElse(new User());
        if (user.getId() != null) {
            user.setStatusUser(statusUserRepository.findOneByName("active"));
            userRepository.save(user);
        }
    }

    @Override
    public EmailMessage createEmailMessage(String email, Integer confirmingPassword) {
        Set<String> emailList = new HashSet<>();
        emailList.add(email);
        String emailSubject = "Регистрация в АСУП";
        EmailMessage emailMessage = EmailMessage.builder().mailSubject(emailSubject).build();
        emailMessage.setMessageContent("Ваш пароль для подтверждения почты: " + confirmingPassword);
        emailMessage.setMessageFrom("m");
        emailMessage.setRecipients(emailList);
        return emailMessage;
    }

    @Override
    public int randomNumberGenerator() throws NoSuchProviderException, NoSuchAlgorithmException {
        return SecureRandom.getInstance(algorithm, provider).nextInt(bound) + correctiveValue;
    }
}
