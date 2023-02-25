package app.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


/**
 * Web filter that puts language attribute in session
 **/
public class LocalizationFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(LocalizationFilter.class);

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession();

        Object lang = session.getAttribute("lang");

        if (lang == null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("lang")) {
                    logger.info("Found language cookie with value: "+cookie.getValue());
                    lang = cookie.getValue();
                    break;
                }

            }
        }

        if (lang != null && (lang.equals("eng") || lang.equals("ukr"))) {
            session.setAttribute("lang", lang);
        } else {
            session.setAttribute("lang", "eng");
            logger.info("Selected default language: eng");
        }

        chain.doFilter(request, response);
    }
}