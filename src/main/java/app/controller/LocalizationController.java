package app.controller;

import app.annotations.HttpRequestMapping;
import app.annotations.HttpRequestParameter;
import app.enums.HttpMethod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LocalizationController {
    private static final Logger logger = LogManager.getLogger(LocalizationController.class);

    @HttpRequestMapping(path = "/language/change", method = HttpMethod.POST)
    public void changeLanguage(@HttpRequestParameter(name = "language") String language,
                               HttpServletRequest request, HttpServletResponse response) {

        logger.debug("Call change language in unauthorized user controller with language = "+language);

        request.getSession().setAttribute("lang", language);
        Cookie cookie = new Cookie("lang", language);
        cookie.setMaxAge(60*60*24*365);
        cookie.setPath("/");
        response.addCookie(cookie);

        response.setStatus(HttpServletResponse.SC_OK);
    }
}
