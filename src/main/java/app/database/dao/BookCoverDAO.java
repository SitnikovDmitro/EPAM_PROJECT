package app.database.dao;

import app.exceptions.DatabaseException;

/**
 * Provides database operations with table of books covers (contains book cover image data in blobs)
 **/
public interface BookCoverDAO {
    /**
     * Finds book cover by book id
     * @param bookId id of book
     * @return book cover image data in byte array
     **/
    byte[] findBookCoverByBookId(int bookId) throws DatabaseException;

    /**
     * Saves book cover by book id
     * @param bookId id of book
     * @param data book cover image data in byte array
     **/
    void saveBookCover(int bookId, byte[] data) throws DatabaseException;
}
