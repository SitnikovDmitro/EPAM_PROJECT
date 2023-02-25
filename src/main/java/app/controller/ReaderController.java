package app.controller;

import app.constants.PaginationConstants;
import app.dto.BookDetailedDTO;
import app.entity.Book;
import app.entity.Feedback;
import app.entity.Order;
import app.enums.*;
import app.service.other.MailService;
import app.tuples.UserCreationEditionResult;
import app.service.entity.*;
import app.service.mapper.BookMapper;
import app.service.mapper.OperationResultMapper;
import app.service.mapper.OrderMapper;
import app.annotations.HttpRequestMapping;
import app.annotations.HttpRequestParameter;
import app.annotations.HttpSessionAttribute;
import app.service.utils.FormatUtil;
import app.service.utils.PaginationUtil;
import app.userdata.ReaderAttributes;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class ReaderController {
    private static final Logger logger = LogManager.getLogger(ReaderController.class);

    private final OperationResultMapper operationResultMapper;
    private final UserService userService;
    private final BookService bookService;
    private final OrderService orderService;
    private final FeedbackService feedbackService;
    private final BookmarkService bookmarkService;
    private final BookMapper bookMapper;
    private final MailService mailService;
    private final OrderMapper orderMapper;
    private final PaginationUtil paginationUtil;
    private final FormatUtil formatUtil;

    public ReaderController(OperationResultMapper operationResultMapper, UserService userService, BookService bookService, OrderService orderService, FeedbackService feedbackService, BookmarkService bookmarkService, BookMapper bookMapper, MailService mailService, OrderMapper orderMapper, PaginationUtil paginationUtil, FormatUtil formatUtil) {
        this.operationResultMapper = operationResultMapper;
        this.userService = userService;
        this.bookService = bookService;
        this.orderService = orderService;
        this.feedbackService = feedbackService;
        this.bookmarkService = bookmarkService;
        this.bookMapper = bookMapper;
        this.mailService = mailService;
        this.orderMapper = orderMapper;
        this.paginationUtil = paginationUtil;
        this.formatUtil = formatUtil;
    }

    @HttpRequestMapping(path = "/reader/books/show", method = HttpMethod.GET)
    public void showBooks(@HttpRequestParameter(name = "page", defaultValue = "1") Integer page,
                          @HttpRequestParameter(name = "size", defaultValue = PaginationConstants.BOOKS_PER_PAGE_STR) Integer size,
                          @HttpSessionAttribute(name = "lang") String lang,
                          @HttpSessionAttribute(name = "readerAttributes") ReaderAttributes attributes,
                          HttpServletRequest request, HttpServletResponse response) throws Exception {

        logger.debug("Call show books in reader controller with page = "+page);

        Integer userId = attributes.isBooksInBookmarksOnly() ? attributes.getReader().getId() : null;
        List<Book> books;

        if (attributes.getBooksCount() == null) {
            if (attributes.isBooksAdvancedSearchEnabled()) {
                attributes.setBooksCount(bookService.countBooks(userId, attributes.getBooksIsbn(), attributes.getBooksPublisherQuery(), attributes.getBooksTitleQuery(), attributes.getBooksAuthorQuery(), attributes.getBooksFormat(), attributes.getBooksGenre(), attributes.getBooksLanguage()));
            } else {
                attributes.setBooksCount(bookService.countBooks(userId, attributes.getBooksQuery()));
            }
        }

        if (attributes.isBooksAdvancedSearchEnabled()) {
            books = bookService.findBooks1(userId, attributes.getBooksIsbn(), attributes.getBooksPublisherQuery(), attributes.getBooksTitleQuery(), attributes.getBooksAuthorQuery(), attributes.getBooksSortCriteria(), attributes.getBooksFormat(), attributes.getBooksGenre(), attributes.getBooksLanguage(), page, size);
        } else {
            books = bookService.findBooks3(userId, attributes.getBooksQuery(), page, size);
        }

        userService.refreshReader(attributes.getReader());

        request.setAttribute("readerFullname", attributes.getReader().getFirstname()+" "+attributes.getReader().getLastname());
        if (attributes.getReader().getFine() > 0) request.setAttribute("readerFine", formatUtil.formatPrice(attributes.getReader().getFine()));
        request.setAttribute("query", attributes.getBooksQuery());
        request.setAttribute("isbn", attributes.getBooksIsbn());
        request.setAttribute("publisherQuery", attributes.getBooksPublisherQuery());
        request.setAttribute("titleQuery", attributes.getBooksTitleQuery());
        request.setAttribute("authorQuery", attributes.getBooksAuthorQuery());
        request.setAttribute("sortCriteria", Objects.isNull(attributes.getBooksSortCriteria()) ? null : attributes.getBooksSortCriteria().toInt());
        request.setAttribute("format", Objects.isNull(attributes.getBooksFormat()) ? null : attributes.getBooksFormat().toInt());
        request.setAttribute("genre", Objects.isNull(attributes.getBooksGenre()) ? null : attributes.getBooksGenre().toInt());
        request.setAttribute("language", Objects.isNull(attributes.getBooksLanguage()) ? null : attributes.getBooksLanguage().toInt());
        request.setAttribute("bookmarksOnly", attributes.isBooksInBookmarksOnly());
        request.setAttribute("advancedSearch", attributes.isBooksAdvancedSearchEnabled());
        request.setAttribute("books", books.stream().map(book -> bookMapper.toBriefDTO(book, lang)).collect(Collectors.toList()));
        request.setAttribute("pagination", paginationUtil.paginate(page, attributes.getBooksCount(), size));

        logger.warn("BOOKS COUNT = "+attributes.getBooksCount());

        request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/reader/books.jsp").forward(request, response);
    }

    @HttpRequestMapping(path = "/reader/orders/show", method = HttpMethod.GET)
    public void showOrders(@HttpRequestParameter(name = "page", defaultValue = "1") Integer page,
                           @HttpRequestParameter(name = "size", defaultValue = PaginationConstants.ORDERS_PER_PAGE_STR) Integer size,
                           @HttpSessionAttribute(name = "lang") String lang,
                           @HttpSessionAttribute(name = "readerAttributes") ReaderAttributes attributes,
                           HttpServletRequest request, HttpServletResponse response) throws Exception {

        logger.debug("Call show orders in reader controller with page = "+page);

        if (attributes.getOrdersCount() == null) {
            attributes.setOrdersCount(orderService.countOrders(attributes.getReader().getId()));
        }

        List<Order> orders = orderService.findOrders(attributes.getReader().getId(), page, size);

        userService.refreshReader(attributes.getReader());

        request.setAttribute("readerFullname", attributes.getReader().getFirstname()+" "+attributes.getReader().getLastname());
        if (attributes.getReader().getFine() > 0) request.setAttribute("readerFine", formatUtil.formatPrice(attributes.getReader().getFine()));
        request.setAttribute("orders", orders.stream().map(order -> orderMapper.toOrderDTO(order, lang)).collect(Collectors.toList()));
        request.setAttribute("pagination", paginationUtil.paginate(page, attributes.getOrdersCount(), size));

        request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/reader/orders.jsp").forward(request, response);
    }

    @HttpRequestMapping(path = "/reader/profile/show", method = HttpMethod.GET)
    public void showProfile(@HttpSessionAttribute(name = "readerAttributes") ReaderAttributes attributes,
                            HttpServletRequest request, HttpServletResponse response) throws Exception {

        logger.debug("Call show profile in reader controller");

        userService.refreshReader(attributes.getReader());

        request.setAttribute("readerFullname", attributes.getReader().getFirstname()+" "+attributes.getReader().getLastname());
        request.setAttribute("readerFirstname", attributes.getReader().getFirstname());
        request.setAttribute("readerLastname", attributes.getReader().getLastname());
        request.setAttribute("readerEmail", attributes.getReader().getEmail());
        if (attributes.getReader().getFine() > 0) request.setAttribute("readerFine", formatUtil.formatPrice(attributes.getReader().getFine()));

        request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/reader/profile.jsp").forward(request, response);
    }


    @HttpRequestMapping(path = "/reader/profile/edit", method = HttpMethod.POST)
    public void editProfile(@HttpRequestParameter(name = "firstname", required = false) String firstname,
                            @HttpRequestParameter(name = "lastname", required = false) String lastname,
                            @HttpRequestParameter(name = "email", required = false) String email,
                            @HttpRequestParameter(name = "password", required = false) String password,
                            @HttpSessionAttribute(name = "lang") String lang,
                            @HttpSessionAttribute(name = "readerAttributes") ReaderAttributes attributes,
                            HttpServletResponse response) throws Exception {

        logger.debug("Call edit profile in reader controller");

        UserCreationEditionResult result = (password == null) ? userService.editUser(attributes.getReader().getId(), firstname, lastname, email) : userService.editUser(attributes.getReader().getId(), firstname, lastname, email, password);
        if (result.getSuccess()) attributes.setReader(result.getUser());

        response.setContentType("text/json; charset=utf-8");
        new ObjectMapper().writer().writeValue(response.getWriter(), operationResultMapper.toDTO(result, lang));
    }

    @HttpRequestMapping(path = "/reader/fine/pay", method = HttpMethod.POST)
    public void payFine(@HttpSessionAttribute(name = "readerAttributes") ReaderAttributes attributes,
                        @HttpSessionAttribute(name = "lang") String lang,
                        HttpServletResponse response) throws Exception {

        logger.debug("Call pay fine in reader controller");

        int fine = userService.payReaderFine(attributes.getReader().getId());
        mailService.sendFinePaymentMessageAsync(attributes.getReader().getEmail(), fine, lang);
        userService.refreshReader(attributes.getReader());

        response.setStatus(HttpServletResponse.SC_OK);
    }


    @HttpRequestMapping(path = "/reader/books/find/advanced", method = HttpMethod.POST)
    public void findBooks(@HttpRequestParameter(name = "isbn", required = false) Integer isbn,
                          @HttpRequestParameter(name = "publisherQuery", required = false) String publisherQuery,
                          @HttpRequestParameter(name = "titleQuery", required = false) String titleQuery,
                          @HttpRequestParameter(name = "authorQuery", required = false) String authorQuery,
                          @HttpRequestParameter(name = "sortCriteria", required = false) Integer sortCriteria,
                          @HttpRequestParameter(name = "format", required = false) Integer format,
                          @HttpRequestParameter(name = "genre", required = false) Integer genre,
                          @HttpRequestParameter(name = "language", required = false) Integer language,
                          @HttpRequestParameter(name = "bookmarksOnly") Boolean bookmarksOnly,
                          @HttpSessionAttribute(name = "readerAttributes") ReaderAttributes attributes,
                          HttpServletRequest request, HttpServletResponse response) throws Exception {

        logger.debug("Call find books (advanced) in reader controller");

        attributes.setBooksIsbn(isbn);
        attributes.setBooksPublisherQuery(publisherQuery);
        attributes.setBooksTitleQuery(titleQuery);
        attributes.setBooksAuthorQuery(authorQuery);
        attributes.setBooksSortCriteria(Objects.isNull(sortCriteria) ? null : BookSortCriteria.ofInt(sortCriteria));
        attributes.setBooksFormat(Objects.isNull(format) ? null : BookFormat.ofInt(format));
        attributes.setBooksGenre(Objects.isNull(genre) ? null : BookGenre.ofInt(genre));
        attributes.setBooksLanguage(Objects.isNull(language) ? null : BookLanguage.ofInt(language));
        attributes.setBooksInBookmarksOnly(bookmarksOnly);
        attributes.setBooksAdvancedSearchEnabled(true);
        attributes.setBooksCount(null);

        response.sendRedirect(request.getContextPath()+"/reader/books/show");
    }

    @HttpRequestMapping(path = "/reader/books/find/basic", method = HttpMethod.POST)
    public void findBooks(@HttpRequestParameter(name = "query", required = false) String query,
                          @HttpRequestParameter(name = "bookmarksOnly") Boolean bookmarksOnly,
                          @HttpSessionAttribute(name = "readerAttributes") ReaderAttributes attributes,
                          HttpServletRequest request, HttpServletResponse response) throws Exception {

        logger.debug("Call find books (basic) in reader controller");

        attributes.setBooksQuery(query);
        attributes.setBooksInBookmarksOnly(bookmarksOnly);
        attributes.setBooksAdvancedSearchEnabled(false);
        attributes.setBooksCount(null);

        response.sendRedirect(request.getContextPath()+"/reader/books/show");
    }

    @HttpRequestMapping(path = "/reader/feedback/create", method = HttpMethod.POST)
    public void createFeedback(@HttpRequestParameter(name = "bookId") Integer bookId,
                               @HttpRequestParameter(name = "grade") Integer grade,
                               @HttpRequestParameter(name = "text", defaultValue = "") String text,
                               @HttpSessionAttribute(name = "readerAttributes") ReaderAttributes attributes,
                               HttpServletRequest request, HttpServletResponse response) throws Exception {

        logger.debug("Call create feedback in reader controller with book id = "+bookId);

        feedbackService.createFeedback(bookId, attributes.getReader().getId(), text, grade);
        attributes.setBooksCount(null);

        response.sendRedirect(request.getContextPath()+"/reader/books/show");
    }

    @HttpRequestMapping(path = "/reader/book/add-to-bookmarks", method = HttpMethod.POST)
    public void addBookToBookmarks(@HttpRequestParameter(name = "bookId") Integer bookId,
                                   @HttpSessionAttribute(name = "readerAttributes") ReaderAttributes attributes,
                                   HttpServletRequest request, HttpServletResponse response) throws Exception {

        logger.debug("Call add book to bookmarks in reader controller with book id = "+bookId);

        bookmarkService.saveBookmarkByUserIdAndBookId(attributes.getReader().getId(), bookId);
        attributes.setBooksCount(null);

        response.sendRedirect(request.getContextPath()+"/reader/books/show");
    }

    @HttpRequestMapping(path = "/reader/book/delete-from-bookmarks", method = HttpMethod.POST)
    public void deleteBookFromBookmarks(@HttpRequestParameter(name = "bookId") Integer bookId,
                                        @HttpSessionAttribute(name = "readerAttributes") ReaderAttributes attributes,
                                        HttpServletRequest request, HttpServletResponse response) throws Exception {

        logger.debug("Call delete book from bookmarks in reader controller with book id = "+bookId);

        bookmarkService.deleteBookmarkByUserIdAndBookId(attributes.getReader().getId(), bookId);
        attributes.setBooksCount(null);

        response.sendRedirect(request.getContextPath()+"/reader/books/show");
    }


    @HttpRequestMapping(path = "/reader/order/cancel", method = HttpMethod.POST)
    public void cancelOrder(@HttpRequestParameter(name = "orderCode") Integer orderCode,
                            @HttpSessionAttribute(name = "readerAttributes") ReaderAttributes attributes,
                            HttpServletRequest request, HttpServletResponse response) throws Exception {

        logger.debug("Call cancel order in reader controller with order code = "+orderCode);

        orderService.cancelOrder(orderCode);
        attributes.setOrdersCount(null);

        response.sendRedirect(request.getContextPath()+"/reader/orders/show");
    }

    @HttpRequestMapping(path = "/reader/order/create", method = HttpMethod.POST)
    public void createOrder(@HttpRequestParameter(name = "bookId") Integer bookId,
                            @HttpSessionAttribute(name = "readerAttributes") ReaderAttributes attributes,
                            HttpServletRequest request, HttpServletResponse response) throws Exception {

        logger.debug("Call create order in reader controller with book id = "+bookId);

        orderService.createOrder(bookId, attributes.getReader().getId());
        attributes.setOrdersCount(null);

        response.sendRedirect(request.getContextPath()+"/reader/orders/show");
    }

    @HttpRequestMapping(path = "/reader/order/delete", method = HttpMethod.POST)
    public void deleteOrder(@HttpRequestParameter(name = "orderCode") Integer orderCode,
                            @HttpSessionAttribute(name = "readerAttributes") ReaderAttributes attributes,
                            HttpServletRequest request, HttpServletResponse response) throws Exception {

        logger.debug("Call delete order in reader controller with order code = "+orderCode);

        orderService.deleteOrder(orderCode);
        attributes.setOrdersCount(null);

        response.sendRedirect(request.getContextPath()+"/reader/orders/show");
    }

    @HttpRequestMapping(path = "/reader/book/details", method = HttpMethod.GET)
    public void getBookDetails(@HttpRequestParameter(name = "bookId") Integer bookId,
                               @HttpSessionAttribute(name = "lang") String lang,
                               @HttpSessionAttribute(name = "readerAttributes") ReaderAttributes attributes,
                               HttpServletResponse response) throws Exception {

        logger.debug("Call get book details (in json format) in reader controller with book id = "+bookId);

        response.setContentType("text/json; charset=utf-8");
        Book book = bookService.findBookById(bookId).orElseThrow();
        List<Feedback> feedbacks = feedbackService.findFeedbacksByBookId(book.getId());
        boolean isBookInBookmarks = bookmarkService.existsBookmarkByUserIdAndBookId(attributes.getReader().getId(), book.getId());
        boolean isBookInOrders = orderService.existsOrderByUserIdAndBookId(attributes.getReader().getId(), book.getId());
        BookDetailedDTO dto = bookMapper.toDetailedDTO(book, feedbacks, isBookInBookmarks, isBookInOrders, lang);
        new ObjectMapper().writer().writeValue(response.getWriter(), dto);
    }
}
