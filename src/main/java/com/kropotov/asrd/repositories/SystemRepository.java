package com.kropotov.asrd.repositories;

import com.kropotov.asrd.entities.items.ControlSystem;
import com.kropotov.asrd.entities.titles.SystemTitle;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.history.RevisionRepository;

// При сборке сервиса создаст имлементацию интерфейса с соответствующими полями, которые будут содержать стандартную реализацию обращений к БД
// Можно дописывать свои методы

public interface SystemRepository extends RevisionRepository<ControlSystem, Long, Long>,
        PagingAndSortingRepository<ControlSystem, Long>, HistoryRepository<ControlSystem, Long> {

    ControlSystem findByNumberAndTitle(String number, SystemTitle title);
}
