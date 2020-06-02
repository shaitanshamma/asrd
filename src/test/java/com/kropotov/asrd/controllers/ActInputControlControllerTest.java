package com.kropotov.asrd.controllers;

import com.kropotov.asrd.converters.docs.DtoToActInputControl;
import com.kropotov.asrd.entities.docs.ActInputControl;
import com.kropotov.asrd.facades.docs.ActInputControlFacade;
import com.kropotov.asrd.services.ActInputControlService;
import com.kropotov.asrd.services.springdatajpa.docs.InvoiceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class ActInputControlControllerTest {

    @Autowired
    InvoiceService invoiceService;

    @Autowired
    DtoToActInputControl dtoToActInputControl;

    @Mock
    ActInputControlFacade actInputControlFacade;

    @Mock
    ActInputControlService actService;

    ActInputControlController actController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        actController = new ActInputControlController(actInputControlFacade);
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

    // Этот тест не проходит потому, что неправильно реализована функциональность в классе. Должна возвращаться ошибка 404.
    // Переделать на фасад весь тест
    @Test
    void showByIdNotFound() throws Exception {
        ActInputControl act = new ActInputControl();
        act.setId(1L);
        Optional<ActInputControl> actOptional = Optional.of(act);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(actController).build();

        when(actService.getById(2L)).thenReturn(actOptional);

        mockMvc.perform(get("/acts/100000/show"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("redirect:/acts"));
    }
}