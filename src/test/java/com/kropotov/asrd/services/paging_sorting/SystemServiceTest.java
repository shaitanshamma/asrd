package com.kropotov.asrd.services.paging_sorting;

import com.kropotov.asrd.converters.items.ControlSystemToDto;
import com.kropotov.asrd.entities.User;
import com.kropotov.asrd.entities.enums.Location;
import com.kropotov.asrd.entities.items.ControlSystem;
import com.kropotov.asrd.entities.titles.SystemTitle;
import com.kropotov.asrd.repositories.SystemRepository;
import com.kropotov.asrd.services.springdatajpa.items.SystemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static com.kropotov.asrd.controllers.paging_sorting.util.PageableAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SystemServiceTest {
	private SystemService systemService;

	@Mock
	private SystemRepository systemRepository;
	@Mock
	private ControlSystemToDto controlSystemToDto;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		systemService = new SystemService(systemRepository, controlSystemToDto);
	}

	@Test
	void evaluatesPageableParameter() throws Exception {
		ControlSystem expectedSystem = new ControlSystem(1L, null, LocalDateTime.now(), LocalDateTime.now(),
				"1", Location.SGP, "test purpose 1", "test purpose passport 1",
				LocalDate.now(), 1, LocalDate.now(), LocalDate.now(), new SystemTitle(),
				new User(), null,
				null, null);
		List<ControlSystem> expectedSystems = Collections.singletonList(expectedSystem);

		Pageable expectedPageable = PageRequest.of(0, 10, Sort.by("id").descending());
		Page<ControlSystem> page = new PageImpl<>(expectedSystems, expectedPageable, expectedSystems.size());
		given(systemRepository.findAll(expectedPageable)).willReturn(page);

		Page<ControlSystem> systems = systemService.getAll(expectedPageable);
		ControlSystem system = systems.toList().get(0);
		ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
		verify(systemRepository).findAll(pageableCaptor.capture());
		PageRequest pageable = (PageRequest) pageableCaptor.getValue();

		assertNotNull(system);
		assertEquals(1, systems.getNumberOfElements());
		assertEquals(1L, system.getId());
		assertEquals("test purpose 1", system.getPurpose());
		assertThat(pageable).hasPageNumber(0);
		assertThat(pageable).hasPageSize(10);
		assertThat(pageable).hasSort("id", Sort.Direction.DESC);
	}
}
