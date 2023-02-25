package app.database.dao;

import app.database.transaction.TransactionManager;
import app.entity.Order;
import app.enums.OrderSortCriteria;
import app.enums.OrderState;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

/**
 * Provides database operations with table of orders
 **/
public interface OrderDAO {
    /**
     * Finds order by code
     * @param orderCode code of order
     * @return found order in optional wrapper
     **/
    Optional<Order> findOrderByCode(int orderCode) throws DatabaseException;

    /**
     * Finds orders using filter parameters
     * @param code code of order (optional)
     * @param userId id of user (optional)
     * @param bookId id of book (optional)
     * @param state state of order (optional)
     * @param sortCriteria criteria of order sort (optional)
     * @param page number of page for which orders are being searched
     * @param recordsPerPage max number of orders that result list could contain
     * @return list of found orders sorted by sort criteria
     **/
    List<Order> findOrders(Integer code, Integer userId, Integer bookId, OrderState state, OrderSortCriteria sortCriteria, int page, int recordsPerPage) throws DatabaseException;

    /**
     * Count orders using filter parameters
     * @param code code of order (optional)
     * @param userId id of user (optional)
     * @param bookId id of book (optional)
     * @param state state of order (optional)
     * @return number of found orders
     **/
    int countOrders(Integer code, Integer userId, Integer bookId, OrderState state) throws DatabaseException;

    /**
     * Creates new order. Used with {@link TransactionManager} in
     * {@link app.database.transaction.TransactionExecutor#execute(Connection)}
     * @param order order to create
     * @param connection connection with database
     **/
    void createOrder(Order order, Connection connection) throws DatabaseException;

    /**
     * Updates existing order. Used with {@link TransactionManager} in
     * {@link app.database.transaction.TransactionExecutor#execute(Connection)}
     * @param order order to update
     * @param connection connection with database
     **/
    void updateOrder(Order order, Connection connection) throws DatabaseException;

    /**
     * Checks if order with given book and user ids exists
     * @param userId id of order user
     * @param bookId id of order book
     * @return true if order with given book and user ids exists and false otherwise
     **/
    boolean existsOrderByUserIdAndBookId(int userId, int bookId) throws DatabaseException;
}
