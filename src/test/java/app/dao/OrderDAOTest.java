package app.dao;

import app.database.dao.BookDAO;
import app.database.dao.OrderDAO;
import app.database.dao.UserDAO;
import app.database.dao.impl.BookDAOImpl;
import app.database.dao.impl.OrderDAOImpl;
import app.database.dao.impl.UserDAOImpl;
import app.database.pool.ConnectionPool;
import app.database.pool.impl.H2ConnectionPoolImpl;
import app.database.transaction.TransactionManager;
import app.database.transaction.impl.TransactionManagerImpl;
import app.entity.Book;
import app.entity.Order;
import app.entity.User;
import app.enums.OrderState;
import app.enums.TransactionResult;
import app.database.initialization.DatabaseInitializer;
import app.exceptions.DatabaseException;
import app.utils.DeepEqualUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

public class OrderDAOTest {
    private final OrderDAO orderDAO;
    private final UserDAO userDAO;
    private final BookDAO bookDAO;
    private final TransactionManager transactionManager;

    public OrderDAOTest() {
        ConnectionPool connectionPool = H2ConnectionPoolImpl.getInstance();

        orderDAO = new OrderDAOImpl(connectionPool);
        userDAO = new UserDAOImpl(connectionPool);
        bookDAO = new BookDAOImpl(connectionPool);
        transactionManager = new TransactionManagerImpl(connectionPool);
    }

    @BeforeEach
    public void initialize() throws DatabaseException {
        DatabaseInitializer.getInstance().initialize();
    }

    @Test
    public void findOrderByCodeTestCase1() throws Exception {
        Order orderA = orderDAO.findOrderByCode(1).orElseThrow();
        Book bookB = bookDAO.findBookById(12).orElseThrow();
        User userB = userDAO.findUserById(6).orElseThrow();
        Order orderB = new Order(1, userB, bookB, OrderState.CONFIRMED, LocalDate.of(2023, 1, 1), LocalDate.of(2023, 4, 1));

        Assertions.assertTrue(DeepEqualUtil.getInstance().equals(orderA, orderB));
    }

    @Test
    public void findOrderByCodeTestCase2() throws Exception {
        Order orderA = orderDAO.findOrderByCode(16).orElseThrow();
        Book bookB = bookDAO.findBookById(14).orElseThrow();
        User userB = userDAO.findUserById(8).orElseThrow();
        Order orderB = new Order(16, userB, bookB, OrderState.WAITING_FOR_CONFIRMATION, null, null);

        Assertions.assertTrue(DeepEqualUtil.getInstance().equals(orderA, orderB));
    }

    @Test
    public void createOrderTestCase1() throws Exception {
        Order orderA = new Order(0, userDAO.findUserById(10).orElseThrow(), bookDAO.findBookById(1).orElseThrow(), OrderState.CONFIRMED, LocalDate.of(2023, 1, 1), LocalDate.of(2024, 4, 1));

        transactionManager.execute(connection -> {
            orderDAO.createOrder(orderA, connection);
            return TransactionResult.COMMIT;
        });

        Order orderB = orderDAO.findOrderByCode(orderA.getCode()).orElseThrow();

        Assertions.assertTrue(orderA.getCode() != 0);
        Assertions.assertTrue(DeepEqualUtil.getInstance().equals(orderA, orderB));
    }

    @Test
    public void createOrderTestCase2() throws Exception {
        Order orderA = new Order(0, userDAO.findUserById(10).orElseThrow(), bookDAO.findBookById(2).orElseThrow(), OrderState.WAITING_FOR_CONFIRMATION, null, null);

        transactionManager.execute(connection -> {
            orderDAO.createOrder(orderA, connection);
            return TransactionResult.COMMIT;
        });

        Order orderB = orderDAO.findOrderByCode(orderA.getCode()).orElseThrow();

        Assertions.assertTrue(orderA.getCode() != 0);
        Assertions.assertTrue(DeepEqualUtil.getInstance().equals(orderA, orderB));
    }

    @Test
    public void findOrdersTestCase1() throws Exception {
        List<Order> orders = orderDAO.findOrders(null, null, null, null, null, 1, 1000);

        Assertions.assertEquals(orders.size(), 35);
    }

    @Test
    public void findOrdersTestCase2() throws Exception {
        List<Order> orders = orderDAO.findOrders(1, null, null, null, null, 1, 1000);
        Order order = orderDAO.findOrderByCode(1).orElseThrow();

        Assertions.assertTrue(DeepEqualUtil.getInstance().equals(orders.get(0), order));
    }

    @Test
    public void findOrdersTestCase3() throws Exception {
        List<Order> orders = orderDAO.findOrders(16, null, null, null, null, 1, 1000);
        Order order = orderDAO.findOrderByCode(16).orElseThrow();

        Assertions.assertTrue(DeepEqualUtil.getInstance().equals(orders.get(0), order));
    }

    @Test
    public void findOrdersTestCase4() throws Exception {
        List<Order> orders = orderDAO.findOrders(null, 6, null, null, null, 1, 1000);

        Assertions.assertEquals(orders.size(), 7);
        Assertions.assertTrue(orders.stream().allMatch(order -> order.getUser().getId() == 6));
    }

    @Test
    public void findOrdersTestCase5() throws Exception {
        List<Order> orders = orderDAO.findOrders(null, 7, null, null, null, 1, 1000);

        Assertions.assertEquals(orders.size(), 5);
        Assertions.assertTrue(orders.stream().allMatch(order -> order.getUser().getId() == 7));
    }

    @Test
    public void findOrdersTestCase6() throws Exception {
        List<Order> orders = orderDAO.findOrders(null, null, 14, null, null, 1, 1000);

        Assertions.assertEquals(orders.size(), 4);
        Assertions.assertTrue(orders.stream().allMatch(order -> order.getBook().getId() == 14));
    }

    @Test
    public void findOrdersTestCase7() throws Exception {
        List<Order> orders = orderDAO.findOrders(null, null, null, OrderState.CONFIRMED, null, 1, 1000);

        Assertions.assertEquals(orders.size(), 7);
        Assertions.assertTrue(orders.stream().allMatch(order -> order.getState() == OrderState.CONFIRMED));
    }

    @Test
    public void countOrdersTest() throws Exception {
        Assertions.assertEquals(orderDAO.countOrders(null, null, null, OrderState.CONFIRMED), 7);
    }
}
