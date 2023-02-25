package app.controller;

import app.annotations.HttpRequestMapping;
import app.annotations.HttpRequestParameter;
import app.enums.HttpMethod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ErrorController {
    private static final Logger logger = LogManager.getLogger(ErrorController.class);

    @HttpRequestMapping(path = "/error", method = HttpMethod.GET)
    public void error(@HttpRequestParameter(name = "code") String code,
                      @HttpRequestParameter(name = "description") String description,
                      HttpServletRequest request, HttpServletResponse response) throws Exception {

        logger.debug("Call error in error controller with code = "+code);

        request.setAttribute("code", code);
        request.setAttribute("description", description);

        request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/error/error.jsp").forward(request, response);
    }
}
