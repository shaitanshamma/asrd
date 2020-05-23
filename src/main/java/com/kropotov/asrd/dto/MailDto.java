package com.kropotov.asrd.dto;

import lombok.Data;

import java.io.File;
import java.util.List;

@Data
public class MailDto {

    private String mailFrom; // от кого (адрес почтового ящика)

    private String mailTo;   // кому (адрес почтового ящика)

    private String mailSubject;  // тема письма

    private String mailContent;  // содержание письма

    private String contentType;  // тип контента

    private List<File> attachments;  // список вложений

    public MailDto() {
        contentType = "text/plain";
    }
}
