package com.kropotov.asrd.repositories;

import com.kropotov.asrd.entities.items.ControlSystem;
import com.kropotov.asrd.entities.titles.SystemTitle;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

// При сборке сервиса создаст имлементацию интерфейса с соответствующими полями, которые будут содержать стандартную реализацию обращений к БД
// Можно дописывать свои методы
@Repository
public interface SystemRepository extends PagingAndSortingRepository<ControlSystem, Long> {
    ControlSystem findByNumberAndTitle(String number, SystemTitle title);
}
