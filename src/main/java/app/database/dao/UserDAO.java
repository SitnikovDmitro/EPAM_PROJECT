package app.database.dao;

import app.database.transaction.TransactionManager;
import app.entity.User;
import app.enums.UserRole;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Provides database operations with table of users
 **/
public interface UserDAO {
    /**
     * Finds all users
     * @return list containing all users
     **/
    List<User> findAll() throws DatabaseException;

    /**
     * Finds users
     * @param query query by user firstname, lastname or email (optional)
     * @param role role of user (optional)
     * @param onlyBlocked if true result list contain only blocked users
     * @param onlyWithFine if true result list contain only users which fine is greater than zero
     * @param page number of page for which users are being searched
     * @param recordsPerPage max number of users that result list could contain
     * @return list containing found users ordered by alphabet
     **/
    List<User> findUsers(String query, UserRole role, boolean onlyBlocked, boolean onlyWithFine, int page, int recordsPerPage) throws DatabaseException;

    /**
     * Counts users
     * @param query query by user firstname, lastname or email (optional)
     * @param role role of user (optional)
     * @param onlyBlocked if true result list contain only blocked users
     * @param onlyWithFine if true result list contain only users which fine is greater than zero
     * @return number of found users
     **/
    int countUsers(String query, UserRole role, boolean onlyBlocked, boolean onlyWithFine) throws DatabaseException;

    /**
     * Finds user by id
     * @param userId id of user
     * @return found user entity in optional wrapper
     **/
    Optional<User> findUserById(int userId) throws DatabaseException;

    /**
     * Finds user by email
     * @param email email of user
     * @return found user entity in optional wrapper
     **/
    Optional<User> findUserByEmail(String email) throws DatabaseException;

    /**
     * Deletes order by user id if such exists.
     * Used with {@link TransactionManager} in
     * {@link app.database.transaction.TransactionExecutor#execute(Connection)}
     * @param userId id of order user
     * @param connection connection with database
     **/
    void deleteUserById(int userId, Connection connection) throws DatabaseException;

    /**
     * Creates new user. Used with {@link TransactionManager} in
     * {@link app.database.transaction.TransactionExecutor#execute(Connection)}
     * @param user user to create
     * @param connection connection with database
     **/
    void createUser(User user, Connection connection) throws DatabaseException;

    /**
     * Updates existing user. Used with {@link TransactionManager} in
     * {@link app.database.transaction.TransactionExecutor#execute(Connection)}
     * @param user user to update
     * @param connection connection with database
     **/
    void updateUser(User user, Connection connection) throws DatabaseException;

    /**
     * Checks if user with given email exists
     * @param email email of user
     * @return true if user with given email exists and false otherwise
     **/
    boolean existsUserByEmail(String email) throws DatabaseException;

    /**
     * Count missed days of all active orders created by user with given id
     * If order deadline date is already in the past and order is
     * still confirmed but not closed yet, count of missed days for this order
     * are equal : nowDate.days - order.deadlineDate.days.
     * @param userId id of user
     * @param currentDate now date
     * @return number of missed days of all orders created by user with given id
     **/
    int countMissedDays(int userId, LocalDate currentDate) throws DatabaseException;
}
