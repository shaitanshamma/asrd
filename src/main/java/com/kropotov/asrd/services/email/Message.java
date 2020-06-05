package com.kropotov.asrd.services.email;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public abstract class Message {

    private String messageFrom; // от кого (адрес почтового ящика)
    private String messageContent;  // содержание письма
    private Set<String> recipients;   // кому (адрес почтового ящика)

}
