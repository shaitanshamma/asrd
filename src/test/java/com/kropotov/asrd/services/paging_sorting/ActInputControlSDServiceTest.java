package com.kropotov.asrd.services.paging_sorting;

import com.kropotov.asrd.converters.docs.ActInputControlToDto;
import com.kropotov.asrd.entities.User;
import com.kropotov.asrd.entities.docs.ActInputControl;
import com.kropotov.asrd.entities.enums.Result;
import com.kropotov.asrd.repositories.ActInputControlRepository;
import com.kropotov.asrd.services.springdatajpa.docs.ActInputControlSDService;
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

class ActInputControlSDServiceTest {
    ActInputControlSDService actInputControlSDService;

    @Mock
    ActInputControlRepository actInputControlRepository;
//    @Mock
//    DtoToActInputControl dtoToActInputControl;
    @Mock
    ActInputControlToDto actInputControlToDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        actInputControlSDService = new ActInputControlSDService(actInputControlRepository, actInputControlToDto);
    }

    @Test
    void evaluatesPageableParameter() throws Exception {
        ActInputControl expectedAct = new ActInputControl(1L, null, LocalDateTime.now(), LocalDateTime.now(), "path string",
                "1", null, LocalDate.now(), Result.POSITIVE, "description", new User(),
                null, null);

        List<ActInputControl> expectedActs = Collections.singletonList(expectedAct);

        Pageable expectedPageable = PageRequest.of(0, 10, Sort.by("id").descending());
        Page<ActInputControl> page = new PageImpl<>(expectedActs, expectedPageable, expectedActs.size());
        given(actInputControlRepository.findAll(expectedPageable)).willReturn(page);

        Page<ActInputControl> acts = actInputControlSDService.getAll(expectedPageable);
        ActInputControl act = acts.toList().get(0);
        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        verify(actInputControlRepository).findAll(pageableCaptor.capture());
        PageRequest pageable = (PageRequest) pageableCaptor.getValue();

        assertNotNull(act);
        assertEquals(1, acts.getNumberOfElements());
        assertEquals(1L, act.getId());
        assertEquals("description", act.getDescription());
        assertThat(pageable).hasPageNumber(0);
        assertThat(pageable).hasPageSize(10);
        assertThat(pageable).hasSort("id", Sort.Direction.DESC);
    }
}