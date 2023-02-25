package app.service.entity;

import app.entity.Book;
import app.enums.BookFormat;
import app.enums.BookGenre;
import app.enums.BookLanguage;
import app.enums.BookSortCriteria;
import app.exceptions.DatabaseException;
import app.exceptions.FileException;
import app.tuples.BookCreationEditionResult;

import javax.servlet.http.Part;
import java.util.List;
import java.util.Optional;

/**
 * Provides operations with book entity
 **/
public interface BookService {
    /**
     * Finds book by id
     * @param bookId id of book
     * @return found book entity optional wrapper
     **/
    Optional<Book> findBookById(int bookId) throws DatabaseException;

    /**
     * Finds books by id of user, ISBN, title, author, format, genre, language ordered by sort criteria
     * @param userId id of user which have bookmark on a book (optional)
     * @param isbn book ISBN (optional)
     * @param format book format (optional)
     * @param genre book genre (optional)
     * @param language book language (optional)
     * @param publisherQuery query string for searching books by publisher (optional)
     * @param titleQuery query string for searching books by title (optional)
     * @param authorQuery query string for searching books by author (optional)
     * @param sortCriteria sort criteria for ordering books (optional, default sort by alphabet)
     * @param page pagination parameter
     * @param size pagination parameter
     * @return list of books matching query parameters
     **/
    List<Book> findBooks1(Integer userId, Integer isbn, String publisherQuery, String titleQuery, String authorQuery, BookSortCriteria sortCriteria, BookFormat format, BookGenre genre, BookLanguage language, int page, int size) throws DatabaseException;

    /**
     * Finds books
     * @see BookService#findBooks1(Integer, Integer, String, String, String, BookSortCriteria, BookFormat, BookGenre, BookLanguage, int, int)
     **/
    List<Book> findBooks2(Integer isbn, String publisherQuery, String titleQuery, String authorQuery, BookSortCriteria sortCriteria, BookFormat format, BookGenre genre, BookLanguage language, int page, int size) throws DatabaseException;

    /**
     * Finds books
     * @see BookService#findBooks1(Integer, Integer, String, String, String, BookSortCriteria, BookFormat, BookGenre, BookLanguage, int, int)
     **/
    List<Book> findBooks3(Integer userId, String query, int page, int size) throws DatabaseException;

    /**
     * Finds books
     * @see BookService#findBooks1(Integer, Integer, String, String, String, BookSortCriteria, BookFormat, BookGenre, BookLanguage, int, int)
     **/
    List<Book> findBooks4(String query, int page, int size) throws DatabaseException;

    /**
     * Finds books
     * @see BookService#findBooks1(Integer, Integer, String, String, String, BookSortCriteria, BookFormat, BookGenre, BookLanguage, int, int)
     **/
    List<Book> findBooks5(Integer userId, int page, int size) throws DatabaseException;

    /**
     * Counts books by id of user, ISBN, title, author, format, genre, language
     * @param userId id of user which have bookmark on a book (optional)
     * @param isbn book ISBN (optional)
     * @param format book format (optional)
     * @param genre book genre (optional)
     * @param language book language (optional)
     * @param titleQuery query string for searching books by title (optional)
     * @param authorQuery query string for searching books by author (optional)
     * @return number of books matching query parameters
     **/
    int countBooks(Integer userId, Integer isbn, String publisherQuery, String titleQuery, String authorQuery, BookFormat format, BookGenre genre, BookLanguage language) throws DatabaseException;

    /**
     * Finds books
     * @see BookService#countBooks(Integer, String, String, String, BookSortCriteria, BookFormat, BookGenre, BookLanguage)
     **/
    int countBooks(Integer isbn, String publisherQuery, String titleQuery, String authorQuery, BookSortCriteria sortCriteria, BookFormat format, BookGenre genre, BookLanguage language) throws DatabaseException;

    /**
     * Finds books
     * @see BookService#countBooks(Integer, String, String, String, BookSortCriteria, BookFormat, BookGenre, BookLanguage)
     **/
    int countBooks(Integer userId, String query) throws DatabaseException;

    /**
     * Finds books
     * @see BookService#countBooks(Integer, String, String, String, BookSortCriteria, BookFormat, BookGenre, BookLanguage)
     **/
    int countBooks(String query) throws DatabaseException;

    /**
     * Finds books
     * @see BookService#countBooks(Integer, String, String, String, BookSortCriteria, BookFormat, BookGenre, BookLanguage)
     **/
    int countBooks(Integer userId) throws DatabaseException;

    /**
     * Creates book
     * @return result tuple containing validation information if operation is not success and book entity otherwise
     **/
    BookCreationEditionResult createBook(String isbn, String title, String author, String description, String publicationDate, String totalCopiesNumber, String genre, String language, Boolean isValuable, String publisherTitle, byte[] cover, byte[] content) throws FileException, DatabaseException;

    /**
     * Edits book
     * @return result tuple containing validation information if operation is not success and book entity otherwise
     **/
    BookCreationEditionResult editBook(Integer id, String isbn, String title, String author, String description, String publicationDate, String totalCopiesNumber, String genre, String language, String publisherTitle, byte[] cover, byte[] content) throws FileException, DatabaseException;

    /**
     * Deletes book by id
     * @param bookId id of book
     **/
    void deleteBook(Integer bookId) throws DatabaseException;
}
