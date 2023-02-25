package app.database.dao.impl;

import app.database.dao.BookContentDAO;
import app.database.pool.ConnectionPool;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookContentDAOImpl implements BookContentDAO {
    private static final String FIND_BOOK_CONTENT_BY_BOOK_ID = "SELECT * FROM booksContent WHERE bookId = ?;";
    private static final String SAVE_BOOK_CONTENT = "INSERT INTO booksContent (bookId, data) VALUES (?, ?) ON DUPLICATE KEY UPDATE data = ?;";

    private final ConnectionPool connectionPool;

    public BookContentDAOImpl(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public synchronized byte[] findBookContentByBookId(int bookId) throws DatabaseException {
        try (Connection con = connectionPool.getConnection();
             PreparedStatement stmt = con.prepareStatement(FIND_BOOK_CONTENT_BY_BOOK_ID)) {

            stmt.setInt(1, bookId);

            ResultSet set = stmt.executeQuery();
            byte[] data = null;

            if (set.next()) {
                data = set.getBytes("data");
            }

            set.close();
            return data;
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }

    @Override
    public synchronized void saveBookContent(int bookId, byte[] data) throws DatabaseException {
        try (Connection con = connectionPool.getConnection();
             PreparedStatement stmt = con.prepareStatement(SAVE_BOOK_CONTENT)) {

            stmt.setInt(1, bookId);
            stmt.setBytes(2, data);
            stmt.setBytes(3, data);

            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }
}
