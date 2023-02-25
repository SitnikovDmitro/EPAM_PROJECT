package app.service.entity;

import app.entity.Feedback;
import app.exceptions.DatabaseException;

import java.sql.SQLException;
import java.util.List;

/**
 * Provides operations with feedback entity
 **/
public interface FeedbackService {
    /**
     * Creates feedback on book by reader
     * @param bookId id of book
     * @param userId id of reader
     * @param text feedback text
     * @param grade feedback grade (should be between 1 and 5)
     **/
    void createFeedback(int bookId, int userId, String text, int grade) throws DatabaseException;

    /**
     * Finds feedbacks on book
     * @param bookId id of book
     * @return list of found feedbacks
     **/
    List<Feedback> findFeedbacksByBookId(int bookId) throws DatabaseException;
}
