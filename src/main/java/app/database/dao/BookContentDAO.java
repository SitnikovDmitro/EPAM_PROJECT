package app.database.dao;

import app.exceptions.DatabaseException;

/**
 * Provides database operations with table of books content (contains book content data as pdf documents in blobs)
 **/
public interface BookContentDAO {
    /**
     * Finds book content by book id
     * @param bookId id of book
     * @return book content pdf document data in byte array
     **/
    byte[] findBookContentByBookId(int bookId) throws DatabaseException;

    /**
     * Saves book cover by book id
     * @param bookId id of book
     * @param data book content pdf document data in byte array
     **/
    void saveBookContent(int bookId, byte[] data) throws DatabaseException;
}
