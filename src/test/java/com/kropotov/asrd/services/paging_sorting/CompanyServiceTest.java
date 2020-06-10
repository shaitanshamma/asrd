package com.kropotov.asrd.services.paging_sorting;

import com.kropotov.asrd.converters.company.CompanyToDto;
import com.kropotov.asrd.entities.company.Company;
import com.kropotov.asrd.repositories.company.CompanyRepository;
import com.kropotov.asrd.services.springdatajpa.titles.company.CompanyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.util.Collections;
import java.util.List;

import static com.kropotov.asrd.controllers.paging_sorting.util.PageableAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class CompanyServiceTest {
	private CompanyService companyService;

	@Mock
	private CompanyRepository companyRepository;
	@Mock
	private CompanyToDto companyToDto;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		companyService = new CompanyService(companyRepository, companyToDto);
	}

	@Test
	void evaluatesPageableParameter() throws Exception {
		Company expectedCompany = new Company(1L, "title", "email", "fax", "military", null, null,null);
		List<Company> expectedCompanies = Collections.singletonList(expectedCompany);

		Pageable expectedPageable = PageRequest.of(0, 10, Sort.by("id").descending());
		Page<Company> page = new PageImpl<>(expectedCompanies, expectedPageable, expectedCompanies.size());
		given(companyRepository.findAll(expectedPageable)).willReturn(page);

		Page<Company> companies = companyService.getAll(expectedPageable);
		Company company = companies.toList().get(0);
		ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
		verify(companyRepository).findAll(pageableCaptor.capture());
		PageRequest pageable = (PageRequest) pageableCaptor.getValue();

		assertNotNull(company);
		assertEquals(1, companies.getNumberOfElements());
		assertEquals(1L, company.getId());
		assertEquals("title", company.getTitle());
		assertThat(pageable).hasPageNumber(0);
		assertThat(pageable).hasPageSize(10);
		assertThat(pageable).hasSort("id", Sort.Direction.DESC);
	}
}
