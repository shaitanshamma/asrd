package com.kropotov.asrd.config;


import com.kropotov.asrd.entities.User;
import com.kropotov.asrd.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

// то что происходит если клиент аутентифицировался
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private UserService userService;

    @Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		System.out.println("\n\nIn customAuthenticationSuccessHandler\n\n");
		String userName = authentication.getName(); // достается имя пользователя
		System.out.println("userName=" + userName);
		User theUser = userService.findByUserName(userName);
		HttpSession session = request.getSession(); // запрос сессии
		session.setAttribute("user", theUser);
		if (!request.getHeader("referer").contains("login")) {
			response.sendRedirect(request.getHeader("referer"));
		} else {
			response.sendRedirect(request.getContextPath() + "/");
		}

	}
}
