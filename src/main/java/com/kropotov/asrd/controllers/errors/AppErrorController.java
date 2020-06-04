package com.kropotov.asrd.controllers.errors;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Artem Kropotov on 02.06.2020
 */
@Controller
public class AppErrorController implements ErrorController {

    @GetMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object status  = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "errors/404error";
            }
            else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "errors/500error";
            }
            else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                return "errors/403error";
            }
        }
        return "errors/error";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
