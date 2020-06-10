package com.kropotov.asrd.controllers.paging_sorting;

import com.kropotov.asrd.controllers.DeviceController;
import com.kropotov.asrd.controllers.util.PageValues;
import com.kropotov.asrd.converters.UserToSimple;
import com.kropotov.asrd.converters.items.DeviceToDto;
import com.kropotov.asrd.converters.items.DtoToDevice;
import com.kropotov.asrd.entities.User;
import com.kropotov.asrd.entities.enums.Location;
import com.kropotov.asrd.entities.items.Device;
import com.kropotov.asrd.entities.titles.DeviceTitle;
import com.kropotov.asrd.services.UserService;
import com.kropotov.asrd.services.springdatajpa.items.DeviceComponentService;
import com.kropotov.asrd.services.springdatajpa.items.DeviceService;
import com.kropotov.asrd.services.springdatajpa.items.SystemService;
import com.kropotov.asrd.services.springdatajpa.titles.DeviceComponentTitleService;
import com.kropotov.asrd.services.springdatajpa.titles.DeviceTitleService;
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
public class DeviceControllerTest {

	private DeviceController deviceController;

	@MockBean
	private DeviceService deviceService;
	@Mock
	private DeviceTitleService deviceTitleService;
	@Mock
	private UserService userService;
	@Mock
	private TopicService topicService;
	@Mock
	private DeviceComponentService deviceComponentService;
	@Mock
	private SystemService systemService;
	@Mock
	private DeviceComponentTitleService deviceComponentTitleService;
	@Mock
	private UserToSimple userToSimple;
	@Mock
	private DeviceToDto deviceToDto;
	@Mock
	private DtoToDevice dtoToDevice;

	@Autowired
	WebApplicationContext context;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		deviceController = new DeviceController(deviceService, deviceTitleService, userService, topicService,
				deviceComponentService, systemService, deviceComponentTitleService, userToSimple, deviceToDto, dtoToDevice);
	}

	@Test
	void evaluatesPageableParameter() throws Exception {
		Device expectedDevice = new Device(1L, null, LocalDateTime.now(), LocalDateTime.now(),
				"1", Location.SGP, "test purpose 1",
				"test purpose passport 1", LocalDate.now(), 1, LocalDate.now(), LocalDate.now(),
				new DeviceTitle(), null, null, null,
				null, new User());
		List<Device> expectedDevices = Collections.singletonList(expectedDevice);

		Pageable testPageable = PageRequest.of(0, 10, Sort.by("id").descending());
		Page<Device> page = new PageImpl<>(expectedDevices, testPageable, expectedDevices.size());
		given(deviceService.getAll(testPageable)).willReturn(page);

		MockMvcBuilders.webAppContextSetup(this.context).build()
				.perform(get("/devices")
				.param("page", "0")
				.param("size", "10")
				.param("sort", "id,desc"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("text/html;charset=UTF-8"))
				.andExpect(view().name("devices/list-devices"))
				.andExpect(model().size(3))
				.andExpect(model().attribute("pageSizes", PageValues.PAGE_SIZES));

		ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
		verify(deviceService).getAll(pageableCaptor.capture());
		PageRequest pageable = (PageRequest) pageableCaptor.getValue();

		assertThat(pageable).hasPageNumber(0);
		assertThat(pageable).hasPageSize(10);
		assertThat(pageable).hasSort("id", Sort.Direction.DESC);
	}
}
