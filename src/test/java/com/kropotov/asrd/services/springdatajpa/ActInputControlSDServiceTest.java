package com.kropotov.asrd.services.springdatajpa;

import com.kropotov.asrd.converters.docs.ActInputControlToDto;
import com.kropotov.asrd.converters.docs.DtoToActInputControl;
import com.kropotov.asrd.entities.docs.ActInputControl;
import com.kropotov.asrd.repositories.ActInputControlRepository;
import com.kropotov.asrd.services.springdatajpa.docs.ActInputControlSDService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class ActInputControlSDServiceTest {

    public static final long ID = 1L;

    ActInputControlSDService actInputControlSDService;

    @Mock
    ActInputControlRepository actInputControlRepository;

    @Mock
    DtoToActInputControl dtoToActInputControl;

    @Mock
    ActInputControlToDto actInputControlToDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        actInputControlSDService = new ActInputControlSDService(actInputControlRepository, dtoToActInputControl, actInputControlToDto);
    }

    @Test
    void getAll() {
        ActInputControl act1 = new ActInputControl();
        ActInputControl act2 = new ActInputControl();
        act1.setId(1L);
        act2.setId(2L);

        List<ActInputControl> actList = new ArrayList<>();

        actList.add(act1);
        actList.add(act2);

        when(actInputControlRepository.findAll()).thenReturn(actList);

        List<ActInputControl> actListReturned = actInputControlSDService.getAll();

        assertEquals(2, actListReturned.size());
        verify(actInputControlRepository, times(1)).findAll();
        verify(actInputControlRepository, never()).findById(anyLong());
    }

    @Test
    void getById() {
        ActInputControl act = new ActInputControl();
        act.setId(ID);
        Optional<ActInputControl> actOptional = Optional.of(act);

        when(actInputControlRepository.findById(ID)).thenReturn(actOptional);

        Optional<ActInputControl> actReturned = actInputControlSDService.getById(ID);

        assertNotNull(actReturned.orElse(null));
        verify(actInputControlRepository, times(1)).findById(anyLong());
        verify(actInputControlRepository, never()).findAll();
    }

}