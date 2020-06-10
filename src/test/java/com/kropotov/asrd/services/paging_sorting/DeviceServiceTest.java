package com.kropotov.asrd.services.paging_sorting;

import com.kropotov.asrd.converters.items.DeviceToDto;
import com.kropotov.asrd.entities.User;
import com.kropotov.asrd.entities.enums.Location;
import com.kropotov.asrd.entities.items.Device;
import com.kropotov.asrd.entities.titles.DeviceTitle;
import com.kropotov.asrd.repositories.DeviceRepository;
import com.kropotov.asrd.services.springdatajpa.items.DeviceService;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class DeviceServiceTest {
	private DeviceService deviceService;

	@Mock
	private DeviceRepository deviceRepository;
	@Mock
	private DeviceToDto deviceToDto;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		deviceService = new DeviceService(deviceRepository, deviceToDto);
	}

	@Test
	void evaluatesPageableParameter() throws Exception {
		Device expectedDevice = new Device(1L, null, LocalDateTime.now(), LocalDateTime.now(),
				"1", Location.SGP, "test purpose 1",
				"test purpose passport 1", LocalDate.now(), 1, LocalDate.now(), LocalDate.now(),
				new DeviceTitle(), null, null, null,
				null, new User());
		List<Device> expectedDevices = Collections.singletonList(expectedDevice);

		Pageable expectedPageable = PageRequest.of(0, 10, Sort.by("id").descending());
		Page<Device> page = new PageImpl<>(expectedDevices, expectedPageable, expectedDevices.size());
		given(deviceRepository.findAll(expectedPageable)).willReturn(page);

		Page<Device> devices = deviceService.getAll(expectedPageable);
		Device device = devices.toList().get(0);
		ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
		verify(deviceRepository).findAll(pageableCaptor.capture());
		PageRequest pageable = (PageRequest) pageableCaptor.getValue();

		assertNotNull(device);
		assertEquals(1, devices.getNumberOfElements());
		assertEquals(1L, device.getId());
		assertEquals("test purpose 1", device.getPurpose());
		assertThat(pageable).hasPageNumber(0);
		assertThat(pageable).hasPageSize(10);
		assertThat(pageable).hasSort("id", Sort.Direction.DESC);
	}
}
