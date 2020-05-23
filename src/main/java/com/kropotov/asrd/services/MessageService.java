package com.kropotov.asrd.services;

import com.kropotov.asrd.dto.MailDto;
import org.springframework.stereotype.Service;

@Service
public interface MessageService {

    void sendMessage(MailDto mailDto);
}
