package app.database.dao.impl;

import app.database.dao.BookDAO;
import app.database.pool.ConnectionPool;
import app.database.utils.ResultSetExtractor;
import app.entity.Book;
import app.enums.BookFormat;
import app.enums.BookGenre;
import app.enums.BookLanguage;
import app.enums.BookSortCriteria;
import app.exceptions.DatabaseException;
import app.utils.QueryBuilder;

import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

public class BookDAOImpl implements BookDAO {
    private static final String BOOK_JOIN_EXPR = "(books INNER JOIN publishers ON books.publisherId = publishers.id)";
    private static final String FIND_BOOK_BY_ID_SQL = "SELECT * FROM "+BOOK_JOIN_EXPR+" WHERE books.id = ?;";
    private static final String FIND_ALL_SQL = "SELECT * FROM "+BOOK_JOIN_EXPR+";";
    private static final String CREATE_BOOK_SQL = "INSERT INTO books (id, publisherId, isbn, publicationDate, totalCopiesNumber, freeCopiesNumber, gradesSum, gradesNumber, hasElectronicFormat, isValuable, isDeleted, title, author, description, genre, language) VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String UPDATE_BOOK_SQL = "UPDATE books SET publisherId = ?, isbn = ?, publicationDate = ?, totalCopiesNumber = ?, freeCopiesNumber = ?, gradesSum = ?, gradesNumber = ?, hasElectronicFormat = ?, isValuable = ?, isDeleted = ?, title = ?, author = ?, description = ?, genre = ?, language = ? WHERE id = ?;";
    private static final String EXISTS_BOOK_BY_ISBN_SQL = "SELECT EXISTS (SELECT * FROM books WHERE isbn = ?);";

    private final ConnectionPool connectionPool;

    public BookDAOImpl(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public Optional<Book> findBookById(int bookId) throws DatabaseException {
        try (Connection con = connectionPool.getConnection();
             PreparedStatement stmt = con.prepareStatement(FIND_BOOK_BY_ID_SQL)) {
            stmt.setInt(1, bookId);

            ResultSet set = stmt.executeQuery();
            Optional<Book> book = ResultSetExtractor.extractBook(set);
            set.close();
            return book;
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }

    @Override
    public List<Book> findAll() throws DatabaseException {
        try (Connection con = connectionPool.getConnection();
             Statement stmt = con.createStatement()) {

            ResultSet set = stmt.executeQuery(FIND_ALL_SQL);
            List<Book> result = ResultSetExtractor.extractBooks(set);
            set.close();
            return result;
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }





    @Override
    public List<Book> findBooks(Integer userId, Integer isbn, String publisherQuery, String titleQuery, String authorQuery, BookGenre genre, BookLanguage language, BookFormat format, BookSortCriteria criteria, int page, int recordsPerPage) throws DatabaseException {
        String selection, orderCriteria, orderType;

        StringJoiner conditions = new StringJoiner(" AND ");
        if (userId != null) conditions.add("id IN (SELECT bookmarks.bookId FROM bookmarks WHERE bookmarks.userId = "+userId+")");
        if (publisherQuery != null) conditions.add("LOWER(publishers.title) LIKE ?");
        if (isbn != null) conditions.add("books.isbn = "+isbn);
        if (titleQuery != null && !titleQuery.isBlank()) conditions.add("LOWER(books.title) LIKE ?");
        if (authorQuery != null && !authorQuery.isBlank()) conditions.add("LOWER(books.author) LIKE ?");
        if (genre != null) conditions.add("books.genre = "+genre.toInt());
        if (language != null) conditions.add("books.language = "+language.toInt());
        if (format == BookFormat.ELECTRONIC) conditions.add("books.hasElectronicFormat = true");
        if (format == BookFormat.VALUABLE) conditions.add("books.isValuable = true");
        conditions.add("books.isDeleted = false");

        if (criteria == BookSortCriteria.ALPHABET) {
            selection = "*";
            orderCriteria = "books.title";
            orderType = "ASC";
        } else if (criteria == BookSortCriteria.PUBLICATION) {
            selection = "*";
            orderCriteria = "books.publicationDate";
            orderType = "DESC";
        } else {
            selection = "*, (CASE books.gradesNumber WHEN 0 THEN 0 ELSE 100 * books.gradesSum / books.gradesNumber END) AS averageGrade";
            orderCriteria = "averageGrade";
            orderType = "DESC";
        }

        QueryBuilder builder = new QueryBuilder();
        String sql = builder.select(selection).from(BOOK_JOIN_EXPR).
                             where(conditions.toString()).
                             order(orderCriteria, orderType).
                             limit((page-1)*recordsPerPage, recordsPerPage).build();

        try (Connection con = connectionPool.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            int pos = 1;
            if (publisherQuery != null && !publisherQuery.isBlank()) stmt.setString(pos++, "%"+publisherQuery.toLowerCase()+"%");
            if (titleQuery != null && !titleQuery.isBlank()) stmt.setString(pos++, "%"+titleQuery.toLowerCase()+"%");
            if (authorQuery != null && !authorQuery.isBlank()) stmt.setString(pos, "%"+authorQuery.toLowerCase()+"%");

            ResultSet set = stmt.executeQuery();
            List<Book> result = ResultSetExtractor.extractBooks(set);
            set.close();
            return result;
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }

    @Override
    public int countBooks(Integer userId, Integer isbn, String publisherQuery, String titleQuery, String authorQuery, BookGenre genre, BookLanguage language, BookFormat format) throws DatabaseException {
        StringJoiner conditions = new StringJoiner(" AND ");
        if (userId != null) conditions.add("id IN (SELECT bookmarks.bookId FROM bookmarks WHERE bookmarks.userId = "+userId+")");
        if (publisherQuery != null) conditions.add("LOWER(publishers.title) LIKE ?");
        if (isbn != null) conditions.add("books.isbn = "+isbn);
        if (titleQuery != null && !titleQuery.isBlank()) conditions.add("LOWER(books.title) LIKE ?");
        if (authorQuery != null && !authorQuery.isBlank()) conditions.add("LOWER(books.author) LIKE ?");
        if (genre != null) conditions.add("books.genre = "+genre.toInt());
        if (language != null) conditions.add("books.language = "+language.toInt());
        if (format == BookFormat.ELECTRONIC) conditions.add("books.hasElectronicFormat = true");
        if (format == BookFormat.VALUABLE) conditions.add("books.isValuable = true");
        conditions.add("books.isDeleted = false");

        QueryBuilder builder = new QueryBuilder();
        String sql = builder.select("COUNT(*)").from(BOOK_JOIN_EXPR).where(conditions.toString()).build();

        try (Connection con = connectionPool.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            int pos = 1;
            if (publisherQuery != null && !publisherQuery.isBlank()) stmt.setString(pos++, "%"+publisherQuery.toLowerCase()+"%");
            if (titleQuery != null && !titleQuery.isBlank()) stmt.setString(pos++, "%"+titleQuery.toLowerCase()+"%");
            if (authorQuery != null && !authorQuery.isBlank()) stmt.setString(pos, "%"+authorQuery.toLowerCase()+"%");

            ResultSet set = stmt.executeQuery();
            set.next();
            int count = set.getInt(1);
            set.close();
            return count;
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }


    @Override
    public void createBook(Book book, Connection connection) throws DatabaseException {
        try (PreparedStatement stmt = connection.prepareStatement(CREATE_BOOK_SQL, Statement.RETURN_GENERATED_KEYS)) {

            int pos = 1;
            stmt.setInt(pos++, book.getPublisher().getId());
            stmt.setInt(pos++, book.getIsbn());
            stmt.setDate(pos++, Date.valueOf(book.getPublicationDate()));
            stmt.setInt(pos++, book.getTotalCopiesNumber());
            stmt.setInt(pos++, book.getFreeCopiesNumber());
            stmt.setInt(pos++, book.getGradesSum());
            stmt.setInt(pos++, book.getGradesNumber());
            stmt.setBoolean(pos++, book.isHasElectronicFormat());
            stmt.setBoolean(pos++, book.isValuable());
            stmt.setBoolean(pos++, book.isDeleted());
            stmt.setString(pos++, book.getTitle());
            stmt.setString(pos++, book.getAuthor());
            stmt.setString(pos++, book.getDescription());
            stmt.setInt(pos++, book.getGenre().toInt());
            stmt.setInt(pos, book.getLanguage().toInt());

            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            book.setId(rs.getInt(1));
            rs.close();
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }

    @Override
    public void updateBook(Book book, Connection connection) throws DatabaseException {
        try (PreparedStatement stmt = connection.prepareStatement(UPDATE_BOOK_SQL)) {

            int pos = 1;
            stmt.setInt(pos++, book.getPublisher().getId());
            stmt.setInt(pos++, book.getIsbn());
            stmt.setDate(pos++, Date.valueOf(book.getPublicationDate()));
            stmt.setInt(pos++, book.getTotalCopiesNumber());
            stmt.setInt(pos++, book.getFreeCopiesNumber());
            stmt.setInt(pos++, book.getGradesSum());
            stmt.setInt(pos++, book.getGradesNumber());
            stmt.setBoolean(pos++, book.isHasElectronicFormat());
            stmt.setBoolean(pos++, book.isValuable());
            stmt.setBoolean(pos++, book.isDeleted());
            stmt.setString(pos++, book.getTitle());
            stmt.setString(pos++, book.getAuthor());
            stmt.setString(pos++, book.getDescription());
            stmt.setInt(pos++, book.getGenre().toInt());
            stmt.setInt(pos++, book.getLanguage().toInt());
            stmt.setInt(pos, book.getId());

            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }

    @Override
    public boolean existsBookByIsbn(int isbn) throws DatabaseException {
        try (Connection con = connectionPool.getConnection();
             PreparedStatement stmt = con.prepareStatement(EXISTS_BOOK_BY_ISBN_SQL)) {

            stmt.setInt(1, isbn);
            ResultSet set = stmt.executeQuery();
            set.next();
            boolean exists = set.getBoolean(1);
            set.close();
            return exists;
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }
}
