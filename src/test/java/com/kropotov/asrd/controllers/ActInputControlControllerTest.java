package com.kropotov.asrd.controllers;

import com.kropotov.asrd.converters.UserToSimple;
import com.kropotov.asrd.converters.docs.DtoToActInputControl;
import com.kropotov.asrd.entities.docs.ActInputControl;
import com.kropotov.asrd.services.ActInputControlService;
import com.kropotov.asrd.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class ActInputControlControllerTest {

    @Mock
    ActInputControlService actService;

    @Mock
    UserService userService;

    @Mock
    DtoToActInputControl dtoToActInputControl;

    @Mock
    UserToSimple userToSimple;

    ActInputControlController actController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        actController = new ActInputControlController(actService, userService, dtoToActInputControl, userToSimple);
    }

    @Test
    void showById() throws Exception {
        ActInputControl act = new ActInputControl();
        act.setId(1L);
        Optional<ActInputControl> actOptional = Optional.of(act);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(actController).build();

        when(actService.getById(1L)).thenReturn(actOptional);

        mockMvc.perform(get("/acts/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("acts/show"));
    }

    @Test
    void showByIdNotFound() throws Exception {
        ActInputControl act = new ActInputControl();
        act.setId(1L);
        Optional<ActInputControl> actOptional = Optional.of(act);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(actController).build();

        when(actService.getById(2L)).thenReturn(actOptional);

        mockMvc.perform(get("/acts/100000/show"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/acts"));
    }
}