package app.controller;

import app.entity.Book;
import app.service.entity.BookService;
import app.service.entity.FeedbackService;
import app.service.file.FileService;
import app.service.mapper.BookMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doReturn;

public class BookControllerTest {
    FileService fileService;
    BookService bookService;
    FeedbackService feedbackService;
    BookMapper bookMapper;

    String lang;

    HttpServletRequest request;
    HttpServletResponse response;

    BookController bookController;

    @BeforeEach
    public void initialize() throws Exception {
        fileService = mock(FileService.class);
        bookService = mock(BookService.class);
        feedbackService = mock(FeedbackService.class);
        bookMapper = mock(BookMapper.class);

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);

        bookController = new BookController(fileService, bookService, feedbackService, bookMapper);

        ServletContext servletContext = mock(ServletContext.class);
        doReturn(mock(RequestDispatcher.class)).when(servletContext).getRequestDispatcher(anyString());
        doReturn(servletContext).when(request).getServletContext();
        doReturn("/EPAM_Servlet").when(request).getContextPath();
        doReturn(mock(ServletOutputStream.class)).when(response).getOutputStream();
        doReturn(mock(PrintWriter.class)).when(response).getWriter();
    }

    @Test
    public void getBookDetailsTest() throws Exception {
        doReturn(Optional.of(mock(Book.class))).when(bookService).findBookById(eq(4));

        bookController.getBookDetails(4, lang, response);

        verify(bookService, only()).findBookById(eq(4));
    }

    @Test
    public void getBookContentTest() throws Exception {
        doReturn(new byte[]{1, 2, 3, 4}).when(fileService).getBookContentFileAsBytes(4);

        bookController.getBookContent(4, response);

        verify(fileService, only()).getBookContentFileAsBytes(eq(4));
    }

    @Test
    public void getBookCoverTest() throws Exception {
        doReturn(new byte[]{1, 2, 3, 4}).when(fileService).getBookCoverImageFileAsBytes(4);

        bookController.getBookCover(4, response);

        verify(fileService, only()).getBookCoverImageFileAsBytes(eq(4));
    }
}
