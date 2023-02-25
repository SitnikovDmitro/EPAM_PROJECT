package app.web;

import app.service.entity.UserService;
import app.service.entity.impl.UserServiceImpl;
import app.userdata.AdminAttributes;
import app.userdata.GuestAttributes;
import app.userdata.LibrarianAttributes;
import app.userdata.ReaderAttributes;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;

/**
 * Web filter controls access of different users to different urls
 **/
public class SecurityFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException, ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession();
        String uri = request.getRequestURI().substring(request.getContextPath().length());

        if (uri.startsWith("/admin")) {
            if (session.getAttribute("adminAttributes") instanceof AdminAttributes) {
                chain.doFilter(request, response);
                return;
            }
            response.sendRedirect(request.getContextPath()+"/error?code=401&description=Unauthorized");
            return;
        }

        if (uri.startsWith("/librarian")) {
            if (session.getAttribute("librarianAttributes") instanceof LibrarianAttributes) {
                chain.doFilter(request, response);
                return;
            }
            response.sendRedirect(request.getContextPath()+"/error?code=401&description=Unauthorized");
            return;
        }

        if (uri.startsWith("/reader")) {
            if (session.getAttribute("readerAttributes") instanceof ReaderAttributes) {
                if (((ReaderAttributes) session.getAttribute("readerAttributes")).getReader().isBlocked()) {
                    response.sendRedirect(request.getContextPath()+"/error?code=403&description=Access denied (account is blocked)");
                    return;
                }

                chain.doFilter(request, response);
                return;
            }
            response.sendRedirect(request.getContextPath()+"/error?code=401&description=Unauthorized");
            return;
        }

        if (uri.startsWith("/guest")) {
            if (!(session.getAttribute("guestAttributes") instanceof GuestAttributes)) {
                session.setAttribute("guestAttributes", new GuestAttributes());
            }
        }

        chain.doFilter(request, response);
    }
}
