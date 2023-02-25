package app.database.dao;

import app.database.transaction.TransactionManager;
import app.exceptions.DatabaseException;

import java.sql.Connection;

/**
 * Provides database operations with table of bookmarks
 **/
public interface BookmarkDAO {
    /**
     * Creates new bookmark or does not anything if such bookmark already exists
     * Used with {@link TransactionManager} in
     * {@link app.database.transaction.TransactionExecutor#execute(Connection)}
     * @param userId id of user
     * @param bookId id of book
     **/
    void saveBookmarkByUserIdAndBookId(int userId, int bookId, Connection connection) throws DatabaseException;

    /**
     * Deletes existing bookmark if it exists and does not anything otherwise.
     * Used with {@link TransactionManager} in
     * {@link app.database.transaction.TransactionExecutor#execute(Connection)}
     * @param userId id of user
     * @param bookId id of book
     **/
    void deleteBookmarkByUserIdAndBookId(int userId, int bookId, Connection connection) throws DatabaseException;

    /**
     * Returns true if bookmark by user id and book id exists and false otherwise
     * @param userId id of user
     * @param bookId id of book
     **/
    boolean existsBookmarkByUserIdAndBookId(int userId, int bookId) throws DatabaseException;
}
