package app.controller;

import app.entity.User;
import app.enums.UserRole;
import app.service.entity.BookService;
import app.service.entity.OrderService;
import app.service.entity.UserService;
import app.service.mapper.BookMapper;
import app.service.mapper.OperationResultMapper;
import app.service.mapper.OrderMapper;
import app.service.mapper.UserMapper;
import app.service.other.MailService;
import app.service.other.XlsxGenerationService;
import app.service.utils.PaginationUtil;
import app.userdata.AdminAttributes;
import app.userdata.LibrarianAttributes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doReturn;

public class LibrarianControllerTest {
    BookService bookService;
    UserService userService;
    OrderService orderService;
    BookMapper bookMapper;
    UserMapper userMapper;
    OrderMapper orderMapper;
    PaginationUtil paginationUtil;
    XlsxGenerationService xlsxGenerationService;

    LibrarianAttributes attributes;
    String lang;

    HttpServletRequest request;
    HttpServletResponse response;

    LibrarianController librarianController;


    @BeforeEach
    public void initialize() throws Exception {
        bookService = mock(BookService.class);
        userService = mock(UserService.class);
        orderService = mock(OrderService.class);
        bookMapper = mock(BookMapper.class);
        userMapper = mock(UserMapper.class);
        orderMapper = mock(OrderMapper.class);
        paginationUtil = mock(PaginationUtil.class);
        xlsxGenerationService = mock(XlsxGenerationService.class);

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);

        attributes = spy(new LibrarianAttributes());
        attributes.setLibrarian(User.builder().id(1).firstname("Matt").lastname("Black").email("librarian@gmail.com").passwordHash("mvFbM25qlhmShTffMLLmojdlafz51+dz7M7eZWBlKaA=").role(UserRole.LIBRARIAN).build());

        librarianController = new LibrarianController(bookService, userService, orderService, bookMapper, userMapper, orderMapper, paginationUtil, xlsxGenerationService);

        ServletContext servletContext = mock(ServletContext.class);
        doReturn(mock(RequestDispatcher.class)).when(servletContext).getRequestDispatcher(anyString());
        doReturn(servletContext).when(request).getServletContext();
        doReturn("/EPAM_Servlet").when(request).getContextPath();
        doReturn(mock(ServletOutputStream.class)).when(response).getOutputStream();
    }

    @Test
    public void deleteOrderTest() throws Exception {
        librarianController.confirmOrder(12, null, attributes, request, response);

        verify(orderService, only()).confirmOrder(eq(12));
    }

    @Test
    public void closeOrderTest() throws Exception {
        librarianController.closeOrder(12, attributes, request, response);

        verify(orderService, only()).closeOrder(eq(12));
    }

    @Test
    public void cancelOrderTest() throws Exception {
        librarianController.cancelOrder(12, attributes, request, response);

        verify(orderService, only()).cancelOrder(eq(12));
    }

    @Test
    public void generateBooksInformationTableTest() throws Exception {
        librarianController.generateBooksInformationTable(lang, response);

        verify(xlsxGenerationService, only()).generateBookDataXlsxTable(any());
    }

    @Test
    public void generateReadersInformationTableTest() throws Exception {
        librarianController.generateReadersInformationTable(lang, response);

        verify(xlsxGenerationService, only()).generateReaderDataXlsxTable(any());
    }
}
