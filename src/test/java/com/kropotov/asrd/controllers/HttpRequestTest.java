package com.kropotov.asrd.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void navBarHasButtonForSignIn_thenSuccess() {
        String renderedHtml = this.restTemplate.getForObject("http://localhost:" + port + "/", String.class);
        assertEquals(renderedHtml.contains("Войти"), true);
    }

    @Test
    public void navBarNoHasButtonForSignOut_thenSuccess() {
        String renderedHtml = this.restTemplate.getForObject("http://localhost:" + port + "/", String.class);
        assertEquals(renderedHtml.contains("Выйти"), false);
    }
}
