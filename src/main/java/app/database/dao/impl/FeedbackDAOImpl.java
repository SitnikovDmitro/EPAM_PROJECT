package app.database.dao.impl;

import app.database.dao.FeedbackDAO;
import app.database.pool.ConnectionPool;
import app.database.utils.ResultSetExtractor;
import app.entity.Feedback;
import app.exceptions.DatabaseException;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class FeedbackDAOImpl implements FeedbackDAO {
    private static final String FEEDBACK_JOIN_EXPR = "(feedbacks INNER JOIN users ON feedbacks.userId = users.id INNER JOIN books ON feedbacks.bookId = books.id INNER JOIN publishers ON books.publisherId = publishers.id)";
    private static final String FIND_FEEDBACK_BY_USER_ID_AND_BOOK_ID_SQL = "SELECT * FROM "+FEEDBACK_JOIN_EXPR+" WHERE bookId = ? AND userId = ?;";
    private static final String FIND_FEEDBACKS_BY_BOOK_ID_SQL = "SELECT * FROM "+FEEDBACK_JOIN_EXPR+" WHERE bookId = ? ORDER BY creationDate DESC LIMIT ?;";
    private static final String SAVE_FEEDBACK_SQL = "INSERT INTO feedbacks (userId, bookId, grade, text, creationDate) VALUES (?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE grade = ?, text = ?, creationDate = ?;";

    private final ConnectionPool connectionPool;

    public FeedbackDAOImpl(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public List<Feedback> findFeedbacksByBookId(int bookId, int recordsPerPage) throws DatabaseException {
        try (Connection con = connectionPool.getConnection();
             PreparedStatement stmt = con.prepareStatement(FIND_FEEDBACKS_BY_BOOK_ID_SQL)) {

            stmt.setInt(1, bookId);
            stmt.setInt(2, recordsPerPage);

            ResultSet set = stmt.executeQuery();
            List<Feedback> result = ResultSetExtractor.extractFeedbacks(set);
            set.close();
            return result;
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }

    @Override
    public Optional<Feedback> findFeedbackByUserIdAndBookId(int bookId, int userId) throws DatabaseException {
        try (Connection con = connectionPool.getConnection();
             PreparedStatement stmt = con.prepareStatement(FIND_FEEDBACK_BY_USER_ID_AND_BOOK_ID_SQL)) {

            stmt.setInt(1, bookId);
            stmt.setInt(2, userId);

            ResultSet set = stmt.executeQuery();
            Optional<Feedback> feedback = ResultSetExtractor.extractFeedback(set);
            set.close();
            return feedback;
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }

    @Override
    public void saveFeedback(Feedback feedback, Connection connection) throws DatabaseException {
        try (PreparedStatement stmt = connection.prepareStatement(SAVE_FEEDBACK_SQL)) {

            int pos = 1;
            stmt.setInt(pos++, feedback.getUser().getId());
            stmt.setInt(pos++, feedback.getBook().getId());
            stmt.setInt(pos++, feedback.getGrade());
            stmt.setString(pos++, feedback.getText());
            stmt.setDate(pos++, Date.valueOf(feedback.getCreationDate()));
            stmt.setInt(pos++, feedback.getGrade());
            stmt.setString(pos++, feedback.getText());
            stmt.setDate(pos, Date.valueOf(feedback.getCreationDate()));

            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }
}
