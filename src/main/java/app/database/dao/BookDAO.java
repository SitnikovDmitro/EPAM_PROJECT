package app.database.dao;

import app.database.transaction.TransactionManager;
import app.entity.Book;
import app.enums.BookFormat;
import app.enums.BookGenre;
import app.enums.BookLanguage;
import app.enums.BookSortCriteria;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

/**
 * Provides database operations with table of books
 **/
public interface BookDAO {
    /**
     * Finds book by id
     * @param bookId id of book
     * @return found book entity optional wrapper
     **/
    Optional<Book> findBookById(int bookId) throws DatabaseException;

    /**
     * Find book all books
     * @return list containing all books
     **/
    List<Book> findAll() throws DatabaseException;

    /**
     * Finds books by id of user, ISBN, title, author, format, genre, language ordered by sort criteria
     * @param userId id of user which have bookmark on a book (optional)
     * @param isbn book ISBN (optional)
     * @param format book format (optional)
     * @param genre book genre (optional)
     * @param language book language (optional)
     * @param publisherQuery query string for searching books by publisher title (optional)
     * @param titleQuery query string for searching books by title (optional)
     * @param authorQuery query string for searching books by author (optional)
     * @param criteria sort criteria for ordering books (optional, default sort by alphabet)
     * @param page number of page for which books are being searched
     * @param recordsPerPage max number of books that result list could contain
     * @return list of books matching query parameters
     **/
    List<Book> findBooks(Integer userId, Integer isbn, String publisherQuery, String titleQuery, String authorQuery, BookGenre genre, BookLanguage language, BookFormat format, BookSortCriteria criteria, int page, int recordsPerPage) throws DatabaseException;

    /**
     * Count books by id of user, ISBN, title, author, format, genre, language
     * @param userId id of user which have bookmark on a book (optional)
     * @param isbn book ISBN (optional)
     * @param format book format (optional)
     * @param genre book genre (optional)
     * @param language book language (optional)
     * @param publisherQuery query string for searching books by publisher title (optional)
     * @param titleQuery query string for searching books by title (optional)
     * @param authorQuery query string for searching books by author (optional)
     * @return number of books matching query parameters
     **/
    int countBooks(Integer userId, Integer isbn, String publisherQuery, String titleQuery, String authorQuery, BookGenre genre, BookLanguage language, BookFormat format) throws DatabaseException;

    /**
     * Creates new book. Used with {@link TransactionManager} in
     * {@link app.database.transaction.TransactionExecutor#execute(Connection)}
     * @param book book to create
     * @param connection connection with database
     **/
    void createBook(Book book, Connection connection) throws DatabaseException;

    /**
     * Updates existing book. Used with {@link TransactionManager} in
     * {@link app.database.transaction.TransactionExecutor#execute(Connection)}
     * @param book book to update
     * @param connection connection with database
     **/
    void updateBook(Book book, Connection connection) throws DatabaseException;

    /**
     * Checks if books with given isbn exists
     * @param isbn isbn of book
     * @return true if book with given isbn exists, false otherwise
     **/
    boolean existsBookByIsbn(int isbn) throws DatabaseException;
}
