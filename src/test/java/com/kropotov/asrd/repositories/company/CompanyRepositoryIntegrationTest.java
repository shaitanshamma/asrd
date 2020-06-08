package com.kropotov.asrd.repositories.company;

import com.kropotov.asrd.entities.company.Company;
import com.kropotov.asrd.entities.company.Employee;
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
public class CompanyRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CompanyRepository companyRepository;

    @Test
    public void whenFindByTitle_thenReturnCompany() {

        Company company = new Company();
        company.setTitle("test_1");
        entityManager.persist(company);
        entityManager.flush();

        Company found = companyRepository.findOneByTitle(company.getTitle());

        assertThat(found.getTitle()).isEqualTo(company.getTitle());
    }
    @Test
    public void whenFindAllByMilitaryRepresentation_thenReturnListCompanies() {
        String militaryRepresentation = "test";
        List<Company> companies = new ArrayList<>();
        for (int i = 0; i <5 ; i++) {
            Company company_i = new Company();
            company_i.setMilitaryRepresentation(militaryRepresentation);
            companies.add(company_i);
            entityManager.persist(company_i);
        }
        entityManager.flush();

        List<Company> found = companyRepository.findByMilitaryRepresentation(militaryRepresentation);

        assertThat(found).isEqualTo(companies);
    }

    @Test
    public void whenFindAll_thenReturnListCompanies() {
        List<Company> companies = new ArrayList<>();
        for (int i = 0; i <5 ; i++) {
            Company company_i = new Company();
            companies.add(company_i);
            entityManager.persist(company_i);
        }
        entityManager.flush();

        List<Company> found = companyRepository.findAll();

        assertThat(found).isEqualTo(companies);
    }
}
