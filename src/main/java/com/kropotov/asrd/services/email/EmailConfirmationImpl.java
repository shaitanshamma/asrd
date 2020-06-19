package com.kropotov.asrd.services.email;

import com.kropotov.asrd.services.EmailConfirmation;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Scope(scopeName = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class EmailConfirmationImpl implements EmailConfirmation {
    private Map<Integer, String> emailConfirmationMap;

    public EmailConfirmationImpl() {
        emailConfirmationMap = new ConcurrentHashMap<>();
    }

    @Override
    public void addValuePassAndEmail(Integer password, String emailVerified) {
        emailConfirmationMap.clear();
        emailConfirmationMap.put(password, emailVerified);
    }

    @Override
    public void removeAll() {
        emailConfirmationMap.clear();
    }

    @Override
    public boolean findByPassAndEmail(Integer password, String emailVerified) {
       return emailConfirmationMap.entrySet().stream()
               .anyMatch(entry -> entry.getKey().equals(password) && entry.getValue().equals(emailVerified));
    }

    @Override
    public boolean isEmpty() {
        return emailConfirmationMap.isEmpty();
    }
}
