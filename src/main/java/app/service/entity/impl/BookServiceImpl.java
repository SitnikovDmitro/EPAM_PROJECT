package app.service.entity.impl;

import app.database.dao.BookDAO;
import app.database.dao.PublisherDAO;
import app.database.transaction.TransactionManager;
import app.entity.Book;
import app.entity.Publisher;
import app.enums.*;
import app.exceptions.DatabaseException;
import app.exceptions.FileException;
import app.tuples.BookCreationEditionResult;
import app.service.entity.BookService;
import app.service.file.FileService;
import app.service.validation.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public class BookServiceImpl implements BookService {
    private static final Logger logger = LogManager.getLogger(BookServiceImpl.class);

    private final BookDAO bookDAO;
    private final PublisherDAO publisherDAO;
    private final FileService fileService;
    private final Validator validator;
    private final TransactionManager transactionManager;

    public BookServiceImpl(BookDAO bookDAO, PublisherDAO publisherDAO, FileService fileService, Validator validator, TransactionManager transactionManager) {
        this.bookDAO = bookDAO;
        this.publisherDAO = publisherDAO;
        this.fileService = fileService;
        this.validator = validator;
        this.transactionManager = transactionManager;
    }

    @Override
    public Optional<Book> findBookById(int bookId) throws DatabaseException {
        return bookDAO.findBookById(bookId);
    }

    @Override
    public List<Book> findBooks1(Integer userId, Integer isbn, String publisherQuery, String titleQuery, String authorQuery, BookSortCriteria sortCriteria, BookFormat format, BookGenre genre, BookLanguage language, int page, int size) throws DatabaseException {
        return bookDAO.findBooks(userId, isbn, publisherQuery, titleQuery, authorQuery, genre, language, format, sortCriteria, page, size);
    }

    @Override
    public List<Book> findBooks2(Integer isbn, String publisherQuery, String titleQuery, String authorQuery, BookSortCriteria sortCriteria, BookFormat format, BookGenre genre, BookLanguage language, int page, int size) throws DatabaseException {
        return findBooks1(null, isbn, publisherQuery, titleQuery, authorQuery, sortCriteria, format, genre, language, page, size);
    }

    @Override
    public List<Book> findBooks3(Integer userId, String query, int page, int size) throws DatabaseException {
        return findBooks1(userId, null, null, query, null, null, null, null, null, page, size);
    }

    @Override
    public List<Book> findBooks4(String query, int page, int size) throws DatabaseException {
        return findBooks1(null, null, null, query, null, null, null, null, null, page, size);
    }

    @Override
    public List<Book> findBooks5(Integer userId, int page, int size) throws DatabaseException {
        return findBooks1(userId, null, null, null, null, null, null, null, null, page, size);
    }

    @Override
    public int countBooks(Integer userId, Integer isbn, String publisherQuery, String titleQuery, String authorQuery, BookFormat format, BookGenre genre, BookLanguage language) throws DatabaseException {
        return bookDAO.countBooks(userId, isbn, publisherQuery, titleQuery, authorQuery, genre, language, format);
    }

    @Override
    public int countBooks(Integer isbn, String publisherQuery, String titleQuery, String authorQuery, BookSortCriteria sortCriteria, BookFormat format, BookGenre genre, BookLanguage language) throws DatabaseException {
        return countBooks(null, isbn, publisherQuery, titleQuery, authorQuery, format, genre, language);
    }

    @Override
    public int countBooks(Integer userId, String query) throws DatabaseException {
        return countBooks(userId, query, null, null, BookSortCriteria.ALPHABET, null, null, null);
    }

    @Override
    public int countBooks(String query) throws DatabaseException {
        return countBooks(null, query);
    }

    @Override
    public int countBooks(Integer userId) throws DatabaseException {
        return countBooks(userId, null);
    }


    @Override
    public BookCreationEditionResult createBook(String isbn, String title, String author, String description, String publicationDate, String totalCopiesNumber, String genre, String language, Boolean isValuable, String publisherTitle, byte[] cover, byte[] content) throws FileException, DatabaseException {
        logger.debug("Creation book with isbn = "+isbn+", title = "+title+", author = "+author+"... started");

        BookCreationEditionResult result = new BookCreationEditionResult();
        validator.validateBookNewIsbn(isbn, result);
        validator.validateBookTitle(title, result);
        validator.validateBookAuthor(author, result);
        validator.validateBookDescription(description, result);
        validator.validateBookPublicationDate(publicationDate, result);
        validator.validateBookTotalCopiesNumber(totalCopiesNumber, result);
        validator.validateBookGenre(genre, result);
        validator.validateBookLanguage(language, result);
        validator.validatePublisherTitle(publisherTitle, result);
        result.setSuccess(result.getIsbnValid() && result.getTitleValid() && result.getAuthorValid() && result.getDescriptionValid() && result.getPublicationDateValid() && result.getTotalCopiesNumberValid() && result.getGenreValid() && result.getLanguageValid() && result.getPublisherTitleValid());

        if (!result.getSuccess()) {
            logger.info("Book creation failed because of invalid creation parameters");
            return result;
        }

        Publisher publisher = new Publisher();
        Book book = new Book();


        transactionManager.execute(connection -> {
            publisher.setTitle(title);
            publisherDAO.savePublisher(publisher, connection);

            book.setPublisher(publisher);
            book.setIsbn(Integer.parseInt(isbn));
            book.setTitle(title);
            book.setAuthor(author);
            book.setDescription(description);
            book.setGenre(BookGenre.ofInt(Integer.parseInt(genre)));
            book.setLanguage(BookLanguage.ofInt(Integer.parseInt(language)));
            book.setPublicationDate(LocalDate.parse(publicationDate));
            book.setTotalCopiesNumber(Integer.parseInt(totalCopiesNumber));
            book.setFreeCopiesNumber(Integer.parseInt(totalCopiesNumber));
            book.setHasElectronicFormat(content != null);
            book.setValuable(isValuable);
            bookDAO.createBook(book, connection);

            return TransactionResult.COMMIT;
        });

        result.setBook(book);

        if (cover != null) fileService.saveFileAsBookCoverImage(book.getId(), cover);
        if (content != null) fileService.saveFileAsBookContent(book.getId(), content);

        logger.debug("Book creation with id = "+book.getId()+" successfully finished");
        return result;
    }

    @Override
    public BookCreationEditionResult editBook(Integer id, String isbn, String title, String author, String description, String publicationDate, String totalCopiesNumber, String genre, String language, String publisherTitle, byte[] cover, byte[] content) throws FileException, DatabaseException {
        logger.debug("Edit book with id = "+id+", isbn = "+isbn+", title = "+title+", author = "+author+" ...");

        Book book = bookDAO.findBookById(id).orElseThrow();

        BookCreationEditionResult result = new BookCreationEditionResult();
        validator.validateBookNewIsbn(isbn, book.getIsbn(), result);
        validator.validateBookTitle(title, result);
        validator.validateBookAuthor(author, result);
        validator.validateBookDescription(description, result);
        validator.validateBookPublicationDate(publicationDate, result);
        validator.validateBookTotalCopiesNumber(totalCopiesNumber, book.getTotalCopiesNumber()-book.getFreeCopiesNumber(), result);
        validator.validateBookGenre(genre, result);
        validator.validateBookLanguage(language, result);
        validator.validatePublisherTitle(publisherTitle, result);
        result.setSuccess(result.getIsbnValid() && result.getTitleValid() && result.getAuthorValid() && result.getDescriptionValid() && result.getPublicationDateValid() && result.getTotalCopiesNumberValid() && result.getGenreValid() && result.getLanguageValid() && result.getPublisherTitleValid());

        if (!result.getSuccess()) {
            logger.info("Book edition failed because of invalid edition parameters");
            return result;
        }

        Publisher publisher = new Publisher();

        transactionManager.execute(connection -> {
            publisher.setTitle(title);
            publisherDAO.savePublisher(publisher, connection);

            book.setPublisher(publisher);
            book.setIsbn(Integer.parseInt(isbn));
            book.setTitle(title);
            book.setAuthor(author);
            book.setDescription(description);
            book.setGenre(BookGenre.ofInt(Integer.parseInt(genre)));
            book.setLanguage(BookLanguage.ofInt(Integer.parseInt(language)));
            book.setTotalCopiesNumber(Integer.parseInt(totalCopiesNumber));
            book.setFreeCopiesNumber(Integer.parseInt(totalCopiesNumber));
            book.setPublicationDate(LocalDate.parse(publicationDate));
            bookDAO.updateBook(book, connection);

            return TransactionResult.COMMIT;
        });

        result.setBook(book);

        if (cover != null) fileService.saveFileAsBookCoverImage(book.getId(), cover);
        if (content != null) fileService.saveFileAsBookContent(book.getId(), content);

        logger.debug("Book edition with id = "+book.getId()+" successfully finished");
        return result;
    }

    @Override
    public void deleteBook(Integer bookId) throws DatabaseException {
        logger.debug("Deletion book with id = "+bookId+" started");

        Book book = bookDAO.findBookById(bookId).orElseThrow();
        book.setDeleted(true);

        transactionManager.execute(connection -> {
            bookDAO.updateBook(book, connection);
            return TransactionResult.COMMIT;
        });

        logger.debug("Deletion book with id = "+bookId+" successfully finished");
    }
}
