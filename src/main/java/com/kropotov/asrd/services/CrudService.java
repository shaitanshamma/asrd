package com.kropotov.asrd.services;

import java.util.List;
import java.util.Optional;

public interface CrudService<T, ID> {
    List<T> getAll();

    Optional<T> getById(ID id);

    T save(T object);

    void delete(ID id);
}
