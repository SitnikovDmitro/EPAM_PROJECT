package app.controller;

import app.annotations.HttpRequestMapping;
import app.annotations.HttpRequestParameter;
import app.annotations.HttpSessionAttribute;
import app.entity.User;
import app.enums.HttpMethod;
import app.enums.UserRole;
import app.exceptions.DatabaseException;
import app.tuples.SendAccessLinkResult;
import app.tuples.UserCreationEditionResult;
import app.tuples.UserFindResult;
import app.service.entity.UserService;
import app.service.mapper.OperationResultMapper;
import app.service.utils.CryptographicUtil;
import app.userdata.AdminAttributes;
import app.userdata.LibrarianAttributes;
import app.userdata.ReaderAttributes;
import app.userdata.GuestAttributes;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationController {
    private static final Logger logger = LogManager.getLogger(AuthenticationController.class);

    private final OperationResultMapper operationResultMapper;
    private final UserService userService;
    private final CryptographicUtil cryptographicUtil;


    public AuthenticationController(OperationResultMapper operationResultMapper, UserService userService, CryptographicUtil cryptographicUtil) {
        this.operationResultMapper = operationResultMapper;
        this.userService = userService;
        this.cryptographicUtil = cryptographicUtil;
    }

    @HttpRequestMapping(path = "/sign-up", method = HttpMethod.POST)
    public void signUp(@HttpRequestParameter(name = "firstname", required = false) String firstname,
                       @HttpRequestParameter(name = "lastname", required = false) String lastname,
                       @HttpRequestParameter(name = "email", required = false) String email,
                       @HttpRequestParameter(name = "password", required = false) String password,
                       @HttpRequestParameter(name = "gCaptchaResponse", required = false) String gCaptchaResponse,
                       @HttpSessionAttribute(name = "lang") String lang,
                       HttpServletRequest request, HttpServletResponse response) throws Exception {

        logger.debug("Call sign up in authentication controller");

        UserCreationEditionResult result = userService.createReader(firstname, lastname, email, password, gCaptchaResponse);

        if (result.getSuccess()) {
            ReaderAttributes attributes = new ReaderAttributes();
            attributes.setReader(result.getUser());
            request.getSession().setAttribute("readerAttributes", attributes);

            logger.info("New reader with id = "+result.getUser().getId()+" was signed up in system");
        }

        response.setContentType("text/json; charset=utf-8");
        new ObjectMapper().writer().writeValue(response.getWriter(), operationResultMapper.toDTO(result, lang));
    }

    @HttpRequestMapping(path = "/sign-in", method = HttpMethod.POST)
    public void signIn(@HttpRequestParameter(name = "email", required = false) String email,
                       @HttpRequestParameter(name = "password", required = false) String password,
                       @HttpSessionAttribute(name = "lang") String lang,
                       HttpServletRequest request, HttpServletResponse response) throws Exception {

        logger.debug("Call sign in in authentication controller with email = "+email+" and password = "+password);

        UserFindResult result = userService.findUserByEmailAndPassword(email, password);

        if (result.getSuccess()) {
            if (result.getUser().getRole() == UserRole.ADMIN) {
                AdminAttributes attributes = new AdminAttributes();
                attributes.setAdmin(result.getUser());
                request.getSession().removeAttribute("guestAttributes");
                request.getSession().setAttribute("adminAttributes", attributes);

                logger.info("Admin with id = "+result.getUser().getId()+" was logged in system");
            } else if (result.getUser().getRole() == UserRole.LIBRARIAN) {
                LibrarianAttributes attributes = new LibrarianAttributes();
                attributes.setLibrarian(result.getUser());
                request.getSession().removeAttribute("guestAttributes");
                request.getSession().setAttribute("librarianAttributes", attributes);

                logger.info("Librarian with id = "+result.getUser().getId()+" was logged in system");
            } else {
                ReaderAttributes attributes = new ReaderAttributes();
                attributes.setReader(result.getUser());
                request.getSession().removeAttribute("guestAttributes");
                request.getSession().setAttribute("readerAttributes", attributes);

                logger.info("Reader with id = "+result.getUser().getId()+" was logged in system");
            }
        }

        response.setContentType("text/json; charset=utf-8");
        new ObjectMapper().writer().writeValue(response.getWriter(), operationResultMapper.toDTO(result, lang));
    }

    @HttpRequestMapping(path = "/logout", method = HttpMethod.POST)
    public void logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.debug("Call logout in authentication controller");

        request.getSession().invalidate();

        response.sendRedirect(request.getContextPath()+"/guest/home-page/show");
    }



    @HttpRequestMapping(path = "/access-link/send", method = HttpMethod.POST)
    public void sendAccessLink(@HttpRequestParameter(name = "email", required = false) String email,
                               @HttpRequestParameter(name = "password", required = false) String password,
                               @HttpSessionAttribute(name = "guestAttributes") GuestAttributes attributes,
                               @HttpSessionAttribute(name = "lang") String lang,
                               HttpServletRequest request, HttpServletResponse response) throws Exception {

        logger.debug("Call send access link in authentication controller of user with email = "+email+" and with new password = "+password);

        String url = request.getRequestURL().toString();
        String path = url.substring(0, url.length()-request.getRequestURI().length()+request.getContextPath().length());

        SendAccessLinkResult result = userService.sendAccessLink(email, password, path, lang);

        if (result.getSuccess()) {
            attributes.setAccessEmail(email);
            attributes.setAccessPasswordHash(cryptographicUtil.generateHash(password));
            attributes.setAccessCodeHash(result.getAccessCodeHash());

            logger.debug("Send letter successfully to email = "+attributes.getAccessEmail()+", password hash = "+attributes.getAccessPasswordHash()+", access code hash = "+attributes.getAccessCodeHash());
        }

        response.setContentType("text/json; charset=utf-8");
        new ObjectMapper().writer().writeValue(response.getWriter(), operationResultMapper.toDTO(result, lang));
    }

    @HttpRequestMapping(path = "/access/get", method = HttpMethod.GET)
    public void getAccess(@HttpRequestParameter(name = "accessCode") String accessCode,
                          @HttpSessionAttribute(name = "guestAttributes") GuestAttributes attributes,
                          HttpServletRequest request, HttpServletResponse response) throws DatabaseException, IOException {

        logger.debug("Get access called in authentication controller with access code = "+accessCode+" (hash = "+cryptographicUtil.generateHash(accessCode)+")");

        if (cryptographicUtil.generateHash(accessCode).equals(attributes.getAccessCodeHash())) {
            User user = userService.findUserByEmail(attributes.getAccessEmail()).orElseThrow();
            user.setPasswordHash(attributes.getAccessPasswordHash());
            if (user.getRole() == UserRole.ADMIN) {
                AdminAttributes adminAttributes = new AdminAttributes();
                adminAttributes.setAdmin(user);
                request.getSession().setAttribute("adminAttributes", adminAttributes);

                logger.info("Admin with id = "+user.getId()+" was logged in system");

                response.sendRedirect(request.getContextPath()+"/admin/books/show");
            } else if (user.getRole() == UserRole.LIBRARIAN) {
                LibrarianAttributes librarianAttributes = new LibrarianAttributes();
                librarianAttributes.setLibrarian(user);
                request.getSession().setAttribute("librarianAttributes", librarianAttributes);

                logger.info("Librarian with id = "+user.getId()+" was logged in system");

                response.sendRedirect(request.getContextPath()+"/librarian/orders/show");
            } else {
                ReaderAttributes readerAttributes = new ReaderAttributes();
                readerAttributes.setReader(user);
                request.getSession().setAttribute("readerAttributes", readerAttributes);

                logger.info("Reader with id = "+user.getId()+" was logged in system");

                response.sendRedirect(request.getContextPath()+"/reader/books/show");
            }
        } else {
            logger.error("Access link is inactive");

            response.sendRedirect(request.getContextPath()+"/error?code=401&description=Unauthorized");
        }
    }
}
