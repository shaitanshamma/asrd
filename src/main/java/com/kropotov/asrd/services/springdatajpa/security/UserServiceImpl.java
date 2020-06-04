package com.kropotov.asrd.services.springdatajpa.security;

import com.kropotov.asrd.entities.Role;
import com.kropotov.asrd.dto.SystemUser;
import com.kropotov.asrd.entities.User;
import com.kropotov.asrd.repositories.RoleRepository;
import com.kropotov.asrd.repositories.StatusUserRepository;
import com.kropotov.asrd.repositories.UserRepository;
import com.kropotov.asrd.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private final StatusUserRepository statusUserRepository;

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
    public void saveDto(SystemUser systemUser) {
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
           user.setStatusUser(statusUserRepository.findOneByName("not confirmed"));
       }  else user.setStatusUser(systemUser.getStatusUser());
        userRepository.save(user);
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
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findOneByUserName(userName);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        else if(user.getStatusUser().getName().equals("not confirmed")) {
            throw new UsernameNotFoundException("User not activated");
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

}
