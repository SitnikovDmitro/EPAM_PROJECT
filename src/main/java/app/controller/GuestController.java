package app.controller;

import app.annotations.HttpRequestMapping;
import app.annotations.HttpRequestParameter;
import app.annotations.HttpSessionAttribute;
import app.constants.PaginationConstants;
import app.entity.Book;
import app.enums.*;
import app.service.mapper.BookMapper;
import app.service.entity.BookService;
import app.service.utils.PaginationUtil;
import app.userdata.GuestAttributes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class GuestController {
    private static final Logger logger = LogManager.getLogger(GuestController.class);

    private final BookMapper bookMapper;
    private final BookService bookService;
    private final PaginationUtil paginationUtil;

    public GuestController(BookMapper bookMapper, BookService bookService, PaginationUtil paginationUtil) {
        this.bookMapper = bookMapper;
        this.bookService = bookService;
        this.paginationUtil = paginationUtil;
    }

    @HttpRequestMapping(path = "/guest/home-page/show", method = HttpMethod.GET)
    public void showHomePage(HttpServletRequest request, HttpServletResponse response) throws Exception {

        logger.debug("Call show home page in guest controller");

        request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/guest/welcome.jsp").forward(request, response);
    }

    @HttpRequestMapping(path = "/guest/books/show", method = HttpMethod.GET)
    public void showBooks(@HttpRequestParameter(name = "page", defaultValue = "1") Integer page,
                          @HttpRequestParameter(name = "size", defaultValue = PaginationConstants.BOOKS_PER_PAGE_STR) Integer size,
                          @HttpSessionAttribute(name = "guestAttributes") GuestAttributes attributes,
                          @HttpSessionAttribute(name = "lang") String lang,
                          HttpServletRequest request, HttpServletResponse response) throws Exception {

        logger.debug("Call show books in guest controller with page = "+page);

        List<Book> books;

        if (attributes.getBooksCount() == null) {
            if (attributes.isBooksAdvancedSearchEnabled()) {
                attributes.setBooksCount(bookService.countBooks(attributes.getBooksIsbn(), attributes.getBooksPublisherQuery(), attributes.getBooksTitleQuery(), attributes.getBooksAuthorQuery(), attributes.getBooksSortCriteria(), attributes.getBooksFormat(), attributes.getBooksGenre(), attributes.getBooksLanguage()));
            } else {
                attributes.setBooksCount(bookService.countBooks(attributes.getBooksQuery()));
            }
        }

        if (attributes.isBooksAdvancedSearchEnabled()) {
            books = bookService.findBooks2(attributes.getBooksIsbn(), attributes.getBooksPublisherQuery(), attributes.getBooksTitleQuery(), attributes.getBooksAuthorQuery(), attributes.getBooksSortCriteria(), attributes.getBooksFormat(), attributes.getBooksGenre(), attributes.getBooksLanguage(), page, size);
        } else {
            books = bookService.findBooks4(attributes.getBooksQuery(), page, size);
        }


        request.setAttribute("query", attributes.getBooksQuery());
        request.setAttribute("isbn", attributes.getBooksIsbn());
        request.setAttribute("publisherQuery", attributes.getBooksPublisherQuery());
        request.setAttribute("titleQuery", attributes.getBooksTitleQuery());
        request.setAttribute("authorQuery", attributes.getBooksAuthorQuery());
        request.setAttribute("sortCriteria", Objects.isNull(attributes.getBooksSortCriteria()) ? null : attributes.getBooksSortCriteria().toInt());
        request.setAttribute("format", Objects.isNull(attributes.getBooksFormat()) ? null : attributes.getBooksFormat().toInt());
        request.setAttribute("genre", Objects.isNull(attributes.getBooksGenre()) ? null : attributes.getBooksGenre().toInt());
        request.setAttribute("language", Objects.isNull(attributes.getBooksLanguage()) ? null : attributes.getBooksLanguage().toInt());
        request.setAttribute("advancedSearch", attributes.isBooksAdvancedSearchEnabled());
        request.setAttribute("books", books.stream().map(book -> bookMapper.toBriefDTO(book, lang)).collect(Collectors.toList()));
        request.setAttribute("pagination", paginationUtil.paginate(page, attributes.getBooksCount(), size));

        request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/guest/books.jsp").forward(request, response);
    }

    @HttpRequestMapping(path = "/guest/books/find/advanced", method = HttpMethod.POST)
    public void findBooks(@HttpRequestParameter(name = "isbn", required = false) Integer isbn,
                          @HttpRequestParameter(name = "publisherQuery", required = false) String publisherQuery,
                          @HttpRequestParameter(name = "titleQuery", required = false) String titleQuery,
                          @HttpRequestParameter(name = "authorQuery", required = false) String authorQuery,
                          @HttpRequestParameter(name = "sortCriteria", required = false) Integer sortCriteria,
                          @HttpRequestParameter(name = "format", required = false) Integer format,
                          @HttpRequestParameter(name = "genre", required = false) Integer genre,
                          @HttpRequestParameter(name = "language", required = false) Integer language,
                          @HttpSessionAttribute(name = "guestAttributes") GuestAttributes attributes,
                          HttpServletRequest request, HttpServletResponse response) throws Exception {

        logger.debug("Call find books (advanced) in guest controller");

        attributes.setBooksIsbn(isbn);
        attributes.setBooksPublisherQuery(publisherQuery);
        attributes.setBooksTitleQuery(titleQuery);
        attributes.setBooksAuthorQuery(authorQuery);
        attributes.setBooksSortCriteria(Objects.isNull(sortCriteria) ? null : BookSortCriteria.ofInt(sortCriteria));
        attributes.setBooksFormat(Objects.isNull(format) ? null : BookFormat.ofInt(format));
        attributes.setBooksGenre(Objects.isNull(genre) ? null : BookGenre.ofInt(genre));
        attributes.setBooksLanguage(Objects.isNull(language) ? null : BookLanguage.ofInt(language));
        attributes.setBooksAdvancedSearchEnabled(true);
        attributes.setBooksCount(null);

        response.sendRedirect(request.getContextPath()+"/guest/books/show");
    }

    @HttpRequestMapping(path = "/guest/books/find/basic", method = HttpMethod.POST)
    public void findBooks(@HttpRequestParameter(name = "query", required = false) String query,
                          @HttpSessionAttribute(name = "guestAttributes") GuestAttributes attributes,
                          HttpServletRequest request, HttpServletResponse response) throws Exception {

        logger.debug("Call find books (basic) in guest controller");

        attributes.setBooksQuery(query);
        attributes.setBooksAdvancedSearchEnabled(false);
        attributes.setBooksCount(null);

        response.sendRedirect(request.getContextPath()+"/guest/books/show");
    }
}
