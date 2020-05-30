package com.kropotov.asrd.services.email;

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
public class EMailService implements MessageService<EMailMessage> {

    private final JavaMailSender mailSender;

    @Override
    public void sendMessage(EMailMessage eMailMessage) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setSubject(eMailMessage.getMailSubject());
            mimeMessageHelper.setFrom(eMailMessage.getMessageFrom());
            mimeMessageHelper.setTo(eMailMessage.getMessageTo());
            mimeMessageHelper.setText(eMailMessage.getMessageContent());
            List<File> listAttachments = eMailMessage.getAttachments();
            if (eMailMessage.getAttachments() != null) {
                eMailMessage.getAttachments().stream().forEach(f -> {
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
