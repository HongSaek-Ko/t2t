package org.t2t;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Error implements ErrorController {
    private String ERROR_TEMPLATE_PATH = "/errors/";

    @RequestMapping(value = "/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return ERROR_TEMPLATE_PATH + "404";
            }
            if (statusCode == HttpStatus.METHOD_NOT_ALLOWED.value()) {
                return ERROR_TEMPLATE_PATH + "405";
            }
            if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return ERROR_TEMPLATE_PATH + "500";
            }

        }
        return "error";
    }
}
