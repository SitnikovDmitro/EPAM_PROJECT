package app.service.entity.impl;

import app.constants.PaginationConstants;
import app.database.dao.BookDAO;
import app.database.dao.FeedbackDAO;
import app.database.dao.UserDAO;
import app.database.transaction.TransactionManager;
import app.entity.Book;
import app.entity.Feedback;
import app.entity.User;
import app.enums.TransactionResult;
import app.enums.UserRole;
import app.exceptions.DatabaseException;
import app.exceptions.InvalidStateException;
import app.service.entity.FeedbackService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class FeedbackServiceImpl implements FeedbackService {
    private static final Logger logger = LogManager.getLogger(FeedbackServiceImpl.class);

    private final UserDAO userDAO;
    private final BookDAO bookDAO;
    private final FeedbackDAO feedbackDAO;
    private final TransactionManager transactionManager;

    public FeedbackServiceImpl(UserDAO userDAO, BookDAO bookDAO, FeedbackDAO feedbackDAO, TransactionManager transactionManager) {
        this.userDAO = userDAO;
        this.bookDAO = bookDAO;
        this.feedbackDAO = feedbackDAO;
        this.transactionManager = transactionManager;
    }

    @Override
    public void createFeedback(int bookId, int userId, String text, int grade) throws DatabaseException {
        logger.debug("Creation feedback on book with id = "+bookId+" by user with id = "+userId+" started");

        Book book = bookDAO.findBookById(bookId).orElseThrow();
        User user = userDAO.findUserById(userId).orElseThrow();
        if (user.getRole() != UserRole.READER) throw new InvalidStateException("Only readers can leave feedbacks");
        if (text.length() > 500) throw new InvalidStateException("Too long text");
        if (grade < 1 || grade > 5) throw new InvalidStateException("Invalid grade: "+grade);

        Optional<Feedback> oldFeedback = feedbackDAO.findFeedbackByUserIdAndBookId(user.getId(), book.getId());
        Feedback newFeedback = new Feedback(user, book, grade, text, LocalDate.now());

        book.setGradesSum(book.getGradesSum()+grade);
        book.setGradesNumber(book.getGradesNumber()+1);

        if (oldFeedback.isPresent()) {
            book.setGradesSum(book.getGradesSum()-oldFeedback.get().getGrade());
            book.setGradesNumber(book.getGradesNumber()-1);
        }

        transactionManager.execute(connection -> {
            bookDAO.updateBook(book, connection);
            feedbackDAO.saveFeedback(newFeedback, connection);
            return TransactionResult.COMMIT;
        });

        logger.debug("Creation feedback on book with id = "+bookId+" successfully finished");
    }

    @Override
    public List<Feedback> findFeedbacksByBookId(int bookId) throws DatabaseException {
        return feedbackDAO.findFeedbacksByBookId(bookId, PaginationConstants.FEEDBACKS_PER_PAGE);
    }
}
