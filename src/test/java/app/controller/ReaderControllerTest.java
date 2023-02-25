package app.controller;

import app.entity.User;
import app.enums.UserRole;
import app.service.entity.*;
import app.service.mapper.BookMapper;
import app.service.mapper.OperationResultMapper;
import app.service.mapper.OrderMapper;
import app.service.mapper.UserMapper;
import app.service.other.MailService;
import app.service.utils.FormatUtil;
import app.service.utils.PaginationUtil;
import app.userdata.AdminAttributes;
import app.userdata.ReaderAttributes;
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

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ReaderControllerTest {
    OperationResultMapper operationResultMapper;
    UserService userService;
    BookService bookService;
    OrderService orderService;
    FeedbackService feedbackService;
    BookmarkService bookmarkService;
    BookMapper bookMapper;
    MailService mailService;
    OrderMapper orderMapper;
    PaginationUtil paginationUtil;
    FormatUtil formatUtil;

    ReaderAttributes attributes;
    String lang;

    HttpServletRequest request;
    HttpServletResponse response;

    ReaderController readerController;

    @BeforeEach
    public void initialize() throws SQLException {
        operationResultMapper = mock(OperationResultMapper.class);
        userService = mock(UserService.class);
        bookService = mock(BookService.class);
        orderService = mock(OrderService.class);
        feedbackService = mock(FeedbackService.class);
        bookmarkService = mock(BookmarkService.class);
        bookMapper = mock(BookMapper.class);
        mailService = mock(MailService.class);
        orderMapper = mock(OrderMapper.class);
        paginationUtil = mock(PaginationUtil.class);
        formatUtil = mock(FormatUtil.class);

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);

        attributes = spy(new ReaderAttributes());
        attributes.setReader(User.builder().id(2).firstname("Alla").lastname("Smith").email("reader@gmail.com").passwordHash("mvFbM25qlhmShTffMLLmojdlafz51+dz7M7eZWBlKaA=").role(UserRole.ADMIN).build());

        readerController = new ReaderController(operationResultMapper, userService, bookService, orderService, feedbackService, bookmarkService, bookMapper, mailService, orderMapper, paginationUtil, formatUtil);

        ServletContext servletContext = mock(ServletContext.class);
        doReturn(mock(RequestDispatcher.class)).when(servletContext).getRequestDispatcher(anyString());
        doReturn(servletContext).when(request).getServletContext();
        doReturn("/EPAM_Servlet").when(request).getContextPath();
    }

    @Test
    public void showProfileTest() throws Exception {
        readerController.showProfile(attributes, request, response);

        verify(userService, only()).refreshReader(any());
    }

    @Test
    public void createFeedbackTest() throws Exception {
        readerController.createFeedback(1, 5, "Good book", attributes, request, response);

        verify(feedbackService, only()).createFeedback(eq(1), eq(2), eq("Good book"), eq(5));
    }

    @Test
    public void addBookToBookmarksTest() throws Exception {
        readerController.addBookToBookmarks(4, attributes, request, response);

        verify(bookmarkService, only()).saveBookmarkByUserIdAndBookId(eq(2), eq(4));
    }

    @Test
    public void deleteBookFromBookmarksTest() throws Exception {
        readerController.deleteBookFromBookmarks(4, attributes, request, response);

        verify(bookmarkService, only()).deleteBookmarkByUserIdAndBookId(eq(2), eq(4));
    }

    @Test
    public void cancelOrder() throws Exception {
        readerController.cancelOrder(12, attributes, request, response);

        verify(orderService, only()).cancelOrder(eq(12));
    }

    @Test
    public void deleteOrderTest() throws Exception {
        readerController.deleteOrder(12, attributes, request, response);

        verify(orderService, only()).deleteOrder(eq(12));
    }

    @Test
    public void createOrderTest() throws Exception {
        readerController.createOrder(4, attributes, request, response);

        verify(orderService, only()).createOrder(eq(4), eq(2));
    }
}
