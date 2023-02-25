package app.dao;

import app.database.dao.BookDAO;
import app.database.dao.FeedbackDAO;
import app.database.dao.UserDAO;
import app.database.dao.impl.BookDAOImpl;
import app.database.dao.impl.FeedbackDAOImpl;
import app.database.dao.impl.UserDAOImpl;
import app.database.pool.ConnectionPool;
import app.database.pool.impl.H2ConnectionPoolImpl;
import app.database.transaction.TransactionManager;
import app.database.transaction.impl.TransactionManagerImpl;
import app.entity.Book;
import app.entity.Feedback;
import app.entity.User;
import app.enums.TransactionResult;
import app.database.initialization.DatabaseInitializer;
import app.exceptions.DatabaseException;
import app.utils.DeepEqualUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

public class FeedbackDAOTest {
    private final FeedbackDAO feedbackDAO;
    private final UserDAO userDAO;
    private final BookDAO bookDAO;
    private final TransactionManager transactionManager;

    public FeedbackDAOTest() {
        ConnectionPool connectionPool = H2ConnectionPoolImpl.getInstance();

        feedbackDAO = new FeedbackDAOImpl(connectionPool);
        userDAO = new UserDAOImpl(connectionPool);
        bookDAO = new BookDAOImpl(connectionPool);
        transactionManager = new TransactionManagerImpl(connectionPool);
    }

    @BeforeEach
    public void initialize() throws DatabaseException {
        DatabaseInitializer.getInstance().initialize();
    }



    @Test
    public void findFeedbackByBookIdTestCase1() throws Exception {
        List<Feedback> feedbacks = feedbackDAO.findFeedbacksByBookId(1, Integer.MAX_VALUE);
        Book book = bookDAO.findBookById(1).orElseThrow();

        Assertions.assertEquals(feedbacks.size(), 6);

        for (Feedback feedback : feedbacks) {
            User user = userDAO.findUserById(feedback.getUser().getId()).orElseThrow();

            Assertions.assertTrue(DeepEqualUtil.getInstance().equals(feedback.getBook(), book));
            Assertions.assertTrue(DeepEqualUtil.getInstance().equals(feedback.getUser(), user));
        }
    }

    @Test
    public void findFeedbackByBookIdTestCase2() throws Exception {
        List<Feedback> feedbacks = feedbackDAO.findFeedbacksByBookId(1, Integer.MAX_VALUE);
        feedbacks.removeIf(feedback -> feedback.getUser().getId() != 5);

        Assertions.assertEquals(feedbacks.size(), 1);

        Feedback feedbackA = feedbacks.get(0);
        Book book = bookDAO.findBookById(1).orElseThrow();
        User user = userDAO.findUserById(5).orElseThrow();
        Feedback feedbackB = new Feedback(user, book, 3, "I know things are stressful right now, but thanks to you, I think everything’s going to slow down soon. When it does, I’m taking you out to happy hour to thank you for everything you’ve done to get us out of this situation.", LocalDate.of(2022, 3, 8));

        Assertions.assertTrue(DeepEqualUtil.getInstance().equals(feedbackA, feedbackB));
    }

    @Test
    public void saveFeedbackTestCase1() throws Exception {
        Book book = bookDAO.findBookById(1).orElseThrow();
        User user = userDAO.findUserById(5).orElseThrow();
        Feedback feedbackB = new Feedback(user, book, 4, "Text", LocalDate.of(2021, 3, 8));

        transactionManager.execute(connection -> {
            feedbackDAO.saveFeedback(feedbackB, connection);
            return TransactionResult.COMMIT;
        });

        List<Feedback> feedbacks = feedbackDAO.findFeedbacksByBookId(1, Integer.MAX_VALUE);
        feedbacks.removeIf(feedback -> feedback.getUser().getId() != 5);
        Assertions.assertEquals(feedbacks.size(), 1);
        Feedback feedbackA = feedbacks.get(0);

        Assertions.assertTrue(DeepEqualUtil.getInstance().equals(feedbackA, feedbackB));
    }

    @Test
    public void saveFeedbackTestCase2() throws Exception {
        Book book = bookDAO.findBookById(1).orElseThrow();
        User user = userDAO.findUserById(11).orElseThrow();
        Feedback feedbackB = new Feedback(user, book, 4, "Text", LocalDate.of(2021, 3, 8));

        transactionManager.execute(connection -> {
            feedbackDAO.saveFeedback(feedbackB, connection);
            return TransactionResult.COMMIT;
        });

        List<Feedback> feedbacks = feedbackDAO.findFeedbacksByBookId(1, Integer.MAX_VALUE);
        feedbacks.removeIf(feedback -> feedback.getUser().getId() != 11);
        Assertions.assertEquals(feedbacks.size(), 1);
        Feedback feedbackA = feedbacks.get(0);

        Assertions.assertTrue(DeepEqualUtil.getInstance().equals(feedbackA, feedbackB));
    }
}
