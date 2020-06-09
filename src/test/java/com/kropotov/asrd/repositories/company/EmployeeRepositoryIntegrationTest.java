package com.kropotov.asrd.repositories.company;

import com.kropotov.asrd.entities.company.Company;
import com.kropotov.asrd.entities.company.Employee;
import org.junit.Before;
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

    private List<Employee> employees = new ArrayList<>();
    private Company company = new Company();

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Before
    public void setUp() {

        for (int i = 0; i < 5; i++) {
            Employee employee_i = new Employee();
            employee_i.setEmail("123@mail.ru_"+ i);
            employee_i.setMobilPhone("12345_" + i);
            employee_i.setCompany(company);
            employees.add(employee_i);
            entityManager.persist(employee_i);
            entityManager.persist(company);
        }
        entityManager.flush();
    }

    @Test
    public void whenFindByMobilPhone_thenReturnEmployee() {
        String mobilPhone = "12345_1";

        Employee found = employeeRepository.findByMobilPhone(mobilPhone);

        assertThat(found.getMobilPhone()).isEqualTo(mobilPhone);
    }

    @Test
    public void whenFindByEmail_thenReturnEmployee() {
        String email="123@mail.ru_1";
        Employee found = employeeRepository.findOneByEmail(email);

        assertThat(found.getEmail()).isEqualTo(email);
    }

    @Test
    public void whenFindAll_thenReturnListEmployee() {

        List<Employee> found = employeeRepository.findAll();

        assertThat(found).isEqualTo(employees);
    }

    @Test
    public void whenFindAllByCompany_thenReturnListEmployee() {

        List<Employee> found = employeeRepository.findAllByCompany(company);

        assertThat(found).isEqualTo(employees);
    }
}
