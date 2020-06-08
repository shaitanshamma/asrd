package com.kropotov.asrd.services.email;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class SpringMailIntegrationTest {

    @Autowired
    private EmailService emailService;

    @Rule
    public SmtpServerRule smtpServerRule = new SmtpServerRule(2525);

    @Test
    public void shouldSendSingleMail() throws MessagingException, IOException {
        EmailMessage mail = new EmailMessage();
        mail.setMessageFrom("Test@gmail.com");
        mail.setMailSubject("Test");
        mail.setMessageContent("Content test");
        mail.getRecipients().add("Test@mail.ru");
        emailService.sendMessage(mail);

        MimeMessage[] receivedMessages = smtpServerRule.getMessages();
        assertEquals(1, receivedMessages.length);

        MimeMessage current = receivedMessages[0];

        assertEquals(mail.getMessageFrom(), current.getFrom()[0].toString());

        assertEquals(mail.getMailSubject(), current.getSubject());
        assertTrue(mail.getRecipients().contains(current.getAllRecipients()[0].toString()));
        assertTrue(String.valueOf(current.getContent()).contains(mail.getMessageContent()));

    }

}