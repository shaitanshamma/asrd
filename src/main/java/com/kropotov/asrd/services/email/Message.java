package com.kropotov.asrd.services.email;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public abstract class Message {

    @NonNull
    private String messageFrom; // от кого (адрес почтового ящика)
    @NonNull
    private String messageContent;  // содержание письма
    @NonNull
    private Set<String> recipients;   // кому (адрес почтового ящика)
}
