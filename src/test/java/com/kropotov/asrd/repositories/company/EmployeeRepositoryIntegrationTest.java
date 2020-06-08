package com.kropotov.asrd.repositories.company;

import com.kropotov.asrd.entities.company.Company;
import com.kropotov.asrd.entities.company.Employee;
import org.checkerframework.checker.units.qual.C;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EmployeeRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void whenFindByMobilPhone_thenReturnEmployee() {

        Employee employee = new Employee();
        employee.setMobilPhone("12345");
        entityManager.persist(employee);
        entityManager.flush();

        Employee found = employeeRepository.findByMobilPhone(employee.getMobilPhone());

        assertThat(found.getMobilPhone()).isEqualTo(employee.getMobilPhone());
    }
    @Test
    public void whenFindByEmail_thenReturnEmployee() {

        Employee employee = new Employee();
        employee.setEmail("123@mail.ru");
        entityManager.persist(employee);
        entityManager.flush();

        Employee found = employeeRepository.findOneByEmail(employee.getEmail());

        assertThat(found.getEmail()).isEqualTo(employee.getEmail());
    }
    @Test
    public void whenFindAll_thenReturnListEmployee() {
        List<Employee> employees = new ArrayList<>();
        for (int i = 0; i <5 ; i++) {
            Employee employee_i = new Employee();
            employees.add(employee_i);
            entityManager.persist(employee_i);
        }
        entityManager.flush();

        List<Employee> found = employeeRepository.findAll();

        assertThat(found).isEqualTo(employees);
    }

    @Test
    public void whenFindAllByCompany_thenReturnListEmployee() {
        List<Employee> employees = new ArrayList<>();
        Company company = new Company();
        for (int i = 0; i <5 ; i++) {
            Employee employee_i = new Employee();
            employee_i.setCompany(company);
            employees.add(employee_i);
            entityManager.persist(employee_i);
            entityManager.persist(company);
        }
        entityManager.flush();

        List<Employee> found = employeeRepository.findAllByCompany(company);

        assertThat(found).isEqualTo(employees);
    }
}
