package com.kropotov.asrd.services.company;

import com.kropotov.asrd.entities.company.Company;
import com.kropotov.asrd.entities.company.CompanyPhone;
import com.kropotov.asrd.repositories.company.CompanyPhoneRepository;
import com.kropotov.asrd.services.springdatajpa.titles.company.CompanyPhoneService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

//@RunWith(SpringRunner.class)
@RunWith(MockitoJUnitRunner.class)
public class CompanyPhoneServiceIntegrationTest {

    @Mock
    private CompanyPhoneRepository companyPhoneRepository;
    @InjectMocks
    private CompanyPhoneService companyPhoneService;

    List<CompanyPhone> companyPhones = new ArrayList<>();

    private Company company = new Company();

    CompanyPhone companyPhone = new CompanyPhone();

    @Before
    public void setUp() {

        companyPhone.setCompany(company);

        companyPhones.add(companyPhone);

        when(companyPhoneRepository.findAll()).thenReturn(companyPhones);
        when(companyPhoneRepository.save(companyPhone)).thenReturn(companyPhone);
    }

    @Test
    public void whenFindAllCompanyPhones_thenReturnCompanyPhonesList() {

        List<CompanyPhone> found = companyPhoneService.getAll().get();

        assertThat(found).isEqualTo(companyPhones);
    }


    @Test
    public void whenSaveAddress_thenAddressShouldBeSave() {

        CompanyPhone found = companyPhoneService.save(companyPhone);

        assertThat(found).isEqualTo(companyPhone);
    }
}
