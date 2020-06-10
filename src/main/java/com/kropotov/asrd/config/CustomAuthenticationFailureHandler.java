package com.kropotov.asrd.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {


    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException e)
            throws IOException {

        log.info("Exception for: {}", e.getClass().getName());
        log.info("Exception name: {}", e.getMessage());

        //Переадресация в зависимости от статуса пользователя
        if(e.getMessage().contains("not confirmed")) {
            response.sendRedirect(request.getContextPath() + "/login?error2");
        }
        else if(e.getMessage().contains("not activated")) {
            response.sendRedirect(request.getContextPath() + "/login?error1");
        }
        else response.sendRedirect(request.getContextPath() + "/login?error");
    }
}
