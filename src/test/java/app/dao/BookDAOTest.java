package app.dao;

import app.database.dao.BookDAO;
import app.database.dao.impl.BookDAOImpl;
import app.database.pool.ConnectionPool;
import app.database.pool.impl.H2ConnectionPoolImpl;
import app.database.transaction.TransactionManager;
import app.database.transaction.impl.TransactionManagerImpl;
import app.entity.Book;
import app.entity.Publisher;
import app.enums.*;
import app.database.initialization.DatabaseInitializer;
import app.exceptions.DatabaseException;
import app.utils.DeepEqualUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

public class BookDAOTest {
    private final BookDAO bookDAO;
    private final TransactionManager transactionManager;

    public BookDAOTest() {
        ConnectionPool connectionPool = H2ConnectionPoolImpl.getInstance();

        bookDAO = new BookDAOImpl(connectionPool);
        transactionManager = new TransactionManagerImpl(connectionPool);
    }

    @BeforeEach
    public void initialize() throws DatabaseException {
        DatabaseInitializer.getInstance().initialize();
    }

    @Test
    public void findBooksTestCase1() throws Exception {
        List<Book> list1 = bookDAO.findAll();
        List<Book> list2 = bookDAO.findBooks(null,null, null, null, null, null, null, null, null, 1, 1000);
        list1.removeIf(Book::isDeleted);

        Assertions.assertEquals(list1.size(), list2.size());

        Assertions.assertTrue(list1.stream().allMatch(
                bookA -> list2.stream().anyMatch(
                        bookB -> DeepEqualUtil.getInstance().equals(bookA, bookB)
                )
        ));
    }

    @Test
    public void findBooksTestCase2() throws Exception {
        List<Book> list = bookDAO.findBooks(null, null,null, "HaRrY", null, null, null, null, null, 1, 1000);

        Assertions.assertTrue(list.stream().allMatch(book -> book.getTitle().toLowerCase().contains("HaRrY".toLowerCase())));
        Assertions.assertEquals(list.size(), 7);
    }

    @Test
    public void findBooksTestCase3() throws Exception {
        List<Book> list = bookDAO.findBooks(null, null, null, null, "OwLiN", null, null, null, null, 1, 1000);

        Assertions.assertTrue(list.stream().allMatch(book -> book.getAuthor().toLowerCase().contains("OwLiN".toLowerCase())));
        Assertions.assertEquals(list.size(), 7);
    }

    @Test
    public void findBooksTestCase4() throws Exception {
        List<Book> list = bookDAO.findBooks(null, null, null, null, null, BookGenre.ADVENTURES, null, null, null, 1, 1000);

        Assertions.assertTrue(list.stream().allMatch(book -> book.getGenre() == BookGenre.ADVENTURES));
        Assertions.assertEquals(list.size(), 9);
    }

    @Test
    public void findBooksTestCase5() throws Exception {
        List<Book> list = bookDAO.findBooks(null, null, null, null, null, null, BookLanguage.JAPANESE, null, null, 1, 1000);

        Assertions.assertTrue(list.stream().allMatch(book -> book.getLanguage() == BookLanguage.JAPANESE));
        Assertions.assertEquals(list.size(), 4);
    }

    @Test
    public void findBooksTestCase6() throws Exception {
        List<Book> list = bookDAO.findBooks(null, null, null, null, null, null, null, BookFormat.ELECTRONIC, null, 1, 1000);

        Assertions.assertTrue(list.stream().allMatch(Book::isHasElectronicFormat));
        Assertions.assertEquals(list.size(), 5);
    }

    @Test
    public void findBooksTestCase7() throws Exception {
        List<Book> list = bookDAO.findBooks(null, null, null, null, null, null, null, null, BookSortCriteria.ALPHABET, 1, 1000);
        Assertions.assertEquals(list.size(), 20);

        for (int i = 0; i < list.size()-1; i++) {
            Book bookA = list.get(i);
            Book bookB = list.get(i+1);
            Assertions.assertTrue(bookA.getTitle().compareTo(bookB.getTitle()) < 0);
        }
    }

    @Test
    public void findBooksTestCase8() throws Exception {
        List<Book> list = bookDAO.findBooks(null, null, null,null, null, null, null, null, BookSortCriteria.PUBLICATION, 1, 1000);
        Assertions.assertEquals(list.size(), 20);

        for (int i = 0; i < list.size()-1; i++) {
            Book bookA = list.get(i);
            Book bookB = list.get(i+1);
            Assertions.assertTrue(bookA.getPublicationDate().compareTo(bookB.getPublicationDate()) > 0);
        }
    }

    @Test
    public void findBooksTestCase9() throws Exception {
        List<Book> list = bookDAO.findBooks(null, null, "Bloomsbury", null, null, null, null, null, null, 1, 1000);

        Assertions.assertTrue(list.stream().allMatch(book -> book.getPublisher().getId() == 4));
        Assertions.assertEquals(list.size(), 7);
    }

    @Test
    public void findBooksTestCase10() throws Exception {
        List<Book> list = bookDAO.findBooks(null, null, "Pearson", null, null, null, null, null, null, 1, 1000);

        Assertions.assertTrue(list.stream().allMatch(book -> book.getPublisher().getId() == 1));
        Assertions.assertEquals(list.size(), 4);
    }

    @Test
    public void countBooksTest() throws Exception {
        Assertions.assertEquals(bookDAO.countBooks(null, null, null, null, null, null, null, null), 20);
        Assertions.assertEquals(bookDAO.countBooks(null, null, null, "HarrY", null, null, null, null), 7);
        Assertions.assertEquals(bookDAO.countBooks(null, 3333,null, null, null, null, null, null), 1);
    }

    @Test
    public void createBookTest() throws Exception {
        Book bookA = new Book(1, new Publisher(1, "Pearson"), 3309, 12, 12, 24, 6, true, false, false, "The Hitchhiker’s Guide to the Galaxy", "Douglas Adams", "Go on a galactic adventure with the last human on Earth, his alien best friend, and a depressed android. Introducing younger readers to The Hitchhiker’s Guide to the Galaxy, this YA edition of the funny sci-fi classic includes an introduction from Artemis Fowl author Eoin Colfer.", BookGenre.ADVENTURES, BookLanguage.ENGLISH, LocalDate.of(2020, 11, 23));

        transactionManager.execute(connection -> {
            bookDAO.createBook(bookA, connection);
            return TransactionResult.COMMIT;
        });

        Book bookB = bookDAO.findBookById(bookA.getId()).orElseThrow();


        Assertions.assertTrue(bookA.getId() != 0);
        Assertions.assertTrue(DeepEqualUtil.getInstance().equals(bookA, bookB));
    }

    @Test
    public void updateBookTest() throws Exception {
        Book bookA = bookDAO.findBookById(1).orElseThrow();
        bookA.setLanguage(BookLanguage.UKRAINIAN);
        bookA.setPublicationDate(LocalDate.of(2000, 10, 10));
        bookA.setAuthor("Test");

        transactionManager.execute(connection -> {
            bookDAO.updateBook(bookA, connection);
            return TransactionResult.COMMIT;
        });

        Book bookB = bookDAO.findBookById(1).orElseThrow();

        Assertions.assertTrue(DeepEqualUtil.getInstance().equals(bookA, bookB));
    }

    @Test
    public void findBookByIdTest() throws Exception {
        Book bookA = new Book(1, new Publisher(6, "HarperCollins"), 3333, 12, 12, 24, 6, true, false, false, "The Hitchhiker’s Guide to the Galaxy", "Douglas Adams", "Go on a galactic adventure with the last human on Earth, his alien best friend, and a depressed android. Introducing younger readers to The Hitchhiker’s Guide to the Galaxy, this YA edition of the funny sci-fi classic includes an introduction from Artemis Fowl author Eoin Colfer.", BookGenre.ADVENTURES, BookLanguage.ENGLISH, LocalDate.of(2020, 11, 23));
        Book bookB = bookDAO.findBookById(1).orElseThrow();
        Assertions.assertTrue(DeepEqualUtil.getInstance().equals(bookA, bookB));
    }
}
