package com.kropotov.asrd.repositories.company;

import com.kropotov.asrd.entities.company.Company;
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
public class CompanyRepositoryIntegrationTest {

    private List<Company> companies = new ArrayList<>();

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CompanyRepository companyRepository;

    @Before
    public void setUp() {
        String militaryRepresentation = "test";

        for (int i = 0; i < 5; i++) {
            Company company_i = new Company();
            company_i.setMilitaryRepresentation(militaryRepresentation);
            company_i.setTitle("test_" + i);
            companies.add(company_i);
            entityManager.persist(company_i);
        }
        entityManager.flush();
    }

    @Test
    public void whenFindByTitle_thenReturnCompany() {

        String title = "test_1";

        Company found = companyRepository.findOneByTitle(title);

        assertThat(found.getTitle()).isEqualTo(title);
    }

    @Test
    public void whenFindAllByMilitaryRepresentation_thenReturnListCompanies() {
        String militaryRepresentation = "test";

        List<Company> found = companyRepository.findByMilitaryRepresentation(militaryRepresentation);

        assertThat(found).isEqualTo(companies);
    }

    @Test
    public void whenFindAll_thenReturnListCompanies() {

        List<Company> found = companyRepository.findAll();

        assertThat(found).isEqualTo(companies);
    }
}
