package app.database.dao;

import app.database.transaction.TransactionManager;
import app.entity.Feedback;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

/**
 * Provides database operations with table of feedbacks
 **/
public interface FeedbackDAO {
    /**
     * Finds feedback by book id
     * @param bookId id of book
     * @param recordsPerPage max number of feedbacks that result list could contain
     * @return list of found feedbacks
     **/
    List<Feedback> findFeedbacksByBookId(int bookId, int recordsPerPage) throws DatabaseException;

    /**
     * Finds feedback by book id and user id
     * @param bookId id of book
     * @param userId id of user
     * @return found feedback entity in optional wrapper
     **/
    Optional<Feedback> findFeedbackByUserIdAndBookId(int bookId, int userId) throws DatabaseException;

    /**
     * Updates existing feedback if it exists or creates new otherwise
     * Used with {@link TransactionManager} in
     * {@link app.database.transaction.TransactionExecutor#execute(Connection)}
     * @param feedback feedback to create or update
     * @param connection connection with database
     **/
    void saveFeedback(Feedback feedback, Connection connection) throws DatabaseException;
}
