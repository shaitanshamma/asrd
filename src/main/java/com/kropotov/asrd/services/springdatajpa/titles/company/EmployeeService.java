package com.kropotov.asrd.services.springdatajpa.titles.company;

import com.kropotov.asrd.entities.company.Company;
import com.kropotov.asrd.entities.company.Employee;
import com.kropotov.asrd.repositories.company.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public Optional<Employee> getById(Long id){
        return employeeRepository.findById(id);
    }

    public List<Employee> getAll(){
        return employeeRepository.findAll();
    }

    public Employee getOneByEmail(String email){
        return employeeRepository.findOneByEmail(email);
    }

    public Employee save(Employee employee){
        return employeeRepository.save(employee);
    }

    public void delete(Long id){
        employeeRepository.deleteById(id);
    }

    public List<Employee> getAllByCompany(Company company){
        return employeeRepository.findAllByCompany(company);
    }

    public Employee findOneByMobilPhone(String mobilPhone){
        return employeeRepository.findByMobilPhone(mobilPhone);
    }
}
