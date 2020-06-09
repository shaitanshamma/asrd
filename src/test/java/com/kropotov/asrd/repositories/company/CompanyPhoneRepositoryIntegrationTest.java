package com.kropotov.asrd.repositories.company;

import com.kropotov.asrd.entities.company.Company;
import com.kropotov.asrd.entities.company.CompanyPhone;
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
public class CompanyPhoneRepositoryIntegrationTest {

    private List<CompanyPhone> companyPhones = new ArrayList<>();

    private Company company = new Company();

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CompanyPhoneRepository companyPhoneRepository;

    @Before
    public void setUp() {

        for (int i = 0; i < 5; i++) {
            CompanyPhone companyPhone_i = new CompanyPhone();
            companyPhones.add(companyPhone_i);
            entityManager.persist(companyPhone_i);
            entityManager.persist(company);
        }
        entityManager.flush();
    }

    @Test
    public void whenFindAll_thenReturnListCompanyPhones() {

        List<CompanyPhone> found = companyPhoneRepository.findAll();

        assertThat(found).isEqualTo(companyPhones);
    }

    @Test
    public void whenFindAllByCompany_thenReturnListCompanyPhones() {

        List<CompanyPhone> found = companyPhoneRepository.findAll();

        assertThat(found).isEqualTo(companyPhones);
    }
}
