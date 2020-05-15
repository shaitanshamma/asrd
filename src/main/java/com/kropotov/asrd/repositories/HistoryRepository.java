package com.kropotov.asrd.repositories;

import java.util.List;


public interface HistoryRepository<T, ID> {
    List<T> getHistoryById(Class<T> entityType, ID id);
}
