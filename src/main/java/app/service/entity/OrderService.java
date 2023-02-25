package app.service.entity;

import app.entity.Order;
import app.enums.OrderSortCriteria;
import app.enums.OrderState;
import app.exceptions.DatabaseException;

import java.time.LocalDate;
import java.util.List;

/**
 * Provides operations with order entity
 **/
public interface OrderService {
    /**
     * Finds orders
     * @param code order code (optional)
     * @param userId id of user (optional)
     * @param bookId id of book (optional)
     * @param state order state (optional)
     * @param sortCriteria sort criteria (optional)
     * @param page pagination parameter
     * @param size pagination parameter
     * @return list of found orders
     **/
    List<Order> findOrders(Integer code, Integer userId, Integer bookId, OrderState state, OrderSortCriteria sortCriteria, int page, int size) throws DatabaseException;

    /**
     * @see OrderService#findOrders(Integer, Integer, Integer, OrderState, OrderSortCriteria, int, int)
     **/
    List<Order> findOrders(Integer userId, int page, int size) throws DatabaseException;

    /**
     * Counts orders
     * @param code order code (optional)
     * @param userId id of user (optional)
     * @param bookId id of book (optional)
     * @param state order state (optional)
     * @return count of found orders
     **/
    int countOrders(Integer code, Integer userId, Integer bookId, OrderState state) throws DatabaseException;

    /**
     * @see OrderService#countOrders(Integer, Integer, Integer, OrderState)
     **/
    int countOrders(Integer userId) throws DatabaseException;

    /**
     * Confirms order on valuable books
     * @param orderCode code of order
     **/
    void confirmOrder(int orderCode) throws DatabaseException;

    /**
     * Confirms order on non-valuable books
     * @param orderCode code of order
     **/
    void confirmOrder(int orderCode, LocalDate deadlineDate) throws DatabaseException;

    /**
     * Cancels order
     * @param orderCode code of order
     **/
    void cancelOrder(int orderCode) throws DatabaseException;

    /**
     * Closes order
     * @param orderCode code of order
     **/
    void closeOrder(int orderCode) throws DatabaseException;

    /**
     * Deletes order
     * @param orderCode code of order
     **/
    void deleteOrder(int orderCode) throws DatabaseException;

    /**
     * Creates order
     * @param bookId id of book
     * @param userId id of user
     **/
    void createOrder(int bookId, int userId) throws DatabaseException;

    /**
     * Checks if order by book and user exists
     * @param bookId id of book
     * @param userId id of user
     * @return true if order by book and user exists and false otherwise
     **/
    boolean existsOrderByUserIdAndBookId(int userId, int bookId) throws DatabaseException;
}
