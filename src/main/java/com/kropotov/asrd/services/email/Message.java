package com.kropotov.asrd.services.email;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Message {
    private String messageFrom; // от кого (адрес почтового ящика)
    private String messageContent;  // содержание письма
    private String messageTo;   // кому (адрес почтового ящика)
}
