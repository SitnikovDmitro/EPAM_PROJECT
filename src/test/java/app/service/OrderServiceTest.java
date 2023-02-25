package app.service;

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
import app.enums.OrderState;
import app.service.entity.OrderService;
import app.service.entity.impl.OrderServiceImpl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class OrderServiceTest {
    BookDAO bookDAO;
    UserDAO userDAO;
    OrderDAO orderDAO;

    OrderService orderService;

    public OrderServiceTest() {
        ConnectionPool connectionPool = H2ConnectionPoolImpl.getInstance();

        BookDAO bookDAO = new BookDAOImpl(connectionPool);
        UserDAO userDAO = new UserDAOImpl(connectionPool);
        orderDAO = new OrderDAOImpl(connectionPool);
        TransactionManager transactionManager = new TransactionManagerImpl(H2ConnectionPoolImpl.getInstance());

        orderService = new OrderServiceImpl(userDAO, bookDAO, orderDAO, transactionManager);
    }

    @BeforeEach
    public void initialize() throws SQLException {
        Connection connection = Mockito.mock(Connection.class);
        ConnectionPool connectionPool = Mockito.mock(ConnectionPool.class);

        Mockito.doReturn(connection).when(connectionPool).getConnection();

        TransactionManager transactionManager = new TransactionManagerImpl(connectionPool);

        bookDAO = Mockito.mock(BookDAO.class);
        userDAO = Mockito.mock(UserDAO.class);
        orderDAO = Mockito.mock(OrderDAO.class);

        orderService = new OrderServiceImpl(userDAO, bookDAO, orderDAO, transactionManager);
    }

    @Test
    public void findOrdersTest() throws Exception {
        List list = Mockito.mock(List.class);

        Mockito.doReturn(9).when(list).size();

        Mockito.doReturn(list).when(orderDAO).findOrders(null, null, null, null, null, 1, 9);

        Assertions.assertEquals(9, orderService.findOrders(null, 1, 9).size());
        Assertions.assertEquals(9, orderService.findOrders(null, null, null, null, null, 1, 9).size());
    }

    @Test
    public void countOrdersTest() throws Exception {
        Mockito.doReturn(35).when(orderDAO).countOrders(null, null, null, null);

        Assertions.assertEquals(35, orderService.countOrders(null));
        Assertions.assertEquals(35, orderService.countOrders(null, null, null, null));
    }

    /*@Test
    public void confirmOrderTest() throws Exception {
        Order order = Mockito.mock(Order.class);
        Mockito.doReturn(OrderState.WAITING_FOR_CONFIRMATION).when(order).getState();
        Mockito.doReturn(OrderState.WAITING_FOR_CONFIRMATION).when(order).getBook();
        Mockito.doReturn(Optional.of(order)).when(orderDAO).findOrderByCode(16);

        orderService.confirmOrder(16, LocalDate.now().plusYears(1));

        Mockito.verify(orderDAO, Mockito.times(1)).updateOrder(Mockito.any(Order.class), Mockito.any(Connection.class));
    }*/


    @Test
    public void cancelOrderTest() throws Exception {
        Order order = Mockito.mock(Order.class);
        Mockito.doReturn(OrderState.WAITING_FOR_CONFIRMATION).when(order).getState();
        Mockito.doReturn(Optional.of(order)).when(orderDAO).findOrderByCode(16);

        orderService.cancelOrder(16);

        Mockito.verify(orderDAO, Mockito.times(1)).updateOrder(Mockito.any(Order.class), Mockito.any(Connection.class));
    }

    @Test
    public void closeOrderTest() throws Exception {
        Order order = Mockito.mock(Order.class);
        Book book = Mockito.mock(Book.class);
        Mockito.doReturn(12).when(book).getFreeCopiesNumber();
        Mockito.doReturn(13).when(book).getTotalCopiesNumber();
        Mockito.doReturn(OrderState.CONFIRMED).when(order).getState();
        Mockito.doReturn(book).when(order).getBook();
        Mockito.doReturn(Optional.of(order)).when(orderDAO).findOrderByCode(1);

        orderService.closeOrder(1);

        Mockito.verify(orderDAO, Mockito.times(1)).updateOrder(Mockito.any(Order.class), Mockito.any(Connection.class));
    }

    @Test
    public void deleteOrderTest() throws Exception {
        Order order = Mockito.mock(Order.class);
        Mockito.doReturn(OrderState.CLOSED).when(order).getState();
        Mockito.doReturn(Optional.of(order)).when(orderDAO).findOrderByCode(12);

        orderService.deleteOrder(12);

        Mockito.verify(orderDAO, Mockito.times(1)).updateOrder(Mockito.any(Order.class), Mockito.any(Connection.class));
    }
}
