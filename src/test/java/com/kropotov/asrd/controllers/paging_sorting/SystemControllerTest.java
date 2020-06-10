package com.kropotov.asrd.controllers.paging_sorting;

import com.kropotov.asrd.controllers.SystemController;
import com.kropotov.asrd.controllers.util.PageValues;
import com.kropotov.asrd.converters.UserToSimple;
import com.kropotov.asrd.converters.items.ControlSystemToDto;
import com.kropotov.asrd.converters.items.DtoToControlSystem;
import com.kropotov.asrd.entities.User;
import com.kropotov.asrd.entities.enums.Location;
import com.kropotov.asrd.entities.items.ControlSystem;
import com.kropotov.asrd.entities.titles.SystemTitle;
import com.kropotov.asrd.services.UserService;
import com.kropotov.asrd.services.springdatajpa.items.SystemService;
import com.kropotov.asrd.services.springdatajpa.titles.SystemTitleService;
import com.kropotov.asrd.services.springdatajpa.titles.TopicService;
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

import java.time.LocalDate;
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
public class SystemControllerTest {

	private SystemController systemController;

	@MockBean
	private SystemService systemService;

	@Mock
	private UserService userService;
	@Mock
	private SystemTitleService systemTitleService;
	@Mock
	private DtoToControlSystem dtoToControlSystem;
	@Mock
	private ControlSystemToDto controlSystemToDto;
	@Mock
	private TopicService topicService;
	@Mock
	private UserToSimple userToSimple;

	@Autowired
	WebApplicationContext context;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		systemController = new SystemController(systemService, userService, systemTitleService,
				dtoToControlSystem, controlSystemToDto, topicService,userToSimple);
	}

	@Test
	void evaluatesPageableParameter() throws Exception {
		ControlSystem expectedSystem = new ControlSystem(1L, null, LocalDateTime.now(), LocalDateTime.now(),
				"1", Location.SGP, "test purpose 1", "test purpose passport 1",
				LocalDate.now(), 1, LocalDate.now(), LocalDate.now(), new SystemTitle(),
				new User(), null,
				null, null);
		List<ControlSystem> expectedSystems = Collections.singletonList(expectedSystem);

		Pageable testPageable = PageRequest.of(0, 10, Sort.by("id").descending());
		Page<ControlSystem> page = new PageImpl<>(expectedSystems, testPageable, expectedSystems.size());
		given(systemService.getAll(testPageable)).willReturn(page);

		MockMvcBuilders.webAppContextSetup(this.context).build()
				.perform(get("/systems")
				.param("page", "0")
				.param("size", "10")
				.param("sort", "id,desc"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("text/html;charset=UTF-8"))
				.andExpect(view().name("systems/list-systems"))
				.andExpect(model().size(3))
				.andExpect(model().attribute("pageSizes", PageValues.PAGE_SIZES));

		ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
		verify(systemService).getAll(pageableCaptor.capture());
		PageRequest pageable = (PageRequest) pageableCaptor.getValue();

		assertThat(pageable).hasPageNumber(0);
		assertThat(pageable).hasPageSize(10);
		assertThat(pageable).hasSort("id", Sort.Direction.DESC);
	}
}
