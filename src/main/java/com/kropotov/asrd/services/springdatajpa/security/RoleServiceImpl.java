package com.kropotov.asrd.services.springdatajpa.security;

import com.kropotov.asrd.entities.Role;
import com.kropotov.asrd.repositories.RoleRepository;
import com.kropotov.asrd.services.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService<Role, Long> {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> getAll() {
        return StreamSupport.stream(roleRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public Optional<Role> getById(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    @Transactional
    public Role save(Role role) {
        roleRepository.save(role);
        log.info("Save role {}", role.getName());
        return role;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        roleRepository.deleteById(id);
    }
}
