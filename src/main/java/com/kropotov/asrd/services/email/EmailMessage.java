package com.kropotov.asrd.services.email;

import lombok.*;

import java.io.File;
import java.util.HashSet;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class EmailMessage extends Message {

    @NonNull
    private String mailSubject;  // тема письма

    @NonNull
    private String contentType;  // тип контента

    private List<File> attachments;  // список вложений

    public EmailMessage() {
        contentType = "text/plain";
        setRecipients(new HashSet<>());
    }

}
