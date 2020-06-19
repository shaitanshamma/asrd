package com.kropotov.asrd.admin;

import com.kropotov.asrd.dto.SystemUser;
import com.kropotov.asrd.entities.User;
import com.kropotov.asrd.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Collections;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@SqlGroup({@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:schema.sql", "classpath:data.sql"})})
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
   void getAllTest() {
        assertEquals(1, userService.getAll().orElse(Collections.emptyList()).size());
    }

    @Test
   void findByUserNameTest() {
        assertNotNull(userService.findByUserName("admin"));
    }

    @Test
    void getByIdTest() {
        assertNotNull(userService.getById(1L).orElse(null));
    }

    @Test
    void activateUserTest() {
        userService.activateUser(1L);
        assertNotNull(userService.findByUserName("admin"));
    }

    @Test
    void saveUserTest() throws NoSuchProviderException, NoSuchAlgorithmException {
        SystemUser systemUser = new SystemUser(Objects.requireNonNull(userService.getById(1L).orElse(null)));
        systemUser.setFirstName("test");
        userService.saveDto(systemUser);
        assertEquals("test", userService.getById(1L).orElse(new User()).getFirstName());
    }
}
