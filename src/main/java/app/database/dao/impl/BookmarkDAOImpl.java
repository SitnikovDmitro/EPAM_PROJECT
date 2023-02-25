package app.database.dao.impl;

import app.database.dao.BookmarkDAO;
import app.database.pool.ConnectionPool;
import app.exceptions.DatabaseException;

import java.sql.*;

public class BookmarkDAOImpl implements BookmarkDAO {
    private static final String SAVE_BOOKMARKS_BY_USER_ID_AND_BOOK_ID_SQL = "INSERT INTO bookmarks (userId, bookId) VALUES (?, ?) ON DUPLICATE KEY UPDATE userId = ?, bookId = ?;";
    private static final String DELETE_BOOKMARKS_BY_USER_ID_AND_BOOK_ID_SQL = "DELETE FROM bookmarks WHERE userId = ? AND bookId = ?;";
    private static final String EXISTS_BOOKMARKS_BY_USER_ID_AND_BOOK_ID_SQL ="SELECT EXISTS (SELECT * FROM bookmarks WHERE userId = ? AND bookId = ?);";


    private final ConnectionPool connectionPool;

    public BookmarkDAOImpl(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public void saveBookmarkByUserIdAndBookId(int userId, int bookId, Connection connection) throws DatabaseException {
        try (PreparedStatement stmt = connection.prepareStatement(SAVE_BOOKMARKS_BY_USER_ID_AND_BOOK_ID_SQL)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, bookId);
            stmt.setInt(3, userId);
            stmt.setInt(4, bookId);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }

    @Override
    public void deleteBookmarkByUserIdAndBookId(int userId, int bookId, Connection connection) throws DatabaseException {
        try (PreparedStatement stmt = connection.prepareStatement(DELETE_BOOKMARKS_BY_USER_ID_AND_BOOK_ID_SQL)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, bookId);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }

    @Override
    public boolean existsBookmarkByUserIdAndBookId(int userId, int bookId) throws DatabaseException {
        try (Connection con = connectionPool.getConnection();
             PreparedStatement stmt = con.prepareStatement(EXISTS_BOOKMARKS_BY_USER_ID_AND_BOOK_ID_SQL)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, bookId);
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
