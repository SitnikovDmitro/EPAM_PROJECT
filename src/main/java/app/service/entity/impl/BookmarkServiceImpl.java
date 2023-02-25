package app.service.entity.impl;

import app.database.dao.BookmarkDAO;
import app.database.transaction.TransactionManager;
import app.enums.TransactionResult;
import app.exceptions.DatabaseException;
import app.service.entity.BookmarkService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BookmarkServiceImpl implements BookmarkService {
    private static final Logger logger = LogManager.getLogger(BookmarkServiceImpl.class);

    private final BookmarkDAO bookmarkDAO;
    private final TransactionManager transactionManager;

    public BookmarkServiceImpl(BookmarkDAO bookmarkDAO, TransactionManager transactionManager) {
        this.bookmarkDAO = bookmarkDAO;
        this.transactionManager = transactionManager;
    }

    @Override
    public void saveBookmarkByUserIdAndBookId(int userId, int bookId) throws DatabaseException {
        logger.debug("Saving bookmark on book with id = "+bookId+" by user with id = "+userId+" started");

        transactionManager.execute(connection -> {
            bookmarkDAO.saveBookmarkByUserIdAndBookId(userId, bookId, connection);
            return TransactionResult.COMMIT;
        });

        logger.debug("Saving bookmark on book with id = "+bookId+" by user with id = "+userId+" successfully finished");
    }

    @Override
    public void deleteBookmarkByUserIdAndBookId(int userId, int bookId) throws DatabaseException {
        logger.debug("Removing bookmark on book with id = "+bookId+" created by user with id = "+userId+" started");

        transactionManager.execute(connection -> {
            bookmarkDAO.deleteBookmarkByUserIdAndBookId(userId, bookId, connection);
            return TransactionResult.COMMIT;
        });

        logger.debug("Removing bookmark on book with id = "+bookId+" created by user with id = "+userId+" successfully finished");
    }

    @Override
    public boolean existsBookmarkByUserIdAndBookId(int userId, int bookId) throws DatabaseException {
        return bookmarkDAO.existsBookmarkByUserIdAndBookId(userId, bookId);
    }
}
