package com.kropotov.asrd.admin;

import com.kropotov.asrd.entities.SystemUser;
import com.kropotov.asrd.entities.User;
import com.kropotov.asrd.services.UserService;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Objects;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
   void getAllTest() {
        assertEquals(1, userService.getAll().size());
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
    void saveUserTest() {
        SystemUser systemUser = new SystemUser(Objects.requireNonNull(userService.getById(1L).orElse(null)));
        systemUser.setFirstName("test");
        userService.save(systemUser);
        assertEquals("test", userService.getById(1L).orElse(new User()).getFirstName());
    }
}
