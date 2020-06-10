package com.kropotov.asrd.config;


import com.kropotov.asrd.entities.User;
import com.kropotov.asrd.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

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
			throws IOException {
		String userName = authentication.getName(); // достается имя пользователя
		User theUser = userService.findByUserName(userName);
		HttpSession session = request.getSession(); // запрос сессии
		session.setAttribute("user", theUser);
		if (!request.getParameter("referer").contains("login")) {
			response.sendRedirect(request.getParameter("referer")); // refer возвращает туда откуда пришел пользователь
		} else {
			response.sendRedirect(request.getContextPath() + "/");
		}
	}
}
