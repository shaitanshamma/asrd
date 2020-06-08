package com.kropotov.asrd.services.company;

import com.kropotov.asrd.entities.company.Company;
import com.kropotov.asrd.entities.company.Employee;
import com.kropotov.asrd.repositories.company.EmployeeRepository;
import com.kropotov.asrd.services.springdatajpa.titles.company.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

//@RunWith(SpringRunner.class)
@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceIntegrationTest {

    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeService employeeService;

    List<Employee> employees = new ArrayList<>();

    private Company company = new Company();

    Employee employee = new Employee();

    @Before
    public void setUp() {
        employee.setEmail("alex@mail.ru");
        employee.setMobilPhone("12345");
        employee.setCompany(company);

        employees.add(employee);

        when(employeeRepository.findOneByEmail(employee.getEmail())).thenReturn(employee);
        when(employeeRepository.findByMobilPhone(employee.getMobilPhone())).thenReturn(employee);
        when(employeeRepository.findAllByCompany(employee.getCompany())).thenReturn(employees);
        when(employeeRepository.save(employee)).thenReturn(employee);
    }

    @Test
    public void whenValidEmail_thenEmployeeShouldBeFound() {
        String email = "alex@mail.ru";
        Employee found = employeeService.getOneByEmail(email);

        assertThat(found.getEmail()).isEqualTo(email);
    }

    @Test
    public void whenValidMobilPhone_thenEmployeeShouldBeFound() {
        String mobilPhone = "12345";
        Employee found = employeeService.findOneByMobilPhone(mobilPhone);

        assertThat(found.getMobilPhone()).isEqualTo(mobilPhone);
    }

    @Test
    public void whenFindAllByCompany_thenReturnListEmployee() {

        List<Employee> found = employeeService.getAllByCompany(company);

        assertThat(found).isEqualTo(employees);
    }

    @Test
    public void whenSaveEmployee_thenEmployeeShouldBeSave() {

        Employee found = employeeService.save(employee);

        assertThat(found).isEqualTo(employee);
    }
}
