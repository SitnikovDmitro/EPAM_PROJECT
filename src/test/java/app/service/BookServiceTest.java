package app.service;

import app.database.dao.BookDAO;
import app.database.dao.PublisherDAO;
import app.database.dao.UserDAO;
import app.database.pool.ConnectionPool;
import app.database.transaction.TransactionManager;
import app.database.transaction.impl.TransactionManagerImpl;
import app.entity.Book;
import app.entity.Publisher;
import app.enums.BookGenre;
import app.enums.BookLanguage;
import app.enums.BookSortCriteria;
import app.service.other.CaptchaVerificationService;
import app.tuples.BookCreationEditionResult;
import app.service.entity.BookService;
import app.service.entity.impl.BookServiceImpl;
import app.service.file.FileService;
import app.service.validation.Validator;
import app.service.validation.impl.ValidatorImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class BookServiceTest {
    BookDAO bookDAO;
    UserDAO userDAO;
    PublisherDAO publisherDAO;
    FileService fileService;

    BookService bookService;

    @BeforeEach
    public void initialize() throws SQLException {
        Connection connection = Mockito.mock(Connection.class);
        ConnectionPool connectionPool = Mockito.mock(ConnectionPool.class);

        Mockito.doReturn(connection).when(connectionPool).getConnection();

        TransactionManager transactionManager = new TransactionManagerImpl(connectionPool);

        bookDAO = Mockito.mock(BookDAO.class);
        userDAO = Mockito.mock(UserDAO.class);
        publisherDAO = Mockito.mock(PublisherDAO.class);

        fileService = Mockito.mock(FileService.class);

        CaptchaVerificationService captchaVerificationService = Mockito.mock(CaptchaVerificationService.class);

        Validator validator = new ValidatorImpl(bookDAO, userDAO, captchaVerificationService);
        bookService = new BookServiceImpl(bookDAO, publisherDAO, fileService, validator, transactionManager);
    }

    private Book getTestBook() {
        Book book = new Book();
        book.setId(1);
        book.setIsbn(3333);
        book.setTitle("The Hitchhiker’s Guide to the Galaxy");
        book.setAuthor("Douglas Adams");
        book.setGenre(BookGenre.ADVENTURES);
        book.setLanguage(BookLanguage.ENGLISH);
        book.setPublicationDate(LocalDate.of(2020, 11, 23));
        book.setTotalCopiesNumber(12);
        book.setFreeCopiesNumber(12);
        book.setGradesSum(24);
        book.setGradesNumber(6);
        book.setHasElectronicFormat(true);
        book.setValuable(false);
        book.setDeleted(false);
        book.setDescription("Go on a galactic adventure with the last human on Earth, his alien best friend, and a depressed android. Introducing younger readers to The Hitchhiker’s Guide to the Galaxy, this YA edition of the funny sci-fi classic includes an introduction from Artemis Fowl author Eoin Colfer.");
        return book;
    }


    @Test
    public void findBooksTest() throws Exception {
        List list = Mockito.mock(List.class);

        Mockito.doReturn(4).when(list).size();

        Mockito.doReturn(list).when(bookDAO).findBooks(null, null, null, null, null, null, null, null, null, 3, 8);

        Assertions.assertEquals(4, bookService.findBooks5((Integer)null, 3, 8).size());
        Assertions.assertEquals(4, bookService.findBooks4((String)null, 3, 8).size());
        Assertions.assertEquals(4, bookService.findBooks3(null, null, 3, 8).size());
        Assertions.assertEquals(4, bookService.findBooks1(null, null, null, null, null, null, null, null, null, 3, 8).size());
        Assertions.assertEquals(4, bookService.findBooks2(null, null, null, null, null, null, null, null, 3, 8).size());
    }

    @Test
    public void countBooksTest() throws Exception {
        Mockito.doReturn(20).when(bookDAO).countBooks(null, null,null, null, null, null, null, null);

        Assertions.assertEquals(20, bookService.countBooks((Integer)null));
        Assertions.assertEquals(20, bookService.countBooks((String)null));
        Assertions.assertEquals(20, bookService.countBooks(null, null));
        Assertions.assertEquals(20, bookService.countBooks(null, null, null, null, (String)null, null, null, null));
        Assertions.assertEquals(20, bookService.countBooks(null, null, null, null, (BookSortCriteria) null, null, null, null));
    }

    @Test
    public void createBookTestCase1() throws Exception {
        Mockito.doReturn(false).when(bookDAO).existsBookByIsbn(1288);

        BookCreationEditionResult result = bookService.createBook("1288", "Title", "Author", "Description", "2020-07-07",  "123", "1", "1", false, "NewPublisher", null, null);

        Assertions.assertTrue(result.getSuccess());
        Assertions.assertEquals(result.getBook().getFreeCopiesNumber(), 123);
        Assertions.assertEquals(result.getBook().getIsbn(), 1288);
        Assertions.assertEquals(result.getBook().getTitle(), "Title");
        Assertions.assertEquals(result.getBook().getPublicationDate(), LocalDate.of(2020, 7, 7));

        Mockito.verify(bookDAO, Mockito.times(1)).createBook(Mockito.any(Book.class), Mockito.any(Connection.class));
        Mockito.verify(publisherDAO, Mockito.times(1)).savePublisher(Mockito.any(Publisher.class), Mockito.any(Connection.class));
    }

    @Test
    public void createBookTestCase2() throws Exception {
        Mockito.doReturn(true).when(bookDAO).existsBookByIsbn(3333);

        BookCreationEditionResult result = bookService.createBook("3333", "Title+", "Author", "Description", null,  "123", "1", "1", false, "NewPublisher", null, null);

        Assertions.assertFalse(result.getSuccess());
        Assertions.assertEquals(result.getIsbnValidationFeedbackKey(), "isbnIsDuplicated");
        Assertions.assertEquals(result.getTitleValidationFeedbackKey(), "titleContainsInvalidCharacters");
        Assertions.assertEquals(result.getPublicationDateValidationFeedbackKey(), "publicationDateIsNotSelected");

        Mockito.verify(bookDAO, Mockito.never()).createBook(Mockito.any(Book.class), Mockito.any(Connection.class));
    }

    @Test
    public void editBookTestCase1() throws Exception {
        Mockito.doReturn(true).when(bookDAO).existsBookByIsbn(3333);
        Mockito.doReturn(Optional.of(getTestBook())).when(bookDAO).findBookById(1);

        BookCreationEditionResult result = bookService.editBook(1, "3333", "Title", "Author", "Description", "2020-07-07",  "123", "1", "1", "NewPublisher", null, null);

        Assertions.assertTrue(result.getSuccess());
        Assertions.assertEquals(result.getBook().getFreeCopiesNumber(), 123);
        Assertions.assertEquals(result.getBook().getIsbn(), 3333);
        Assertions.assertEquals(result.getBook().getTitle(), "Title");
        Assertions.assertEquals(result.getBook().getPublicationDate(), LocalDate.of(2020, 7, 7));
    }

    @Test
    public void editBookTestCase2() throws Exception {
        Mockito.doReturn(true).when(bookDAO).existsBookByIsbn(3333);
        Mockito.doReturn(Optional.of(getTestBook())).when(bookDAO).findBookById(1);

        BookCreationEditionResult result = bookService.editBook(1,"3333", "Title+", "Author", "Description", null,  "-123", "1", "1", "NewPublisher", null, null);

        Assertions.assertFalse(result.getSuccess());
        Assertions.assertEquals(result.getTitleValidationFeedbackKey(), "titleContainsInvalidCharacters");
        Assertions.assertEquals(result.getPublicationDateValidationFeedbackKey(), "publicationDateIsNotSelected");
        Assertions.assertEquals(result.getTotalCopiesNumberValidationFeedbackKey(), "copiesNumberIsNegative");
    }

    @Test
    public void deleteBookTest() throws Exception {
        Mockito.doReturn(Optional.of(getTestBook())).when(bookDAO).findBookById(1);

        bookService.deleteBook(1);

        Mockito.verify(bookDAO, Mockito.times(1)).updateBook(Mockito.any(Book.class), Mockito.any(Connection.class));
    }

}
