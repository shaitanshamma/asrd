package com.kropotov.asrd.services.springdatajpa.security;

import com.kropotov.asrd.entities.StatusUser;
import com.kropotov.asrd.repositories.StatusUserRepository;
import com.kropotov.asrd.services.StatusUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StatusUserServiceImpl implements StatusUserService {

    private final StatusUserRepository statusUserRepository;

    @Override
    public Optional<List<StatusUser>> getAll() {
        return Optional.ofNullable(statusUserRepository.findAll());
    }
}
