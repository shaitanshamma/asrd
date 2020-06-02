package com.kropotov.asrd.services.springdatajpa.titles.company;

import com.kropotov.asrd.entities.company.Company;
import com.kropotov.asrd.entities.company.Employee;
import com.kropotov.asrd.repositories.company.EmployeeRepository;
import com.kropotov.asrd.services.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService implements CrudService<Employee, Long> {

    private final EmployeeRepository employeeRepository;

    @Override
    public Optional<List<Employee>> getAll() {
        return Optional.ofNullable(employeeRepository.findAll());
    }

    public Optional<Employee> getById(Long id) {
        return employeeRepository.findById(id);
    }


    public Employee getOneByEmail(String email) {
        return employeeRepository.findOneByEmail(email);
    }

    @Transactional
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public void deleteById(Long id) {
        employeeRepository.deleteById(id);
    }

    public List<Employee> getAllByCompany(Company company) {
        return employeeRepository.findAllByCompany(company);
    }

    public Employee findOneByMobilPhone(String mobilPhone) {
        return employeeRepository.findByMobilPhone(mobilPhone);
    }
}
