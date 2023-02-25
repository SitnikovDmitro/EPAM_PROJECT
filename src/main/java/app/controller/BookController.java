package app.controller;


import app.annotations.HttpSessionAttribute;
import app.dto.BookDetailedDTO;
import app.entity.Book;
import app.entity.Feedback;
import app.enums.HttpMethod;
import app.service.mapper.BookMapper;
import app.annotations.HttpRequestMapping;
import app.annotations.HttpRequestParameter;
import app.service.entity.BookService;
import app.service.entity.FeedbackService;
import app.service.file.FileService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


public class BookController {
    private static final Logger logger = LogManager.getLogger(BookController.class);

    private final FileService fileService;
    private final BookService bookService;
    private final FeedbackService feedbackService;
    private final BookMapper bookMapper;

    public BookController(FileService fileService, BookService bookService, FeedbackService feedbackService, BookMapper bookMapper) {
        this.fileService = fileService;
        this.bookService = bookService;
        this.feedbackService = feedbackService;
        this.bookMapper = bookMapper;
    }

    @HttpRequestMapping(path = "/book/details", method = HttpMethod.GET)
    public void getBookDetails(@HttpRequestParameter(name = "bookId") Integer bookId,
                               @HttpSessionAttribute(name = "lang") String lang,
                               HttpServletResponse response) throws Exception {

        logger.debug("Call get book details (in json format) in book controller with id = "+bookId);

        response.setContentType("text/json; charset=utf-8");
        Book book = bookService.findBookById(bookId).orElseThrow();
        List<Feedback> feedbacks = feedbackService.findFeedbacksByBookId(book.getId());
        BookDetailedDTO dto = bookMapper.toDetailedDTO(book, feedbacks, false, false, lang);
        new ObjectMapper().writer().writeValue(response.getWriter(), dto);
    }

    @HttpRequestMapping(path = "/book/content", method = HttpMethod.GET)
    public void getBookContent(@HttpRequestParameter(name = "bookId") Integer bookId,
                             HttpServletResponse response) throws Exception {

        logger.debug("Call get book content (in pdf format) in book controller with id = "+bookId);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"Book.pdf\"");
        byte[] bytes = fileService.getBookContentFileAsBytes(bookId);

        ServletOutputStream out = response.getOutputStream();
        out.write(bytes);
    }

    @HttpRequestMapping(path = "/book/cover", method = HttpMethod.GET)
    public void getBookCover(@HttpRequestParameter(name = "bookId") Integer bookId,
                             HttpServletResponse response) throws Exception {

        response.setContentType("image/png");
        response.setHeader("Content-Disposition", "attachment; filename=\"Icon.png\"");
        byte[] bytes = fileService.getBookCoverImageFileAsBytes(bookId);

        ServletOutputStream out = response.getOutputStream();
        out.write(bytes);
    }
}
