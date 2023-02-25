package app.controller;

import app.annotations.HttpRequestMapping;
import app.annotations.HttpRequestParameter;
import app.annotations.HttpSessionAttribute;
import app.constants.PaginationConstants;
import app.entity.Book;
import app.entity.Order;
import app.entity.User;
import app.enums.HttpMethod;
import app.enums.OrderSortCriteria;
import app.enums.OrderState;
import app.service.mapper.BookMapper;
import app.service.mapper.OrderMapper;
import app.service.mapper.UserMapper;
import app.service.entity.BookService;
import app.service.entity.OrderService;

import app.service.entity.UserService;
import app.service.utils.PaginationUtil;
import app.service.other.XlsxGenerationService;
import app.userdata.LibrarianAttributes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class LibrarianController {
    private static final Logger logger = LogManager.getLogger(LibrarianController.class);

    private final BookService bookService;
    private final UserService userService;
    private final OrderService orderService;
    private final BookMapper bookMapper;
    private final UserMapper userMapper;
    private final OrderMapper orderMapper;
    private final PaginationUtil paginationUtil;
    private final XlsxGenerationService xlsxGenerationService;


    public LibrarianController(BookService bookService, UserService userService, OrderService orderService, BookMapper bookMapper, UserMapper userMapper, OrderMapper orderMapper, PaginationUtil paginationUtil, XlsxGenerationService xlsxGenerationService) {
        this.bookService = bookService;
        this.userService = userService;
        this.orderService = orderService;
        this.bookMapper = bookMapper;
        this.userMapper = userMapper;
        this.orderMapper = orderMapper;
        this.paginationUtil = paginationUtil;
        this.xlsxGenerationService = xlsxGenerationService;
    }

    @HttpRequestMapping(path = "/librarian/books/show", method = HttpMethod.GET)
    public void showBooks(@HttpRequestParameter(name = "page", defaultValue = "1") Integer page,
                          @HttpRequestParameter(name = "size", defaultValue = PaginationConstants.BOOKS_PER_PAGE_STR) Integer size,
                          @HttpSessionAttribute(name = "lang") String lang,
                          @HttpSessionAttribute(name = "librarianAttributes") LibrarianAttributes attributes,
                          HttpServletRequest request, HttpServletResponse response) throws Exception {

        logger.debug("Call show books in librarian controller with page = "+page);

        if (attributes.getBooksCount() == null) {
            attributes.setBooksCount(bookService.countBooks(attributes.getBooksQuery()));
        }

        List<Book> books = bookService.findBooks4(attributes.getBooksQuery(), page, size);

        request.setAttribute("librarianFullname", attributes.getLibrarian().getFirstname()+" "+attributes.getLibrarian().getLastname());
        request.setAttribute("query", attributes.getBooksQuery());
        request.setAttribute("books", books.stream().map(book -> bookMapper.toBriefDTO(book, lang)).collect(Collectors.toList()));
        request.setAttribute("pagination", paginationUtil.paginate(page, attributes.getBooksCount(), size));

        request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/librarian/books.jsp").forward(request, response);
    }



    @HttpRequestMapping(path = "/librarian/readers/show", method = HttpMethod.GET)
    public void showReaders(@HttpRequestParameter(name = "page", defaultValue = "1") Integer page,
                            @HttpRequestParameter(name = "size", defaultValue = PaginationConstants.USERS_PER_PAGE_STR) Integer size,
                            @HttpSessionAttribute(name = "librarianAttributes") LibrarianAttributes attributes,
                            HttpServletRequest request, HttpServletResponse response) throws Exception {

        logger.debug("Call show readers in librarian controller with page = "+page);

        if (attributes.getReadersCount() == null) {
            attributes.setReadersCount(userService.countReaders(attributes.getReadersQuery()));
        }

        List<User> readers = userService.findReaders(attributes.getReadersQuery(), page, size);

        request.setAttribute("librarianFullname", attributes.getLibrarian().getFirstname()+" "+attributes.getLibrarian().getLastname());
        request.setAttribute("query", attributes.getReadersQuery());
        request.setAttribute("readers", readers.stream().map(userMapper::toReaderDTO).collect(Collectors.toList()));
        request.setAttribute("pagination", paginationUtil.paginate(page, attributes.getReadersCount(), size));


        request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/librarian/readers.jsp").forward(request, response);
    }

    @HttpRequestMapping(path = "/librarian/orders/show", method = HttpMethod.GET)
    public void showOrders(@HttpRequestParameter(name = "page", defaultValue = "1") Integer page,
                           @HttpRequestParameter(name = "size", defaultValue = PaginationConstants.ORDERS_PER_PAGE_STR) Integer size,
                           @HttpSessionAttribute(name = "lang") String lang,
                           @HttpSessionAttribute(name = "librarianAttributes") LibrarianAttributes attributes,
                           HttpServletRequest request, HttpServletResponse response) throws Exception {

        logger.debug("Call show orders in librarian controller with page = "+page);

        if (attributes.getOrdersCount() == null) {
            attributes.setOrdersCount(orderService.countOrders(attributes.getOrdersCode(), attributes.getOrdersReaderId(), attributes.getOrdersBookId(), attributes.getOrdersState()));
        }

        List<Order> orders = orderService.findOrders(attributes.getOrdersCode(), attributes.getOrdersReaderId(), attributes.getOrdersBookId(), attributes.getOrdersState(), attributes.getOrdersSortCriteria(), page, size);

        request.setAttribute("librarianFullname", attributes.getLibrarian().getFirstname()+" "+attributes.getLibrarian().getLastname());
        request.setAttribute("code", attributes.getOrdersCode());
        request.setAttribute("state", Objects.isNull(attributes.getOrdersState()) ? null : attributes.getOrdersState().toInt());
        request.setAttribute("sortCriteria", Objects.isNull(attributes.getOrdersSortCriteria()) ? null : attributes.getOrdersSortCriteria().toInt());
        request.setAttribute("readerFullname", attributes.getOrdersReaderFullname());
        request.setAttribute("bookTitle", attributes.getOrdersBookTitle());
        request.setAttribute("orders", orders.stream().map(order -> orderMapper.toOrderDTO(order, lang)).collect(Collectors.toList()));
        request.setAttribute("pagination", paginationUtil.paginate(page, attributes.getOrdersCount(), size));


        request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/librarian/orders.jsp").forward(request, response);
    }

    @HttpRequestMapping(path = "/librarian/books/find", method = HttpMethod.POST)
    public void findBooks(@HttpRequestParameter(name = "query", required = false) String query,
                          @HttpSessionAttribute(name = "librarianAttributes") LibrarianAttributes attributes,
                          HttpServletRequest request, HttpServletResponse response) throws Exception {

        logger.debug("Call find books in librarian controller");

        attributes.setBooksQuery(query);
        attributes.setBooksCount(null);

        response.sendRedirect(request.getContextPath()+"/librarian/books/show");
    }


    @HttpRequestMapping(path = "/librarian/readers/find", method = HttpMethod.POST)
    public void findReaders(@HttpRequestParameter(name = "query", required = false) String query,
                            @HttpSessionAttribute(name = "librarianAttributes") LibrarianAttributes attributes,
                            HttpServletRequest request, HttpServletResponse response) throws Exception {

        logger.debug("Call find readers in librarian controller");

        attributes.setReadersQuery(query);
        attributes.setReadersCount(null);

        response.sendRedirect(request.getContextPath()+"/librarian/readers/show");
    }

    @HttpRequestMapping(path = "/librarian/orders/find", method = HttpMethod.POST)
    public void findOrders(@HttpRequestParameter(name = "code", required = false) Integer code,
                           @HttpRequestParameter(name = "state", required = false) Integer state,
                           @HttpRequestParameter(name = "sortCriteria", required = false) Integer sortCriteria,
                           @HttpSessionAttribute(name = "librarianAttributes") LibrarianAttributes attributes,
                           HttpServletRequest request, HttpServletResponse response) throws Exception {

        logger.debug("Call find orders in librarian controller");

        attributes.setOrdersCode(code);
        attributes.setOrdersState(Objects.isNull(state) ? null : OrderState.ofInt(state));
        attributes.setOrdersSortCriteria(Objects.isNull(sortCriteria) ? null : OrderSortCriteria.ofInt(sortCriteria));
        attributes.setOrdersCount(null);

        response.sendRedirect(request.getContextPath()+"/librarian/orders/show");
    }

    @HttpRequestMapping(path = "/librarian/reader/select", method = HttpMethod.POST)
    public void selectReader(@HttpRequestParameter(name = "readerId", required = false) Integer readerId,
                             @HttpSessionAttribute(name = "librarianAttributes") LibrarianAttributes attributes,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {

        logger.debug("Call select reader in librarian controller");

        Optional<User> reader = Objects.isNull(readerId) ? Optional.empty() : userService.findReaderById(readerId);
        if (reader.isPresent()) {
            attributes.setOrdersReaderId(reader.get().getId());
            attributes.setOrdersReaderFullname(reader.get().getFirstname()+" "+reader.get().getLastname());
        } else {
            attributes.setOrdersReaderId(null);
            attributes.setOrdersReaderFullname(null);
        }
        attributes.setOrdersCount(null);

        response.sendRedirect(request.getContextPath()+"/librarian/orders/show");
    }

    @HttpRequestMapping(path = "/librarian/book/select", method = HttpMethod.POST)
    public void selectBook(@HttpRequestParameter(name = "bookId", required = false) Integer bookId,
                           @HttpSessionAttribute(name = "librarianAttributes") LibrarianAttributes attributes,
                           HttpServletRequest request, HttpServletResponse response) throws Exception {

        logger.debug("Call select reader in librarian controller");

        Optional<Book> book = Objects.isNull(bookId) ? Optional.empty() : bookService.findBookById(bookId);
        if (book.isPresent()) {
            attributes.setOrdersBookId(book.get().getId());
            attributes.setOrdersBookTitle(book.get().getTitle());
        } else {
            attributes.setOrdersBookId(null);
            attributes.setOrdersBookTitle(null);
        }
        attributes.setOrdersCount(null);

        response.sendRedirect(request.getContextPath()+"/librarian/orders/show");
    }

    @HttpRequestMapping(path = "/librarian/order/confirm", method = HttpMethod.POST)
    public void confirmOrder(@HttpRequestParameter(name = "orderCode") Integer orderCode,
                             @HttpRequestParameter(name = "orderDeadlineDate", required = false) LocalDate orderDeadlineDate,
                             @HttpSessionAttribute(name = "librarianAttributes") LibrarianAttributes attributes,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {

        logger.debug("Call confirm order in librarian controller with code = "+orderCode);

        if (Objects.isNull(orderDeadlineDate)) {
            orderService.confirmOrder(orderCode);
        } else {
            orderService.confirmOrder(orderCode, orderDeadlineDate);
        }
        attributes.setOrdersCount(null);

        response.sendRedirect(request.getContextPath()+"/librarian/orders/show");
    }

    @HttpRequestMapping(path = "/librarian/order/close", method = HttpMethod.POST)
    public void closeOrder(@HttpRequestParameter(name = "orderCode") Integer orderCode,
                           @HttpSessionAttribute(name = "librarianAttributes") LibrarianAttributes attributes,
                           HttpServletRequest request, HttpServletResponse response) throws Exception {

        logger.debug("Call close order in librarian controller with code = "+orderCode);

        orderService.closeOrder(orderCode);
        attributes.setOrdersCount(null);

        response.sendRedirect(request.getContextPath()+"/librarian/orders/show");
    }

    @HttpRequestMapping(path = "/librarian/order/cancel", method = HttpMethod.POST)
    public void cancelOrder(@HttpRequestParameter(name = "orderCode") Integer orderCode,
                            @HttpSessionAttribute(name = "librarianAttributes") LibrarianAttributes attributes,
                            HttpServletRequest request, HttpServletResponse response) throws Exception {

        logger.debug("Call cancel order in librarian controller with code = "+orderCode);

        orderService.cancelOrder(orderCode);
        attributes.setOrdersCount(null);

        response.sendRedirect(request.getContextPath()+"/librarian/orders/show");
    }

    @HttpRequestMapping(path = "/librarian/generate-reader-data-table", method = HttpMethod.GET)
    public void generateReadersInformationTable(@HttpSessionAttribute(name = "lang") String lang,
                                                HttpServletResponse response) throws Exception {

        logger.debug("Call generate readers information table in librarian controller");

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=\"ReadersTable.xlsx\"");
        byte[] bytes = xlsxGenerationService.generateReaderDataXlsxTable(lang);

        ServletOutputStream out = response.getOutputStream();
        out.write(bytes);
    }

    @HttpRequestMapping(path = "/librarian/generate-book-data-table", method = HttpMethod.GET)
    public void generateBooksInformationTable(@HttpSessionAttribute(name = "lang") String lang,
                                              HttpServletResponse response) throws Exception {

        logger.debug("Call generate books information table in librarian controller");

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=\"BooksTable.xlsx\"");
        byte[] bytes = xlsxGenerationService.generateBookDataXlsxTable(lang);

        ServletOutputStream out = response.getOutputStream();
        out.write(bytes);
    }

}
