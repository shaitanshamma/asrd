package com.kropotov.asrd.controllers.paging_sorting;

import com.kropotov.asrd.controllers.InvoiceController;
import com.kropotov.asrd.controllers.util.PageValues;
import com.kropotov.asrd.converters.UserToSimple;
import com.kropotov.asrd.converters.docs.DtoToInvoice;
import com.kropotov.asrd.entities.User;
import com.kropotov.asrd.entities.company.Company;
import com.kropotov.asrd.entities.docs.Invoice;
import com.kropotov.asrd.services.StorageService;
import com.kropotov.asrd.services.UserService;
import com.kropotov.asrd.services.springdatajpa.docs.InvoiceService;
import com.kropotov.asrd.services.springdatajpa.titles.DeviceTitleService;
import com.kropotov.asrd.services.springdatajpa.titles.SystemTitleService;
import com.kropotov.asrd.services.springdatajpa.titles.TopicService;
import com.kropotov.asrd.services.springdatajpa.titles.company.CompanyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static com.kropotov.asrd.controllers.paging_sorting.util.PageableAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class InvoiceControllerTest {

	private InvoiceController invoiceController;

	@MockBean
	private InvoiceService invoiceService;
	@Mock
	private CompanyService companyService;
	@Mock
	private UserService userService;
	@Mock
	private TopicService topicService;
	@Mock
	private DeviceTitleService deviceTitleService;
	@Mock
	private SystemTitleService systemTitleService;
	@Mock
	private StorageService storageService;
	@Mock
	private UserToSimple userToSimple;
	@Mock
	private DtoToInvoice dtoToInvoice;

	@Autowired
	WebApplicationContext context;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		invoiceController = new InvoiceController(invoiceService, companyService, userService, topicService,
				deviceTitleService, systemTitleService, storageService, userToSimple, dtoToInvoice);
	}

	@Test
	void evaluatesPageableParameter() throws Exception {
		Invoice expectedInvoice = new Invoice(1L, null, LocalDateTime.now(), LocalDateTime.now(), "path string",
				"1",
				null, new Company(), new Company(), "description", new User(),
				null, null);
		List<Invoice> expectedInvoices = Collections.singletonList(expectedInvoice);
		Pageable testPageable = PageRequest.of(0, 10, Sort.by("id").descending());
		Page<Invoice> page = new PageImpl<>(expectedInvoices, testPageable, expectedInvoices.size());
		given(invoiceService.getAll(testPageable)).willReturn(page);

		MockMvcBuilders.webAppContextSetup(this.context).build()
				.perform(get("/invoices")
				.param("page", "0")
				.param("size", "10")
				.param("sort", "id,desc"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("text/html;charset=UTF-8"))
				.andExpect(view().name("invoices/list-invoices"))
				.andExpect(model().size(3))
				.andExpect(model().attribute("pageSizes", PageValues.PAGE_SIZES));

		ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
		verify(invoiceService).getAll(pageableCaptor.capture());
		PageRequest pageable = (PageRequest) pageableCaptor.getValue();

		assertThat(pageable).hasPageNumber(0);
		assertThat(pageable).hasPageSize(10);
		assertThat(pageable).hasSort("id", Sort.Direction.DESC);
	}
}
