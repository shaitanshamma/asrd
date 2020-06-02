package com.kropotov.asrd.services.email;

import org.springframework.stereotype.Service;

@Service
public interface MessageService<T extends Message> {

    void sendMessage(T message);
}
