package app.controller;

import app.entity.User;
import app.enums.UserRole;
import app.service.entity.BookService;
import app.service.entity.UserService;
import app.service.mapper.BookMapper;
import app.service.mapper.OperationResultMapper;
import app.service.mapper.UserMapper;
import app.service.other.MailService;
import app.service.utils.PaginationUtil;
import app.userdata.AdminAttributes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AdminControllerTest {
    OperationResultMapper operationResultMapper;
    BookMapper bookMapper;
    UserMapper userMapper;
    BookService bookService;
    UserService userService;
    MailService mailService;
    PaginationUtil paginationUtil;

    AdminAttributes attributes;
    String lang;

    HttpServletRequest request;
    HttpServletResponse response;

    AdminController adminController;

    @BeforeEach
    public void initialize() throws SQLException {
        operationResultMapper = mock(OperationResultMapper.class);
        bookMapper = mock(BookMapper.class);
        userMapper = mock(UserMapper.class);
        bookService = mock(BookService.class);
        userService = mock(UserService.class);
        mailService = mock(MailService.class);
        paginationUtil = mock(PaginationUtil.class);

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);

        attributes = spy(new AdminAttributes());
        attributes.setAdmin(User.builder().id(1).firstname("Mark").lastname("Brown").email("admin@gmail.com").passwordHash("mvFbM25qlhmShTffMLLmojdlafz51+dz7M7eZWBlKaA=").role(UserRole.ADMIN).build());

        adminController = new AdminController(operationResultMapper, bookMapper, userMapper, bookService, userService, mailService, paginationUtil);

        ServletContext servletContext = mock(ServletContext.class);
        doReturn(mock(RequestDispatcher.class)).when(servletContext).getRequestDispatcher(anyString());
        doReturn(servletContext).when(request).getServletContext();
        doReturn("/EPAM_Servlet").when(request).getContextPath();
    }

    @Test
    public void showBooksTest() throws Exception {
        attributes.setBooksAdvancedSearchEnabled(false);
        doReturn(20).when(bookService).countBooks((String)null);
        doReturn(mock(List.class)).when(bookService).findBooks4(eq(null), eq(1), eq(8));

        adminController.showBooks(1, 8, lang, attributes, request, response);

        verify(bookService, times(1)).countBooks((String)null);
        verify(bookService, times(1)).findBooks4(eq(null), eq(1), eq(8));
    }

    @Test
    public void showReadersTest() throws Exception {
        User user = mock(User.class);
        doReturn(20).when(userService).countReaders(eq("q"), eq(false), eq(true));
        doReturn(List.of(user, user, user)).when(userService).findReaders(eq("q"), eq(false), eq(true), eq(1), eq(8));

        attributes.setReadersQuery("q");
        attributes.setReadersBlockedOnly(false);
        attributes.setReadersWithFineOnly(true);

        adminController.showReaders(1, 8, attributes, request, response);

        verify(userService, times(3)).recalculateReaderFine(any());
        verify(userService, times(1)).countReaders(eq("q"), eq(false), eq(true));
        verify(userService, times(1)).findReaders(eq("q"), eq(false), eq(true), eq(1), eq(8));
    }

    @Test
    public void showLibrariansTest() throws Exception {
        doReturn(20).when(userService).countLibrarians(eq("q"));
        doReturn(mock(List.class)).when(userService).findLibrarians(eq("q"), eq(1), eq(10));

        attributes.setLibrariansQuery("q");

        adminController.showLibrarians(1, 10, attributes, request, response);

        verify(userService, times(1)).countLibrarians(eq("q"));
        verify(userService, times(1)).findLibrarians(eq("q"), eq(1), eq(10));
    }

    @Test
    public void deleteBookTest() throws Exception {
        adminController.deleteBook(1, attributes, request, response);

        verify(bookService, only()).deleteBook(eq(1));
    }

    @Test
    public void blockReaderTest() throws Exception {
        adminController.blockReader(1, attributes, request, response);

        verify(userService, only()).blockReader(eq(1));
    }

    @Test
    public void unblockReaderTest() throws Exception {
        adminController.unblockReader(1, attributes, request, response);

        verify(userService, only()).unblockReader(eq(1));
    }

    @Test
    public void deleteLibrarianTest() throws Exception {
        adminController.deleteLibrarian(1, attributes, request, response);

        verify(userService, only()).deleteLibrarian(eq(1));
    }
}
