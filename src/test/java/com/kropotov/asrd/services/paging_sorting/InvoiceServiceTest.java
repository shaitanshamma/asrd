package com.kropotov.asrd.services.paging_sorting;

import com.kropotov.asrd.converters.docs.InvoiceToDto;
import com.kropotov.asrd.entities.User;
import com.kropotov.asrd.entities.company.Company;
import com.kropotov.asrd.entities.docs.Invoice;
import com.kropotov.asrd.repositories.InvoiceRepository;
import com.kropotov.asrd.services.springdatajpa.docs.InvoiceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static com.kropotov.asrd.controllers.paging_sorting.util.PageableAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class InvoiceServiceTest {
	private InvoiceService invoiceService;

	@Mock
	private InvoiceRepository invoiceRepository;
	@Mock
	private InvoiceToDto invoiceToDto;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		invoiceService = new InvoiceService(invoiceRepository, invoiceToDto);
	}

	@Test
	void evaluatesPageableParameter() throws Exception {
		Invoice expectedInvoice = new Invoice(1L, null, LocalDateTime.now(), LocalDateTime.now(), "path string",
				"1",
				null, new Company(), new Company(), "description", new User(),
				null, null);
		List<Invoice> expectedInvoices = Collections.singletonList(expectedInvoice);

		Pageable expectedPageable = PageRequest.of(0, 10, Sort.by("id").descending());
		Page<Invoice> page = new PageImpl<>(expectedInvoices, expectedPageable, expectedInvoices.size());
		given(invoiceRepository.findAll(expectedPageable)).willReturn(page);

		Page<Invoice> invoices = invoiceService.getAll(expectedPageable);
		Invoice invoice = invoices.toList().get(0);
		ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
		verify(invoiceRepository).findAll(pageableCaptor.capture());
		PageRequest pageable = (PageRequest) pageableCaptor.getValue();

		assertNotNull(invoice);
		assertEquals(1, invoices.getNumberOfElements());
		assertEquals(1L, invoice.getId());
		assertEquals("description", invoice.getDescription());
		assertThat(pageable).hasPageNumber(0);
		assertThat(pageable).hasPageSize(10);
		assertThat(pageable).hasSort("id", Sort.Direction.DESC);
	}
}
