package app.dao;

import app.database.dao.BookmarkDAO;
import app.database.dao.impl.BookmarkDAOImpl;
import app.database.pool.ConnectionPool;
import app.database.pool.impl.H2ConnectionPoolImpl;

import app.database.transaction.TransactionManager;
import app.database.transaction.impl.TransactionManagerImpl;
import app.enums.TransactionResult;
import app.database.initialization.DatabaseInitializer;
import app.exceptions.DatabaseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BookmarkDAOTest {
    private final BookmarkDAO bookmarkDAO;
    private final TransactionManager transactionManager;

    public BookmarkDAOTest() {
        ConnectionPool connectionPool = H2ConnectionPoolImpl.getInstance();

        bookmarkDAO = new BookmarkDAOImpl(connectionPool);
        transactionManager = new TransactionManagerImpl(connectionPool);
    }

    @BeforeEach
    public void initialize() throws DatabaseException {
        DatabaseInitializer.getInstance().initialize();
    }
    

    @Test
    public void existsBookmarkByUserIdAndBookIdTestCase1() throws Exception {
        Assertions.assertTrue(bookmarkDAO.existsBookmarkByUserIdAndBookId(6, 12));
        Assertions.assertTrue(bookmarkDAO.existsBookmarkByUserIdAndBookId(6, 13));
        Assertions.assertTrue(bookmarkDAO.existsBookmarkByUserIdAndBookId(6, 14));
        Assertions.assertTrue(bookmarkDAO.existsBookmarkByUserIdAndBookId(6, 15));
        Assertions.assertTrue(bookmarkDAO.existsBookmarkByUserIdAndBookId(6, 16));
        Assertions.assertTrue(bookmarkDAO.existsBookmarkByUserIdAndBookId(6, 17));
        Assertions.assertTrue(bookmarkDAO.existsBookmarkByUserIdAndBookId(6, 18));
    }

    @Test
    public void existsBookmarkByUserIdAndBookIdTestCase2() throws Exception {
        Assertions.assertFalse(bookmarkDAO.existsBookmarkByUserIdAndBookId(7, 12));
        Assertions.assertFalse(bookmarkDAO.existsBookmarkByUserIdAndBookId(7, 13));
        Assertions.assertFalse(bookmarkDAO.existsBookmarkByUserIdAndBookId(7, 14));
        Assertions.assertFalse(bookmarkDAO.existsBookmarkByUserIdAndBookId(7, 15));
        Assertions.assertFalse(bookmarkDAO.existsBookmarkByUserIdAndBookId(7, 16));
        Assertions.assertFalse(bookmarkDAO.existsBookmarkByUserIdAndBookId(7, 17));
        Assertions.assertFalse(bookmarkDAO.existsBookmarkByUserIdAndBookId(7, 18));
        Assertions.assertFalse(bookmarkDAO.existsBookmarkByUserIdAndBookId(8, 1));
        Assertions.assertFalse(bookmarkDAO.existsBookmarkByUserIdAndBookId(9, 2));
        Assertions.assertFalse(bookmarkDAO.existsBookmarkByUserIdAndBookId(9, 2));
        Assertions.assertFalse(bookmarkDAO.existsBookmarkByUserIdAndBookId(9, 20));
    }

    @Test
    public void createBookmarkByUserIdAndBookIdTest() throws Exception {
        Assertions.assertFalse(bookmarkDAO.existsBookmarkByUserIdAndBookId(7, 12));

        transactionManager.execute(connection -> {
            bookmarkDAO.saveBookmarkByUserIdAndBookId(7, 12, connection);
            return TransactionResult.COMMIT;
        });

        Assertions.assertTrue(bookmarkDAO.existsBookmarkByUserIdAndBookId(7, 12));

        Assertions.assertFalse(bookmarkDAO.existsBookmarkByUserIdAndBookId(8, 13));

        transactionManager.execute(connection -> {
            bookmarkDAO.saveBookmarkByUserIdAndBookId(8, 13, connection);
            return TransactionResult.COMMIT;
        });

        Assertions.assertTrue(bookmarkDAO.existsBookmarkByUserIdAndBookId(8, 13));
    }

    @Test
    public void deleteBookmarkByUserIdAndBookIdTest() throws Exception {

        transactionManager.execute(connection -> {
            bookmarkDAO.saveBookmarkByUserIdAndBookId(7, 12, connection);
            return TransactionResult.COMMIT;
        });

        Assertions.assertTrue(bookmarkDAO.existsBookmarkByUserIdAndBookId(7, 12));

        transactionManager.execute(connection -> {
            bookmarkDAO.deleteBookmarkByUserIdAndBookId(7, 12, connection);
            return TransactionResult.COMMIT;
        });

        Assertions.assertFalse(bookmarkDAO.existsBookmarkByUserIdAndBookId(7, 12));


        transactionManager.execute(connection -> {
            bookmarkDAO.saveBookmarkByUserIdAndBookId(7, 13, connection);
            return TransactionResult.COMMIT;
        });

        Assertions.assertTrue(bookmarkDAO.existsBookmarkByUserIdAndBookId(7, 13));

        transactionManager.execute(connection -> {
            bookmarkDAO.deleteBookmarkByUserIdAndBookId(7, 13, connection);
            return TransactionResult.COMMIT;
        });

        Assertions.assertFalse(bookmarkDAO.existsBookmarkByUserIdAndBookId(7, 13));

    }
}
