package app.dao;

import app.database.dao.BookCoverDAO;
import app.database.dao.impl.BookCoverDAOImpl;
import app.database.pool.ConnectionPool;
import app.database.pool.impl.H2ConnectionPoolImpl;
import app.database.initialization.DatabaseInitializer;
import app.exceptions.DatabaseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BookCoverDAOTest  {
    private final BookCoverDAO bookCoverDAO;

    public BookCoverDAOTest() {
        ConnectionPool connectionPool = H2ConnectionPoolImpl.getInstance();

        bookCoverDAO = new BookCoverDAOImpl(connectionPool);
    }

    @BeforeEach
    public void initialize() throws DatabaseException {
        DatabaseInitializer.getInstance().initialize();
    }

    @Test
    public void saveAndFindBookContentTestCase1() throws Exception {
        byte[] bytes = {9, 8, 7, 6, 5, 4, 3, 2, 1, 0};
        bookCoverDAO.saveBookCover(1, bytes);

        Assertions.assertArrayEquals(bytes, bookCoverDAO.findBookCoverByBookId(1));
    }

    @Test
    public void saveAndFindBookContentTestCase2() throws Exception {
        byte[] bytes = {1, 2, 3, 4, 5, 6, 7, 8, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0};
        bookCoverDAO.saveBookCover(2, bytes);

        Assertions.assertArrayEquals(bytes, bookCoverDAO.findBookCoverByBookId(2));
        Assertions.assertNull(bookCoverDAO.findBookCoverByBookId(100));
    }
}
