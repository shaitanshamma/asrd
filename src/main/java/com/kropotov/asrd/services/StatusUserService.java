package com.kropotov.asrd.services;

import com.kropotov.asrd.entities.StatusUser;

import java.util.List;
import java.util.Optional;

public interface StatusUserService {

    Optional<List<StatusUser>> getAll();
}
