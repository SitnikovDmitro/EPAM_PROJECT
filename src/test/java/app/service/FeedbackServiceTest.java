package app.service;

import app.database.dao.BookDAO;
import app.database.dao.FeedbackDAO;
import app.database.dao.UserDAO;
import app.database.pool.ConnectionPool;

import app.database.transaction.TransactionManager;
import app.database.transaction.impl.TransactionManagerImpl;
import app.entity.Book;
import app.entity.Feedback;
import app.entity.User;
import app.enums.UserRole;
import app.exceptions.InvalidStateException;
import app.service.entity.FeedbackService;
import app.service.entity.impl.FeedbackServiceImpl;
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

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class FeedbackServiceTest {
    BookDAO bookDAO;
    UserDAO userDAO;
    FeedbackDAO feedbackDAO;

    FeedbackService feedbackService;

    @BeforeEach
    public void initialize() throws SQLException {
        Connection connection = Mockito.mock(Connection.class);
        ConnectionPool connectionPool = Mockito.mock(ConnectionPool.class);

        Mockito.doReturn(connection).when(connectionPool).getConnection();

        TransactionManager transactionManager = new TransactionManagerImpl(connectionPool);

        bookDAO = Mockito.mock(BookDAO.class);
        userDAO = Mockito.mock(UserDAO.class);
        feedbackDAO = Mockito.mock(FeedbackDAO.class);

        feedbackService = new FeedbackServiceImpl(userDAO, bookDAO, feedbackDAO, transactionManager);
    }




    @Test
    public void createFeedbackTestCase1() throws Exception {
        Book book = Mockito.mock(Book.class);
        Mockito.doReturn(Optional.of(book)).when(bookDAO).findBookById(1);

        User user3 = Mockito.mock(User.class);
        Mockito.doReturn(UserRole.READER).when(user3).getRole();
        Mockito.doReturn(Optional.of(user3)).when(userDAO).findUserById(5);

        feedbackService.createFeedback(1, 5, "Good book", 4);

        Mockito.verify(feedbackDAO, Mockito.times(1)).saveFeedback(Mockito.any(Feedback.class), Mockito.any(Connection.class));
    }

    @Test
    public void createFeedbackTestCase2() throws Exception {
        Book book = Mockito.mock(Book.class);
        Mockito.doReturn(Optional.of(book)).when(bookDAO).findBookById(1);

        User user1 = Mockito.mock(User.class);
        Mockito.doReturn(UserRole.ADMIN).when(user1).getRole();
        Mockito.doReturn(Optional.of(user1)).when(userDAO).findUserById(1);

        User user2 = Mockito.mock(User.class);
        Mockito.doReturn(UserRole.LIBRARIAN).when(user2).getRole();
        Mockito.doReturn(Optional.of(user2)).when(userDAO).findUserById(2);

        User user3 = Mockito.mock(User.class);
        Mockito.doReturn(UserRole.READER).when(user3).getRole();
        Mockito.doReturn(Optional.of(user3)).when(userDAO).findUserById(5);


        Assertions.assertThrows(InvalidStateException.class, () -> {
            feedbackService.createFeedback(1, 5, "Good book", 7);
        });

        Assertions.assertThrows(InvalidStateException.class, () -> {
            feedbackService.createFeedback(1, 5, "Good book", -1);
        });

        Assertions.assertThrows(InvalidStateException.class, () -> {
            feedbackService.createFeedback(1, 1, "Good book", 5);
        });

        Assertions.assertThrows(InvalidStateException.class, () -> {
            feedbackService.createFeedback(1, 2, "Good book", 5);
        });
    }
}
