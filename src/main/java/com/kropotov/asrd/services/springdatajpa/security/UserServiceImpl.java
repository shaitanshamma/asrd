package com.kropotov.asrd.services.springdatajpa.security;

import com.kropotov.asrd.entities.Role;
import com.kropotov.asrd.entities.SystemUser;
import com.kropotov.asrd.entities.User;
import com.kropotov.asrd.repositories.RoleRepository;
import com.kropotov.asrd.repositories.StatusUserRepository;
import com.kropotov.asrd.repositories.UserRepository;
import com.kropotov.asrd.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private BCryptPasswordEncoder passwordEncoder;
	private StatusUserRepository statusUserRepository;

	@Autowired
	public void setStatusUserRepository(StatusUserRepository statusUserRepository) {
		this.statusUserRepository = statusUserRepository;
	}

	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Autowired
	public void setRoleRepository(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	@Autowired
	public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public User findByUserName(String userName) {
		return userRepository.findOneByUserName(userName);
	}

	@Override
	public List<User> getAll() {
		return StreamSupport.stream(userRepository.findAll().spliterator(), false).collect(Collectors.toList());
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
		if(!systemUser.getRoles().isEmpty()) {
			user.setRoles(systemUser.getRoles());
		}
		else
		user.setRoles(Collections.singletonList(roleRepository.findOneByName("ROLE_USER")));
		user.setWorkPhone(systemUser.getWorkPhone());
		user.setMobilePhone(systemUser.getMobilePhone());
		user.setStatusUser(systemUser.getStatusUser());
		userRepository.save(user);
	}

	@Override
	public Optional<User> getById(Long id) {
		return userRepository.findById(id);
	}

	// Реализация Алексей Токарев
	@Override
	public User save(User user) {
		return null;
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = userRepository.findOneByUserName(userName);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
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
		return StreamSupport.stream(userRepository.findAll().spliterator(), false).collect(Collectors.toList())
				.stream().filter((u) -> u.getStatusUser().getName().contains("confirmed")).count();
	}

	@Override
	public void activateUser(Long id) {
		User user = userRepository.findById(id).orElse(new User());
		if(user.getId() != null) {
			user.setStatusUser(statusUserRepository.findOneByName("active"));
			userRepository.save(user);
		}
	}

}
