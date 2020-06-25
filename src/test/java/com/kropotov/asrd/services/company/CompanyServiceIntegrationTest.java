package com.kropotov.asrd.services.company;

import com.kropotov.asrd.entities.company.Company;
import com.kropotov.asrd.repositories.company.CompanyRepository;
import com.kropotov.asrd.services.springdatajpa.titles.company.CompanyService;
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
public class CompanyServiceIntegrationTest {

    @Mock
    private CompanyRepository companyRepository;
    @InjectMocks
    private CompanyService companyService;

    List<Company> companies = new ArrayList<>();

    private Company company = new Company();


    @Before
    public void setUp() {
        company.setTitle("1");
        company.setMilitaryRepresentation("1");
        companies.add(company);

        when(companyRepository.findAll()).thenReturn(companies);
        when(companyRepository.findOneByTitle(company.getTitle())).thenReturn(java.util.Optional.ofNullable(company));
        when(companyRepository.findByMilitaryRepresentation(company.getMilitaryRepresentation())).thenReturn(companies);
        when(companyRepository.save(company)).thenReturn(company);
    }

    @Test
    public void whenFindAllCompany_thenReturnCompaniesList() {

        List<Company> found = companyService.getAll().get();

        assertThat(found).isEqualTo(companies);
    }


    @Test
    public void whenFindByMilitaryRepresentation_thenReturnListCompanies() {
        String militaryRepresentation = "1";
        List<Company> found = companyService.getByMilitaryRepresentation(militaryRepresentation);

        assertThat(found).isEqualTo(companies);
    }

    @Test
    public void whenFindByTitle_thenCompanyShouldBeSave() {
        String title = "1";
        Company found = companyService.getOneByTitle(title).get();

        assertThat(found).isEqualTo(company);
    }
    @Test
    public void whenSaveCompany_thenCompanyShouldBeSave() {

        Company found = companyService.save(company);

        assertThat(found).isEqualTo(company);
    }
}
