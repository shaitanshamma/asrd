package com.kropotov.asrd.services.email;

import com.kropotov.asrd.validation.EmailValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
@RunWith(SpringRunner.class)
public class EmailTest {

    @Autowired
    EmailValidator emailValidator;

    @Test
    public void testAddress() {
        assertFalse(emailValidator.isValid("asrd", null));
        assertTrue(emailValidator.isValid("mail@mail.com", null));
    }
}
