package com.kropotov.asrd.services.springdatajpa.titles.company;

import com.kropotov.asrd.entities.company.Company;
import com.kropotov.asrd.entities.company.Employee;
import com.kropotov.asrd.repositories.company.EmployeesRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeesRepository employeesRepository;

    public Optional<Employee> getById(Long id){
        return employeesRepository.findById(id);
    }

    public List<Employee> getAll(){
        return employeesRepository.findAll();
    }

    public Employee getOneByEmail(String email){
        return employeesRepository.findOneByEmail(email);
    }

    public Employee save(Employee employee){
        return employeesRepository.save(employee);
    }

    public void delete(Long id){
        employeesRepository.deleteById(id);
    }

    public List<Employee> getAllByCompany(Company company){
        return employeesRepository.findAllByCompany(company);
    }

    public Employee findOneByMobilPhone(String mobilPhone){
        return employeesRepository.findByMobilPhone(mobilPhone);
    }
}
