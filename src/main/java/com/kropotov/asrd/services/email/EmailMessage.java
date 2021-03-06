package com.kropotov.asrd.services.email;

import lombok.*;

import java.io.File;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class EmailMessage extends Message {

    private String mailSubject;  // тема письма

    private String contentType;  // тип контента

    private List<File> attachments;  // список вложений

    public EmailMessage() {
        contentType = "text/plain";
    }
}
