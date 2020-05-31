package com.kropotov.asrd.repositories.company;

import com.kropotov.asrd.entities.company.Company;
import com.kropotov.asrd.entities.company.Employee;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;


public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long> {
    Employee findOneByEmail(String email);

    Employee findByMobilPhone(String mobilPhone);

    List<Employee> findAllByCompany(Company company);

    List<Employee> findAll();
}
