package com.kropotov.asrd.repositories.company;

import com.kropotov.asrd.entities.company.Company;
import com.kropotov.asrd.entities.company.Employee;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;


public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long> {

    @Override
    Optional<Employee> findById(Long aLong);

    Optional<Employee> findOneByEmail(String email);

    Optional<Employee> findByMobilPhone(String mobilPhone);

    List<Employee> findAllByCompany(Company company);

    List<Employee> findAll();
}
