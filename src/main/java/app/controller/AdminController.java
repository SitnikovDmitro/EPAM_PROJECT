package app.controller;

import app.constants.PaginationConstants;
import app.entity.Book;
import app.entity.User;
import app.enums.*;
import app.service.other.MailService;
import app.tuples.BookCreationEditionResult;
import app.tuples.UserCreationEditionResult;
import app.service.mapper.BookMapper;
import app.service.mapper.OperationResultMapper;
import app.service.mapper.UserMapper;
import app.service.entity.BookService;
import app.annotations.HttpRequestMapping;
import app.annotations.HttpRequestParameter;
import app.annotations.HttpSessionAttribute;
import app.service.entity.UserService;
import app.service.utils.PaginationUtil;
import app.userdata.AdminAttributes;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class AdminController {
    private static final Logger logger = LogManager.getLogger(AdminController.class);

    private final OperationResultMapper operationResultMapper;
    private final BookMapper bookMapper;
    private final UserMapper userMapper;
    private final BookService bookService;
    private final UserService userService;
    private final MailService mailService;
    private final PaginationUtil paginationUtil;

    public AdminController(OperationResultMapper operationResultMapper, BookMapper bookMapper, UserMapper userMapper, BookService bookService, UserService userService, MailService mailService, PaginationUtil paginationUtil) {
        this.operationResultMapper = operationResultMapper;
        this.bookMapper = bookMapper;
        this.userMapper = userMapper;
        this.bookService = bookService;
        this.userService = userService;
        this.mailService = mailService;
        this.paginationUtil = paginationUtil;
    }

    @HttpRequestMapping(path = "/admin/books/show", method = HttpMethod.GET)
    public void showBooks(@HttpRequestParameter(name = "page", defaultValue = "1") Integer page,
                          @HttpRequestParameter(name = "size", defaultValue = PaginationConstants.BOOKS_PER_PAGE_STR) Integer size,
                          @HttpSessionAttribute(name = "lang") String lang,
                          @HttpSessionAttribute(name = "adminAttributes") AdminAttributes attributes,
                          HttpServletRequest request, HttpServletResponse response) throws Exception {

        logger.debug("Call show books in admin controller with page = "+page);

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

        request.setAttribute("adminFullname", attributes.getAdmin().getFirstname()+" "+attributes.getAdmin().getLastname());
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

        request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/admin/books.jsp").forward(request, response);
    }

    @HttpRequestMapping(path = "/admin/readers/show", method = HttpMethod.GET)
    public void showReaders(@HttpRequestParameter(name = "page", defaultValue = "1") Integer page,
                            @HttpRequestParameter(name = "size", defaultValue = PaginationConstants.USERS_PER_PAGE_STR) Integer size,
                            @HttpSessionAttribute(name = "adminAttributes") AdminAttributes attributes,
                            HttpServletRequest request, HttpServletResponse response) throws Exception {

        logger.debug("Call show readers in admin controller with page = "+page);

        if (attributes.getReadersCount() == null) {
            attributes.setReadersCount(userService.countReaders(attributes.getReadersQuery(), attributes.isReadersBlockedOnly(), attributes.isReadersWithFineOnly()));
        }

        List<User> readers = userService.findReaders(attributes.getReadersQuery(), attributes.isReadersBlockedOnly(), attributes.isReadersWithFineOnly(), page, size);

        for (User reader : readers) {
            userService.recalculateReaderFine(reader);
        }

        request.setAttribute("adminFullname", attributes.getAdmin().getFirstname()+" "+attributes.getAdmin().getLastname());
        request.setAttribute("query", attributes.getReadersQuery());
        request.setAttribute("blockedOnly", attributes.isReadersBlockedOnly());
        request.setAttribute("withFineOnly", attributes.isReadersWithFineOnly());
        request.setAttribute("readers", readers.stream().map(userMapper::toReaderDTO).collect(Collectors.toList()));
        request.setAttribute("pagination", paginationUtil.paginate(page, attributes.getReadersCount(), size));

        request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/admin/readers.jsp").forward(request, response);
    }

    @HttpRequestMapping(path = "/admin/librarians/show", method = HttpMethod.GET)
    public void showLibrarians(@HttpRequestParameter(name = "page", defaultValue = "1") Integer page,
                               @HttpRequestParameter(name = "size", defaultValue = PaginationConstants.USERS_PER_PAGE_STR) Integer size,
                               @HttpSessionAttribute(name = "adminAttributes") AdminAttributes attributes,
                               HttpServletRequest request, HttpServletResponse response) throws Exception {

        logger.debug("Call show librarians in admin controller with page = "+page);

        if (attributes.getLibrariansCount() == null) {
            attributes.setLibrariansCount(userService.countLibrarians(attributes.getLibrariansQuery()));
        }

        List<User> librarians = userService.findLibrarians(attributes.getLibrariansQuery(), page, size);

        request.setAttribute("adminFullname", attributes.getAdmin().getFirstname()+" "+attributes.getAdmin().getLastname());
        request.setAttribute("query", attributes.getLibrariansQuery());
        request.setAttribute("librarians", librarians.stream().map(userMapper::toLibrarianDTO).collect(Collectors.toList()));
        request.setAttribute("pagination", paginationUtil.paginate(page, attributes.getLibrariansCount(), size));

        request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/admin/librarians.jsp").forward(request, response);
    }

    @HttpRequestMapping(path = "/admin/books/find/advanced", method = HttpMethod.POST)
    public void findBooks(@HttpRequestParameter(name = "isbn", required = false) Integer isbn,
                          @HttpRequestParameter(name = "publisherQuery", required = false) String publisherQuery,
                          @HttpRequestParameter(name = "titleQuery", required = false) String titleQuery,
                          @HttpRequestParameter(name = "authorQuery", required = false) String authorQuery,
                          @HttpRequestParameter(name = "sortCriteria", required = false) Integer sortCriteria,
                          @HttpRequestParameter(name = "format", required = false) Integer format,
                          @HttpRequestParameter(name = "genre", required = false) Integer genre,
                          @HttpRequestParameter(name = "language", required = false) Integer language,
                          @HttpSessionAttribute(name = "adminAttributes") AdminAttributes attributes,
                          HttpServletRequest request, HttpServletResponse response) throws Exception {

        logger.debug("Call find books (advanced) in admin controller");

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

        response.sendRedirect(request.getContextPath()+"/admin/books/show");
    }

    @HttpRequestMapping(path = "/admin/books/find/basic", method = HttpMethod.POST)
    public void findBooks(@HttpRequestParameter(name = "query", required = false) String query,
                          @HttpSessionAttribute(name = "adminAttributes") AdminAttributes attributes,
                          HttpServletRequest request, HttpServletResponse response) throws Exception {

        logger.debug("Call find books (basic) in admin controller");

        attributes.setBooksQuery(query);
        attributes.setBooksAdvancedSearchEnabled(false);
        attributes.setBooksCount(null);

        response.sendRedirect(request.getContextPath()+"/admin/books/show");
    }


    @HttpRequestMapping(path = "/admin/readers/find", method = HttpMethod.POST)
    public void findReaders(@HttpRequestParameter(name = "query", required = false) String query,
                            @HttpRequestParameter(name = "blockedOnly", defaultValue = "false") Boolean blockedOnly,
                            @HttpRequestParameter(name = "withFineOnly", defaultValue = "false") Boolean withFineOnly,
                            @HttpSessionAttribute(name = "adminAttributes") AdminAttributes attributes,
                            HttpServletRequest request, HttpServletResponse response) throws Exception {

        logger.debug("Call find readers in admin controller");

        attributes.setReadersQuery(query);
        attributes.setReadersBlockedOnly(blockedOnly);
        attributes.setReadersWithFineOnly(withFineOnly);
        attributes.setReadersCount(null);

        response.sendRedirect(request.getContextPath()+"/admin/readers/show");
    }


    @HttpRequestMapping(path = "/admin/librarians/find", method = HttpMethod.POST)
    public void findLibrarians(@HttpRequestParameter(name = "query", required = false) String query,
                               @HttpSessionAttribute(name = "adminAttributes") AdminAttributes attributes,
                               HttpServletRequest request, HttpServletResponse response) throws Exception {

        logger.debug("Call find librarians in admin controller");

        attributes.setLibrariansQuery(query);
        attributes.setLibrariansCount(null);

        response.sendRedirect(request.getContextPath()+"/admin/librarians/show");
    }





    @HttpRequestMapping(path = "/admin/book/create", method = HttpMethod.POST)
    public void createBook(@HttpRequestParameter(name = "isbn", required = false) String isbn,
                           @HttpRequestParameter(name = "title", required = false) String title,
                           @HttpRequestParameter(name = "author", required = false) String author,
                           @HttpRequestParameter(name = "genre", required = false) String genre,
                           @HttpRequestParameter(name = "language", required = false) String language,
                           @HttpRequestParameter(name = "description", required = false) String description,
                           @HttpRequestParameter(name = "publicationDate", required = false) String publicationDate,
                           @HttpRequestParameter(name = "totalCopiesNumber", required = false) String totalCopiesNumber,
                           @HttpRequestParameter(name = "isValuable", defaultValue = "false") Boolean isValuable,
                           @HttpRequestParameter(name = "publisherTitle", required = false) String publisherTitle,
                           @HttpSessionAttribute(name = "lang") String lang,
                           @HttpSessionAttribute(name = "adminAttributes") AdminAttributes attributes,
                           HttpServletRequest request, HttpServletResponse response) throws Exception {

        logger.debug("Call create book in admin controller");

        Part coverPart = request.getPart("cover");
        Part contentPart = request.getPart("content");
        byte[] cover = Objects.isNull(coverPart)?null:coverPart.getInputStream().readAllBytes();
        byte[] content = Objects.isNull(contentPart)?null:contentPart.getInputStream().readAllBytes();

        attributes.setBooksCount(null);

        BookCreationEditionResult result = bookService.createBook(isbn, title, author, description, publicationDate, totalCopiesNumber, genre, language, isValuable, publisherTitle, cover, content);
        response.setContentType("text/json; charset=utf-8");
        new ObjectMapper().writer().writeValue(response.getWriter(), operationResultMapper.toDTO(result, lang));
    }

    @HttpRequestMapping(path = "/admin/book/edit", method = HttpMethod.POST)
    public void editBook(@HttpRequestParameter(name = "id") Integer id,
                         @HttpRequestParameter(name = "isbn", required = false) String isbn,
                         @HttpRequestParameter(name = "title", required = false) String title,
                         @HttpRequestParameter(name = "author", required = false) String author,
                         @HttpRequestParameter(name = "genre", required = false) String genre,
                         @HttpRequestParameter(name = "language", required = false) String language,
                         @HttpRequestParameter(name = "description", required = false) String description,
                         @HttpRequestParameter(name = "publicationDate", required = false) String publicationDate,
                         @HttpRequestParameter(name = "totalCopiesNumber", required = false) String totalCopiesNumber,
                         @HttpRequestParameter(name = "publisherTitle", required = false) String publisherTitle,
                         @HttpSessionAttribute(name = "lang") String lang,
                         @HttpSessionAttribute(name = "adminAttributes") AdminAttributes attributes,
                         HttpServletRequest request, HttpServletResponse response) throws Exception {

        logger.debug("Call edit book in admin controller with id = "+id);

        Part coverPart = request.getPart("cover");
        Part contentPart = request.getPart("content");
        byte[] cover = Objects.isNull(coverPart)?null:coverPart.getInputStream().readAllBytes();
        byte[] content = Objects.isNull(contentPart)?null:contentPart.getInputStream().readAllBytes();

        attributes.setBooksCount(null);

        BookCreationEditionResult result = bookService.editBook(id, isbn, title, author, description, publicationDate, totalCopiesNumber, genre, language, publisherTitle, cover, content);
        response.setContentType("text/json; charset=utf-8");
        new ObjectMapper().writer().writeValue(response.getWriter(), operationResultMapper.toDTO(result, lang));
    }




    @HttpRequestMapping(path = "/admin/book/delete", method = HttpMethod.POST)
    public void deleteBook(@HttpRequestParameter(name = "bookId") Integer bookId,
                           @HttpSessionAttribute(name = "adminAttributes") AdminAttributes attributes,
                           HttpServletRequest request, HttpServletResponse response) throws Exception {

        logger.debug("Call find books in admin controller");

        bookService.deleteBook(bookId);
        attributes.setBooksCount(null);

        response.sendRedirect(request.getContextPath()+"/admin/books/show");
    }


    @HttpRequestMapping(path = "/admin/reader/block", method = HttpMethod.POST)
    public void blockReader(@HttpRequestParameter(name = "userId") Integer userId,
                            @HttpSessionAttribute(name = "adminAttributes") AdminAttributes attributes,
                            HttpServletRequest request, HttpServletResponse response) throws Exception {

        logger.debug("Call block reader in admin controller with id = "+userId);

        userService.blockReader(userId);
        attributes.setReadersCount(null);

        response.sendRedirect(request.getContextPath()+"/admin/readers/show");
    }


    @HttpRequestMapping(path = "/admin/reader/unblock", method = HttpMethod.POST)
    public void unblockReader(@HttpRequestParameter(name = "userId") Integer userId,
                              @HttpSessionAttribute(name = "adminAttributes") AdminAttributes attributes,
                              HttpServletRequest request, HttpServletResponse response) throws Exception {

        logger.debug("Call unblock reader in admin controller with id = "+userId);

        userService.unblockReader(userId);
        attributes.setReadersCount(null);

        response.sendRedirect(request.getContextPath()+"/admin/readers/show");
    }

    @HttpRequestMapping(path = "/admin/reader/fine/pay", method = HttpMethod.POST)
    public void payReaderFine(@HttpRequestParameter(name = "userId") Integer userId,
                              @HttpSessionAttribute(name = "lang") String lang,
                              @HttpSessionAttribute(name = "adminAttributes") AdminAttributes attributes,
                              HttpServletRequest request, HttpServletResponse response) throws Exception {

        logger.debug("Call pay reader fine in admin controller with id = "+userId);

        User user = userService.findReaderById(userId).orElseThrow();
        int fine = userService.payReaderFine(user.getId());
        mailService.sendFinePaymentMessageAsync(user.getEmail(), fine, lang);
        attributes.setReadersCount(null);

        response.sendRedirect(request.getContextPath()+"/admin/readers/show");
    }


    @HttpRequestMapping(path = "/admin/librarian/delete", method = HttpMethod.POST)
    public void deleteLibrarian(@HttpRequestParameter(name = "userId") Integer userId,
                                @HttpSessionAttribute(name = "adminAttributes") AdminAttributes attributes,
                                HttpServletRequest request, HttpServletResponse response) throws Exception {

        logger.debug("Call delete librarian in admin controller with id = "+userId);

        userService.deleteLibrarian(userId);
        attributes.setLibrariansCount(null);

        response.sendRedirect(request.getContextPath()+"/admin/librarians/show");
    }

    @HttpRequestMapping(path = "/admin/librarian/create", method = HttpMethod.POST)
    public void createLibrarian(@HttpRequestParameter(name = "firstname", required = false) String firstname,
                                @HttpRequestParameter(name = "lastname", required = false) String lastname,
                                @HttpRequestParameter(name = "email", required = false) String email,
                                @HttpRequestParameter(name = "password", required = false) String password,
                                @HttpSessionAttribute(name = "lang") String lang,
                                @HttpSessionAttribute(name = "adminAttributes") AdminAttributes attributes,
                                HttpServletResponse response) throws Exception {

        logger.debug("Call create librarian in admin controller");

        UserCreationEditionResult result = userService.createLibrarian(firstname, lastname, email, password);
        attributes.setLibrariansCount(null);

        response.setContentType("text/json; charset=utf-8");
        new ObjectMapper().writer().writeValue(response.getWriter(), operationResultMapper.toDTO(result, lang));
    }
}
