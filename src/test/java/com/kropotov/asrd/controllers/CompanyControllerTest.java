package com.kropotov.asrd.controllers;

import com.kropotov.asrd.converters.company.*;
import com.kropotov.asrd.dto.company.AddressDto;
import com.kropotov.asrd.dto.company.CompanyDto;
import com.kropotov.asrd.dto.company.CompanyPhoneDto;
import com.kropotov.asrd.dto.company.EmployeeDto;
import com.kropotov.asrd.entities.company.Company;
import com.kropotov.asrd.facades.CompanyFacade;
import com.kropotov.asrd.services.springdatajpa.items.DeviceService;
import com.kropotov.asrd.services.springdatajpa.titles.company.AddressService;
import com.kropotov.asrd.services.springdatajpa.titles.company.CompanyPhoneService;
import com.kropotov.asrd.services.springdatajpa.titles.company.CompanyService;
import com.kropotov.asrd.services.springdatajpa.titles.company.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class CompanyControllerTest {

    private CompanyFacade companyFacade;

    @MockBean
    private DeviceService deviceService;

    @Mock
    private CompanyService companyService;
    @Mock
    private AddressService addressService;
    @Mock
    private CompanyPhoneService companyPhoneService;
    @Mock
    private EmployeeService employeeService;
    @Mock
    private CompanyToDto companyToDto;
    @Mock
    private DtoToCompany dtoToCompany;
    @Mock
    private EmployeeToDto employeeToDto;
    @Mock
    private AddressToDto addressToDto;
    @Mock
    private CompanyPhoneToDto companyPhoneToDto;
    @Mock
    private DtoAddressToCompany dtoAddressToCompany;
    @Mock
    private DtoCompanyPhoneToCompanyPhone dtoCompanyPhoneToCompanyPhone;
    @Mock
    private DtoEmployeeToCompany dtoEmployeeToCompany;
    @Mock
    private List<CompanyDto> companyDtos;
    @Mock
    private List<EmployeeDto> employeeDtos;
    @Mock
    private List<AddressDto> addressDtos;
    @Mock
    private List<CompanyPhoneDto> companyPhoneDtos;

    @Autowired
    WebApplicationContext context;

    @Autowired
    private MockMvc mvc;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        companyFacade = new CompanyFacade(companyService, addressService, companyPhoneService, employeeService, companyToDto,
                dtoToCompany, employeeToDto, addressToDto, companyPhoneToDto, dtoAddressToCompany, dtoCompanyPhoneToCompanyPhone,
                dtoEmployeeToCompany);
    }

    @Test
    void givenCompany_whenGetCompany_thenStatus200() throws Exception {

        Company company = new Company();
        List<Company> companies = new ArrayList<>();
        companies.add(company);
        given(companyService.getAll()).willReturn(Optional.of(companies));
        mvc.perform(get("/companies")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("companies/list-companies"))
				.andExpect(model().size(1));

    }

    @WithMockUser(username = "admin", password = "100", roles = {"ADMIN"})
    @Test
    void whenGetCompanyCreatePageWithUserAndPass_thenStatus200() throws Exception {

        mvc.perform(get("/companies/company")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("companies/add-company"));

    }
}
