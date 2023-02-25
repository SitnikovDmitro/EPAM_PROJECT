package app.database.dao.impl;

import app.database.dao.OrderDAO;
import app.database.pool.ConnectionPool;
import app.database.utils.ResultSetExtractor;
import app.entity.Order;
import app.enums.*;
import app.exceptions.DatabaseException;
import app.utils.QueryBuilder;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class OrderDAOImpl implements OrderDAO {
    private static final String ORDER_JOIN_EXPR = "(orders INNER JOIN users ON orders.userId = users.id INNER JOIN books ON orders.bookId = books.id INNER JOIN publishers ON books.publisherId = publishers.id)";
    private static final String FIND_ORDER_BY_CODE_SQL = "SELECT * FROM "+ORDER_JOIN_EXPR+" WHERE code = ?;";
    private static final String CREATE_ORDER_SQL = "INSERT INTO orders (code, userId, bookId, state, creationDate, deadlineDate) VALUES (NULL, ?, ?, ?, ?, ?);";
    private static final String UPDATE_ORDER_SQL = "UPDATE orders SET userId = ?, bookId = ?, state = ?, creationDate = ?, deadlineDate = ? WHERE code = ?;";
    private static final String EXISTS_ORDER_BY_USER_ID_AND_BOOK_ID_SQL = "SELECT EXISTS (SELECT * FROM orders WHERE userId = ? AND bookId = ? AND state <> "+OrderState.DELETED.toInt()+");";

    private final ConnectionPool connectionPool;

    public OrderDAOImpl(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public Optional<Order> findOrderByCode(int orderCode) throws DatabaseException {
        try (Connection con = connectionPool.getConnection();
             PreparedStatement stmt = con.prepareStatement(FIND_ORDER_BY_CODE_SQL)) {

            stmt.setInt(1, orderCode);

            ResultSet set = stmt.executeQuery();
            Optional<Order> order = ResultSetExtractor.extractOrder(set);
            set.close();

            return order;
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }


    @Override
    public List<Order> findOrders(Integer code, Integer userId, Integer bookId, OrderState state, OrderSortCriteria sortCriteria, int page, int recordsPerPage) throws DatabaseException {
        String orderCriteria, orderType;

        StringJoiner conditions = new StringJoiner(" AND ");
        if (code != null) conditions.add("orders.code = "+code);
        if (userId != null) conditions.add("orders.userId = "+userId);
        if (bookId != null) conditions.add("orders.bookId = "+bookId);
        if (state != null) conditions.add("orders.state = "+state.toInt());
        conditions.add("orders.state <> "+OrderState.DELETED.toInt());

        if (sortCriteria == OrderSortCriteria.CREATION_DATE_FROM_OLDER_TO_NEWER || sortCriteria == OrderSortCriteria.DEADLINE_DATE_FROM_OLDER_TO_NEWER) {
            orderType = "ASC";
        } else {
            orderType = "DESC";
        }

        if (sortCriteria == OrderSortCriteria.DEADLINE_DATE_FROM_NEWER_TO_OLDER || sortCriteria == OrderSortCriteria.DEADLINE_DATE_FROM_OLDER_TO_NEWER) {
            orderCriteria = "deadlineDate";
        } else {
            orderCriteria = "creationDate";
        }

        QueryBuilder builder = new QueryBuilder();
        String sql = builder.select("*").from(ORDER_JOIN_EXPR).
                             where(conditions.toString()).
                             order(orderCriteria, orderType).
                             limit((page-1)*recordsPerPage, recordsPerPage).build();

        try (Connection con = connectionPool.getConnection();
             Statement stmt = con.createStatement()) {

            ResultSet set = stmt.executeQuery(sql);
            List<Order> result = ResultSetExtractor.extractOrders(set);
            set.close();
            return result;
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }

    @Override
    public int countOrders(Integer code, Integer userId, Integer bookId, OrderState state) throws DatabaseException {
        StringJoiner conditions = new StringJoiner(" AND ");
        if (code != null) conditions.add("orders.code = "+code);
        if (userId != null) conditions.add("orders.userId = "+userId);
        if (bookId != null) conditions.add("orders.bookId = "+bookId);
        if (state != null) conditions.add("orders.state = "+state.toInt());
        conditions.add("orders.state <> "+OrderState.DELETED.toInt());

        QueryBuilder builder = new QueryBuilder();
        String sql = builder.select("COUNT(*)").from("orders").where(conditions.toString()).build();

        try (Connection con = connectionPool.getConnection();
             Statement stmt = con.createStatement()) {

            ResultSet set = stmt.executeQuery(sql);
            set.next();
            int count = set.getInt(1);
            set.close();
            return count;
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }

    @Override
    public void createOrder(Order order, Connection connection) throws DatabaseException {
        try (PreparedStatement stmt = connection.prepareStatement(CREATE_ORDER_SQL, Statement.RETURN_GENERATED_KEYS)) {

            int pos = 1;
            stmt.setInt(pos++, order.getUser().getId());
            stmt.setInt(pos++, order.getBook().getId());
            stmt.setInt(pos++, order.getState().toInt());
            stmt.setDate(pos++, Objects.isNull(order.getCreationDate())? null : Date.valueOf(order.getCreationDate()));
            stmt.setDate(pos, Objects.isNull(order.getDeadlineDate())? null : Date.valueOf(order.getDeadlineDate()));

            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            order.setCode(rs.getInt(1));
            rs.close();
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }

    @Override
    public void updateOrder(Order order, Connection connection) throws DatabaseException {
        try (PreparedStatement stmt = connection.prepareStatement(UPDATE_ORDER_SQL)) {

            int pos = 1;
            stmt.setInt(pos++, order.getUser().getId());
            stmt.setInt(pos++, order.getBook().getId());
            stmt.setInt(pos++, order.getState().toInt());
            stmt.setDate(pos++, Objects.isNull(order.getCreationDate())? null : Date.valueOf(order.getCreationDate()));
            stmt.setDate(pos++, Objects.isNull(order.getDeadlineDate())? null : Date.valueOf(order.getDeadlineDate()));
            stmt.setInt(pos, order.getCode());

            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }

    @Override
    public boolean existsOrderByUserIdAndBookId(int userId, int bookId) throws DatabaseException {
        try (Connection con = connectionPool.getConnection();
             PreparedStatement stmt = con.prepareStatement(EXISTS_ORDER_BY_USER_ID_AND_BOOK_ID_SQL)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, bookId);
            ResultSet set = stmt.executeQuery();
            set.next();
            boolean exists = set.getBoolean(1);
            set.close();
            return exists;
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }
}
