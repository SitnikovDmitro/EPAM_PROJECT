package app.web;


import app.annotations.HttpRequestMapping;
import app.annotations.HttpRequestParameter;
import app.annotations.HttpSessionAttribute;
import app.controller.*;
import app.database.dao.*;
import app.database.dao.impl.*;
import app.database.initialization.DatabaseInitializer;
import app.database.pool.ConnectionPool;
import app.database.pool.impl.H2ConnectionPoolImpl;

import app.database.transaction.TransactionManager;
import app.database.transaction.impl.TransactionManagerImpl;
import app.enums.HttpMethod;
import app.exceptions.InitializationError;
import app.service.entity.*;
import app.service.entity.impl.*;
import app.service.file.FileService;
import app.service.file.impl.DatabaseFileServiceImpl;
import app.service.mapper.*;
import app.service.mapper.impl.BookMapperImpl;
import app.service.mapper.impl.OperationResultMapperImpl;
import app.service.mapper.impl.OrderMapperImpl;
import app.service.mapper.impl.UserMapperImpl;
import app.service.other.*;

import app.service.other.impl.CaptchaVerificationServiceImpl;
import app.service.other.impl.LocalizatorImpl;
import app.service.other.impl.MailServiceImpl;
import app.service.other.impl.XlsxGenerationServiceImpl;
import app.service.utils.CryptographicUtil;
import app.service.utils.EnumerationUtil;
import app.service.utils.FormatUtil;
import app.service.utils.PaginationUtil;
import app.service.utils.impl.CryptographicUtilImpl;
import app.service.utils.impl.EnumerationUtilImpl;
import app.service.utils.impl.FormatUtilImpl;
import app.service.utils.impl.PaginationUtilImpl;
import app.service.validation.Validator;
import app.service.validation.impl.ValidatorImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;


/**
 * Application web front controller (servlet-dispatcher)
 *
 * Initializes application components
 *
 * Redirects http requests to the controller method marked with a
 * {@link HttpRequestMapping} annotation
 *
 * Extracts http request parameters and session attributes and substitute
 * them into controller method arguments marked with a {@link HttpRequestParameter}
 * and {@link HttpSessionAttribute} annotations
 **/
public class DispatcherServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(DispatcherServlet.class);

    private List<Object> controllers;

    //Initialize components
    @Override
    public void init() {
        try {
            //Connection pool and database initialization
            ConnectionPool connectionPool = H2ConnectionPoolImpl.getInstance();
            DatabaseInitializer.getInstance().initializeWithBookData();
            TransactionManager transactionManager = new TransactionManagerImpl(connectionPool);

            //DAO initialization
            BookDAO bookDAO = new BookDAOImpl(connectionPool);
            BookmarkDAO bookmarkDAO = new BookmarkDAOImpl(connectionPool);
            FeedbackDAO feedbackDAO = new FeedbackDAOImpl(connectionPool);
            OrderDAO orderDAO = new OrderDAOImpl(connectionPool);
            UserDAO userDAO = new UserDAOImpl(connectionPool);
            PublisherDAO publisherDAO = new PublisherDAOImpl(connectionPool);
            BookCoverDAO bookCoverDAO = new BookCoverDAOImpl(connectionPool);
            BookContentDAO bookContentDAO = new BookContentDAOImpl(connectionPool);

            //Services initialization
            CryptographicUtil cryptographicUtil = new CryptographicUtilImpl();
            EnumerationUtil enumerationUtil = new EnumerationUtilImpl();
            FormatUtil formatUtil = new FormatUtilImpl();
            PaginationUtil paginationUtil = new PaginationUtilImpl();

            Localizator localizator = LocalizatorImpl.getInstance();

            BookMapper bookMapper = new BookMapperImpl(localizator, formatUtil, enumerationUtil);
            UserMapper userMapper = new UserMapperImpl(formatUtil);
            OrderMapper orderMapper = new OrderMapperImpl(localizator, enumerationUtil);
            OperationResultMapper operationResultMapper = new OperationResultMapperImpl(localizator);

            CaptchaVerificationService captchaVerificationService = new CaptchaVerificationServiceImpl();
            FileService fileService = new DatabaseFileServiceImpl(bookCoverDAO, bookContentDAO);
            Validator validator = new ValidatorImpl(bookDAO, userDAO, captchaVerificationService);
            XlsxGenerationService xlsxGenerationService = new XlsxGenerationServiceImpl(userDAO, bookDAO, orderDAO, formatUtil, localizator);
            MailService mailService = new MailServiceImpl(formatUtil, localizator);

            BookService bookService = new BookServiceImpl(bookDAO, publisherDAO, fileService, validator, transactionManager);
            UserService userService = new UserServiceImpl(userDAO, validator, cryptographicUtil, mailService, transactionManager);
            OrderService orderService = new OrderServiceImpl(userDAO, bookDAO, orderDAO, transactionManager);
            FeedbackService feedbackService = new FeedbackServiceImpl(userDAO, bookDAO, feedbackDAO, transactionManager);
            BookmarkService bookmarkService = new BookmarkServiceImpl(bookmarkDAO, transactionManager);
            PublisherService publisherService = new PublisherServiceImpl(publisherDAO);

            //Controllers initialization
            AdminController adminController = new AdminController(operationResultMapper, bookMapper, userMapper, bookService, userService, mailService, paginationUtil);
            ReaderController readerController = new ReaderController(operationResultMapper, userService, bookService, orderService, feedbackService, bookmarkService, bookMapper, mailService, orderMapper, paginationUtil, formatUtil);
            LibrarianController librarianController = new LibrarianController(bookService, userService, orderService, bookMapper, userMapper, orderMapper, paginationUtil, xlsxGenerationService);
            GuestController guestController = new GuestController(bookMapper, bookService, paginationUtil);
            BookController bookController = new BookController(fileService, bookService, feedbackService, bookMapper);
            ErrorController errorController = new ErrorController();
            AuthenticationController authenticationController = new AuthenticationController(operationResultMapper, userService, cryptographicUtil);
            LocalizationController localizationController = new LocalizationController();
            WelcomeController welcomeController = new WelcomeController();
            PublisherController publisherController = new PublisherController(publisherService);

            controllers = List.of(adminController, readerController, bookController, librarianController, guestController, errorController, authenticationController, localizationController, welcomeController, publisherController);
        } catch (Throwable e) {
            logger.log(Level.FATAL, "Components initialization failed");
            e.printStackTrace();
            throw new InitializationError("Components initialization failed", e);
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        dispatch(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        dispatch(request, response);
    }

    protected void dispatch(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=utf-8");

        try {
            for (Object controller : controllers) {
                for (Method method : controller.getClass().getMethods()) {
                    if (matches(request, method)) {
                        execute(request, response, method, controller);
                        return;
                    }
                }
            }
            logger.log(Level.ERROR, "Http request mapping not found for url "+request.getRequestURI().substring(request.getContextPath().length()));
            response.sendRedirect(request.getContextPath()+"/error?code=404&description=Request not found");
        } catch (Exception exception) {
            logger.log(Level.ERROR, "Server error happened", exception);
            response.sendRedirect(request.getContextPath()+"/error?code=500&description=Server error");
        }
    }

    //Checks if given controller method matches request
    private boolean matches(HttpServletRequest request, Method method) {
        if (!method.isAnnotationPresent(HttpRequestMapping.class)) return false;
        HttpRequestMapping mapping = method.getAnnotation(HttpRequestMapping.class);
        if (!request.getRequestURI().startsWith(request.getContextPath())) throw new AssertionError("Invalid URI");
        return mapping.path().equals(request.getRequestURI().substring(request.getContextPath().length()));
    }

    //Invokes controller method
    private void execute(HttpServletRequest request, HttpServletResponse response, Method method, Object controller) throws InvocationTargetException, IllegalAccessException, IOException {
        HttpRequestMapping mapping = method.getAnnotation(HttpRequestMapping.class);
        if ((request.getMethod().equalsIgnoreCase("get") && !mapping.method().equals(HttpMethod.GET)) ||
             request.getMethod().equalsIgnoreCase("post") && !mapping.method().equals(HttpMethod.POST)) {

            logger.log(Level.ERROR, "Method not allowed: expected "+request.getMethod().toUpperCase()+" but actual is "+mapping.method());
            response.sendRedirect(request.getContextPath()+"/error?code=405&description=Method not allowed");
            return;
        }

        Parameter[] parameters = method.getParameters();
        Object[] values = new Object[method.getParameterCount()];

        for (int i = 0; i < method.getParameterCount(); i++) {
            Parameter parameter = parameters[i];

            if (parameter.isAnnotationPresent(HttpRequestParameter.class)) {
                HttpRequestParameter requestParameter = parameter.getAnnotation(HttpRequestParameter.class);
                String value = request.getParameter(requestParameter.name());

                if (value != null && value.isBlank()) {
                    value = null;
                }

                if (value == null && !requestParameter.defaultValue().equals("DEFAULT")) {
                    value = requestParameter.defaultValue();
                }

                if (value == null && requestParameter.required()) {
                    throw new RuntimeException("Required request "+requestParameter.name()+" parameter is empty");
                }

                try {
                    if (value == null) {
                        values[i] = null;
                    } else if (parameter.getType().equals(String.class)) {
                        values[i] = value;
                    } else if (parameter.getType().equals(Boolean.class)) {
                        values[i] = Boolean.parseBoolean(value);
                    } else if (parameter.getType().equals(Integer.class)) {
                        values[i] = Integer.parseInt(value);
                    } else if (parameter.getType().equals(LocalDateTime.class)) {
                        values[i] = LocalDateTime.parse(value);
                    } else if (parameter.getType().equals(LocalDate.class)) {
                        values[i] = LocalDate.parse(value);
                    } else {
                        throw new RuntimeException("Impossible to convert request parameter "+requestParameter.name()+" with value "+value+" to specified type (" + parameter.getType().getName() + ")");
                    }
                } catch (NumberFormatException | DateTimeParseException exception) {
                    throw new RuntimeException("Impossible to convert request parameter "+requestParameter.name()+" with value "+value+" to specified type (" + parameter.getType().getName() + ")", exception);
                }
            } else if (parameter.isAnnotationPresent(HttpSessionAttribute.class)) {
                HttpSessionAttribute sessionAttribute = parameter.getAnnotation(HttpSessionAttribute.class);
                Object value = request.getSession().getAttribute(sessionAttribute.name());
                if (value == null && sessionAttribute.required()) throw new RuntimeException("Required session attribute "+sessionAttribute.name()+" is empty");

                if (value == null || parameter.getType().equals(value.getClass())) {
                    values[i] = value;
                } else {
                    throw new RuntimeException("Impossible to convert session attribute "+sessionAttribute.name()+" with value "+value+" to specified type (" + parameter.getType().getName() + ")");
                }
            } else if (parameter.getType().equals(HttpServletRequest.class)) {
                values[i] = request;
            } else if (parameter.getType().equals(HttpServletResponse.class)) {
                values[i] = response;
            } else {
                throw new RuntimeException("Unknown parameter "+parameter.getName());
            }
        }

        method.invoke(controller, values);
    }
}