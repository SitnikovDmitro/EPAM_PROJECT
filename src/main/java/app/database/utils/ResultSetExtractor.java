package app.database.utils;

import app.entity.*;
import app.enums.BookGenre;
import app.enums.BookLanguage;
import app.enums.OrderState;
import app.enums.UserRole;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Utilities for extracting information from result set
 */
public class ResultSetExtractor {
    private static User extractUserIteration(ResultSet set) throws SQLException {
        User user = new User();

        user.setId(set.getInt("users.id"));
        user.setFine(set.getInt("users.fine"));
        user.setBlocked(set.getBoolean("users.isBlocked"));
        user.setEmail(set.getString("users.email"));
        user.setFirstname(set.getString("users.firstname"));
        user.setLastname(set.getString("users.lastname"));
        user.setPasswordHash(set.getString("users.passwordHash"));
        user.setRole(UserRole.ofInt(set.getInt("users.role")));
        user.setLastFineRecalculationDate(set.getDate("users.lastFineRecalculationDate").toLocalDate());

        return user;
    }

    private static Publisher extractPublisherIteration(ResultSet set) throws SQLException {
        Publisher publisher = new Publisher();

        publisher.setId(set.getInt("publishers.id"));
        publisher.setTitle(set.getString("publishers.title"));

        return publisher;
    }

    private static Book extractBookIteration(ResultSet set) throws SQLException {
        Book book = new Book();

        book.setPublisher(extractPublisherIteration(set));
        book.setId(set.getInt("books.id"));
        book.setIsbn(set.getInt("books.isbn"));
        book.setPublicationDate(set.getDate("books.publicationDate").toLocalDate());
        book.setTotalCopiesNumber(set.getInt("books.totalCopiesNumber"));
        book.setFreeCopiesNumber(set.getInt("books.freeCopiesNumber"));
        book.setGradesSum(set.getInt("books.gradesSum"));
        book.setGradesNumber(set.getInt("books.gradesNumber"));
        book.setHasElectronicFormat(set.getBoolean("books.hasElectronicFormat"));
        book.setValuable(set.getBoolean("books.isValuable"));
        book.setDeleted(set.getBoolean("books.isDeleted"));
        book.setTitle(set.getString("books.title"));
        book.setAuthor(set.getString("books.author"));
        book.setDescription(set.getString("books.description"));
        book.setGenre(BookGenre.ofInt(set.getInt("books.genre")));
        book.setLanguage(BookLanguage.ofInt(set.getInt("books.language")));

        return book;
    }

    private static Feedback extractFeedbackIteration(ResultSet set) throws SQLException {
        Feedback feedback = new Feedback();

        feedback.setUser(extractUserIteration(set));
        feedback.setBook(extractBookIteration(set));
        feedback.setGrade(set.getInt("feedbacks.grade"));
        feedback.setText(set.getString("feedbacks.text"));
        feedback.setCreationDate(set.getDate("feedbacks.creationDate").toLocalDate());

        return feedback;
    }

    private static Order extractOrderIteration(ResultSet set) throws SQLException {
        Order order = new Order();

        order.setUser(extractUserIteration(set));
        order.setBook(extractBookIteration(set));
        order.setCode(set.getInt("orders.code"));
        order.setState(OrderState.ofInt(set.getInt("orders.state")));
        Date creationDate = set.getDate("orders.creationDate");
        order.setCreationDate(Objects.isNull(creationDate) ? null : creationDate.toLocalDate());
        Date deadlineDate = set.getDate("orders.deadlineDate");
        order.setDeadlineDate(Objects.isNull(deadlineDate) ? null : deadlineDate.toLocalDate());

        return order;
    }


    /**
     * Extracts users from result set
     * @param set result set
     * @return list of users
     **/
    public static List<User> extractUsers(ResultSet set) throws SQLException {
        List<User> users = new ArrayList<>();
        while (set.next()) {
            users.add(extractUserIteration(set));
        }
        return users;
    }

    /**
     * Extracts user from result set
     * @param set result set
     * @return user entity in optional wrapper
     **/
    public static Optional<User> extractUser(ResultSet set) throws SQLException {
        if (set.next()) {
            User user = extractUserIteration(set);
            if (set.next()) throw new AssertionError("Duplication of unique column");
            return Optional.of(user);
        }
        return Optional.empty();
    }

    /**
     * Extracts publishers from result set
     * @param set result set
     * @return list of publishers
     **/
    public static List<Publisher> extractPublishers(ResultSet set) throws SQLException {
        List<Publisher> publishers = new ArrayList<>();
        while (set.next()) {
            publishers.add(extractPublisherIteration(set));
        }
        return publishers;
    }

    /**
     * Extracts publisher from result set
     * @param set result set
     * @return publisher entity in optional wrapper
     **/
    public static Optional<Publisher> extractPublisher(ResultSet set) throws SQLException {
        if (set.next()) {
            Publisher publisher = extractPublisherIteration(set);
            if (set.next()) throw new AssertionError("Duplication of unique column");
            return Optional.of(publisher);
        }
        return Optional.empty();
    }

    /**
     * Extracts books from result set
     * @param set result set
     * @return list of books
     **/
    public static List<Book> extractBooks(ResultSet set) throws SQLException {
        List<Book> books = new ArrayList<>();
        while (set.next()) {
            books.add(extractBookIteration(set));
        }
        return books;
    }

    /**
     * Extracts book from result set
     * @param set result set
     * @return book entity in optional wrapper
     **/
    public static Optional<Book> extractBook(ResultSet set) throws SQLException {
        if (set.next()) {
            Book book = extractBookIteration(set);
            if (set.next()) throw new AssertionError("Duplication of unique column");
            return Optional.of(book);
        }
        return Optional.empty();
    }

    /**
     * Extracts feedbacks from result set
     * @param set result set
     * @return list of feedbacks
     **/
    public static List<Feedback> extractFeedbacks(ResultSet set) throws SQLException {
        List<Feedback> feedbacks = new ArrayList<>();
        while (set.next()) {
            feedbacks.add(extractFeedbackIteration(set));
        }
        return feedbacks;
    }

    /**
     * Extracts feedback from result set
     * @param set result set
     * @return feedback entity in optional wrapper
     **/
    public static Optional<Feedback> extractFeedback(ResultSet set) throws SQLException {
        if (set.next()) {
            Feedback feedback = extractFeedbackIteration(set);
            if (set.next()) throw new AssertionError("Duplication of unique column");
            return Optional.of(feedback);
        }
        return Optional.empty();
    }

    /**
     * Extracts orders from result set
     * @param set result set
     * @return list of orders
     **/
    public static List<Order> extractOrders(ResultSet set) throws SQLException {
        List<Order> orders = new ArrayList<>();
        while (set.next()) {
            orders.add(extractOrderIteration(set));
        }
        return orders;
    }

    /**
     * Extracts order from result set
     * @param set result set
     * @return order entity in optional wrapper
     **/
    public static Optional<Order> extractOrder(ResultSet set) throws SQLException {
        if (set.next()) {
            Order order = extractOrderIteration(set);
            if (set.next()) throw new AssertionError("Duplication of unique column");
            return Optional.of(order);
        }
        return Optional.empty();
    }
}
