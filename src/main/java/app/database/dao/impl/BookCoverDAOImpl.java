package app.database.dao.impl;

import app.database.dao.BookCoverDAO;
import app.database.pool.ConnectionPool;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookCoverDAOImpl implements BookCoverDAO {
    private static final String FIND_BOOK_COVER_BY_BOOK_ID = "SELECT * FROM booksCover WHERE bookId = ?;";
    private static final String SAVE_BOOK_COVER = "INSERT INTO booksCover (bookId, data) VALUES (?, ?) ON DUPLICATE KEY UPDATE data = ?;";

    private final ConnectionPool connectionPool;

    public BookCoverDAOImpl(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public synchronized byte[] findBookCoverByBookId(int bookId) throws DatabaseException {
        try (Connection con = connectionPool.getConnection();
             PreparedStatement stmt = con.prepareStatement(FIND_BOOK_COVER_BY_BOOK_ID)) {

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
    public synchronized void saveBookCover(int bookId, byte[] data) throws DatabaseException {
        try (Connection con = connectionPool.getConnection();
             PreparedStatement stmt = con.prepareStatement(SAVE_BOOK_COVER)) {

            stmt.setInt(1, bookId);
            stmt.setBytes(2, data);
            stmt.setBytes(3, data);

            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }
}