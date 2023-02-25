package app.controller;

import app.annotations.HttpRequestMapping;
import app.enums.HttpMethod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WelcomeController {
    private static final Logger logger = LogManager.getLogger(WelcomeController.class);

    @HttpRequestMapping(path = "/", method = HttpMethod.GET)
    public void welcome(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.debug("Welcome called in welcome controller");

        response.sendRedirect(request.getContextPath()+"/guest/home-page/show");
    }
}
