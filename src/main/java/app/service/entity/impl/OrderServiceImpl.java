package app.service.entity.impl;

import app.database.dao.BookDAO;
import app.database.dao.OrderDAO;
import app.database.dao.UserDAO;
import app.database.transaction.TransactionManager;
import app.entity.Book;
import app.entity.Order;
import app.enums.OrderSortCriteria;
import app.enums.OrderState;
import app.enums.TransactionResult;
import app.exceptions.DatabaseException;
import app.exceptions.InvalidStateException;
import app.service.entity.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    private static final Logger logger = LogManager.getLogger(OrderServiceImpl.class);

    private final UserDAO userDAO;
    private final BookDAO bookDAO;
    private final OrderDAO orderDAO;
    private final TransactionManager transactionManager;

    public OrderServiceImpl(UserDAO userDAO, BookDAO bookDAO, OrderDAO orderDAO, TransactionManager transactionManager) {
        this.userDAO = userDAO;
        this.bookDAO = bookDAO;
        this.orderDAO = orderDAO;
        this.transactionManager = transactionManager;
    }

    @Override
    public List<Order> findOrders(Integer code, Integer userId, Integer bookId, OrderState state, OrderSortCriteria sortCriteria, int page, int size) throws DatabaseException {
        return orderDAO.findOrders(code, userId, bookId, state, sortCriteria, page, size);
    }

    @Override
    public List<Order> findOrders(Integer userId, int page, int size) throws DatabaseException {
        return orderDAO.findOrders(null, userId, null, null, null, page, size);
    }

    @Override
    public int countOrders(Integer code, Integer userId, Integer bookId, OrderState state) throws DatabaseException {
        return orderDAO.countOrders(code, userId, bookId, state);
    }

    @Override
    public int countOrders(Integer userId) throws DatabaseException {
        return orderDAO.countOrders(null, userId, null, null);
    }

    @Override
    public void confirmOrder(int orderCode) throws DatabaseException {
        logger.debug("Order confirmation with code = "+orderCode+" started");

        Order order = orderDAO.findOrderByCode(orderCode).orElseThrow();

        if (!order.getBook().isValuable()) throw new InvalidStateException("Deadline date is required for orders on non-valuable books");
        if (order.getState() != OrderState.WAITING_FOR_CONFIRMATION) throw new InvalidStateException("Cannot confirm order with state : "+order.getState());
        if (order.getBook().getFreeCopiesNumber() <= 0) throw new InvalidStateException("Cannot confirm order of a book without any free copy");

        order.setState(OrderState.CONFIRMED);
        order.setCreationDate(null);
        order.setDeadlineDate(null);

        transactionManager.execute(connection -> {
            orderDAO.updateOrder(order, connection);

            Book book = order.getBook();
            book.setFreeCopiesNumber(book.getFreeCopiesNumber() - 1);
            bookDAO.updateBook(book, connection);

            return TransactionResult.COMMIT;
        });

        logger.debug("Order confirmation with code = "+orderCode+" successfully finished");
    }

    @Override
    public void confirmOrder(int orderCode, LocalDate deadlineDate) throws DatabaseException {
        logger.debug("Order confirmation with code = "+orderCode+" started");

        Order order = orderDAO.findOrderByCode(orderCode).orElseThrow();
        LocalDate creationDate = LocalDate.now();

        if (order.getBook().isValuable()) throw new InvalidStateException("Deadline date is odd for orders on valuable books");
        if (order.getState() != OrderState.WAITING_FOR_CONFIRMATION) throw new InvalidStateException("Cannot confirm order with state : "+order.getState());
        if (deadlineDate.isBefore(creationDate.plusDays(7))) throw new InvalidStateException("Deadline date must be in future after at least 7 days after creation date");
        if (deadlineDate.isAfter(creationDate.plusDays(60))) throw new InvalidStateException("Deadline date cannot be later than 60 days after creation date");
        if (order.getBook().getFreeCopiesNumber() <= 0) throw new InvalidStateException("Cannot confirm order of a book without any free copy");

        order.setState(OrderState.CONFIRMED);
        order.setCreationDate(creationDate);
        order.setDeadlineDate(deadlineDate);

        transactionManager.execute(connection -> {
            orderDAO.updateOrder(order, connection);

            Book book = order.getBook();
            book.setFreeCopiesNumber(book.getFreeCopiesNumber() - 1);
            bookDAO.updateBook(book, connection);

            return TransactionResult.COMMIT;
        });

        logger.debug("Order confirmation with code = "+orderCode+" successfully finished");
    }

    @Override
    public void cancelOrder(int orderCode) throws DatabaseException {
        logger.debug("Order cancel with code = "+orderCode+" started");

        Order order = orderDAO.findOrderByCode(orderCode).orElseThrow();

        if (order.getState() != OrderState.WAITING_FOR_CONFIRMATION) throw new InvalidStateException("Cannot cancel order with state : "+order.getState());

        order.setState(OrderState.CANCELLED);
        order.setCreationDate(null);
        order.setDeadlineDate(null);

        transactionManager.execute(connection -> {
            orderDAO.updateOrder(order, connection);
            return TransactionResult.COMMIT;
        });

        logger.debug("Order cancel with code = "+orderCode+" successfully finished");
    }

    @Override
    public void closeOrder(int orderCode) throws DatabaseException {
        logger.debug("Order closing with code = "+orderCode+" started");

        Order order = orderDAO.findOrderByCode(orderCode).orElseThrow();

        if (order.getState() != OrderState.CONFIRMED) throw new InvalidStateException("Cannot close order with state : "+order.getState());
        if (order.getBook().getFreeCopiesNumber() >= order.getBook().getTotalCopiesNumber()) throw new InvalidStateException("Cannot close order of a book without any copy in use (cannot close non-existent order)");

        order.setState(OrderState.CLOSED);
        order.setCreationDate(null);
        order.setDeadlineDate(null);

        transactionManager.execute(connection -> {
            orderDAO.updateOrder(order, connection);

            Book book = order.getBook();
            book.setFreeCopiesNumber(book.getFreeCopiesNumber() + 1);
            bookDAO.updateBook(book, connection);

            return TransactionResult.COMMIT;
        });

        logger.debug("Order closing with code = "+orderCode+" successfully finished");
    }

    @Override
    public void deleteOrder(int orderCode) throws DatabaseException {
        logger.debug("Order deletion with code = "+orderCode+" started");

        Order order = orderDAO.findOrderByCode(orderCode).orElseThrow();

        if (order.getState() == OrderState.CONFIRMED) throw new InvalidStateException("Cannot delete order with state : "+order.getState());

        order.setState(OrderState.DELETED);
        order.setCreationDate(null);
        order.setDeadlineDate(null);

        transactionManager.execute(connection -> {
            orderDAO.updateOrder(order, connection);
            return TransactionResult.COMMIT;
        });

        logger.debug("Order deletion with code = "+orderCode+" successfully finished");
    }

    @Override
    public void createOrder(int bookId, int userId) throws DatabaseException {
        logger.debug("Order creation on book with id = "+bookId+" by user with id = "+userId+" started");

        Order order = new Order();
        order.setBook(bookDAO.findBookById(bookId).orElseThrow());
        order.setUser(userDAO.findUserById(userId).orElseThrow());
        order.setState(OrderState.WAITING_FOR_CONFIRMATION);

        transactionManager.execute(connection -> {
            orderDAO.createOrder(order, connection);
            return TransactionResult.COMMIT;
        });

        logger.debug("Order creation with code "+order.getCode()+" on book with id = "+bookId+" by user with id = "+userId+" successfully finished");
    }

    @Override
    public boolean existsOrderByUserIdAndBookId(int userId, int bookId) throws DatabaseException {
        return orderDAO.existsOrderByUserIdAndBookId(userId, bookId);
    }
}
