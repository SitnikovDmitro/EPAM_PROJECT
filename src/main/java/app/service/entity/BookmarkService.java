package app.service.entity;

import app.exceptions.DatabaseException;

import java.sql.SQLException;

/**
 * Provides operations with bookmark entity
 **/
public interface BookmarkService {
    /**
     * Creates new bookmark or does not anything if such bookmark already exists
     * @param userId id of user
     * @param bookId id of book
     **/
    void saveBookmarkByUserIdAndBookId(int userId, int bookId) throws DatabaseException;

    /**
     * Deletes existing bookmark if it exists and does not anything otherwise
     * @param userId id of user
     * @param bookId id of book
     **/
    void deleteBookmarkByUserIdAndBookId(int userId, int bookId) throws DatabaseException;

    /**
     * Returns true if bookmark by user id and book id exists and false otherwise
     * @param userId id of user
     * @param bookId id of book
     **/
    boolean existsBookmarkByUserIdAndBookId(int userId, int bookId) throws DatabaseException;
}
