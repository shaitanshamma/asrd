package com.kropotov.asrd.services;

import com.kropotov.asrd.dto.MailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MailService implements MessageService {

    private final JavaMailSender mailSender;

    @Override
    public void sendMessage(MailDto mailDto) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {


        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setSubject(mailDto.getMailSubject());
        mimeMessageHelper.setFrom(mailDto.getMailFrom());
        mimeMessageHelper.setTo(mailDto.getMailTo());
        mimeMessageHelper.setText(mailDto.getMailContent());
        List<File> listAttachments = mailDto.getAttachments();
        if (mailDto.getAttachments() != null) {
            mailDto.getAttachments().stream().forEach(f -> {
                try {
                    mimeMessageHelper.addAttachment(f.getName(), f);
                } catch (MessagingException ex) {
                    ex.printStackTrace();
                }
            });
        }

        mailSender.send(mimeMessageHelper.getMimeMessage());

        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }
}
