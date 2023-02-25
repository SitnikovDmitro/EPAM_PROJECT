package app.service.entity;

import app.entity.User;
import app.enums.BookFormat;
import app.enums.BookGenre;
import app.enums.BookLanguage;
import app.enums.BookSortCriteria;
import app.exceptions.CaptchaVerificationException;
import app.exceptions.DatabaseException;
import app.tuples.UserCreationEditionResult;
import app.tuples.SendAccessLinkResult;
import app.tuples.UserFindResult;

import java.util.List;
import java.util.Optional;

/**
 * Provides operations with user entity
 **/
public interface UserService {
    /**
     * Finds user by email address
     * @param email email address of user
     * @return found user entity in optional wrapper
     */
    Optional<User> findUserByEmail(String email) throws DatabaseException;

    /**
     * Finds reader by id
     * @param readerId id of user
     * @return found user entity in optional wrapper
     */
    Optional<User> findReaderById(int readerId) throws DatabaseException;

    /**
     * Finds readers
     * @param query reader search query by name or email (optional)
     * @param onlyBlocked reader search option to find only readers with blocking status (optional)
     * @param onlyWithFine reader search option to find only readers with positive fine (optional)
     * @param page pagination parameter
     * @param size pagination parameter
     * @return list of users matching query parameters
     */
    List<User> findReaders(String query, Boolean onlyBlocked, Boolean onlyWithFine, int page, int size) throws DatabaseException;

    /**
     * Counts readers
     * @param query reader search query by name or email (optional)
     * @param onlyBlocked reader search option to find only readers with blocking status (optional)
     * @param onlyWithFine reader search option to find only readers with positive fine (optional)
     * @return number of users matching query parameters
     */
    int countReaders(String query, Boolean onlyBlocked, Boolean onlyWithFine) throws DatabaseException;

    /**
     * Finds readers
     * @see UserService#findReaders(String, Boolean, Boolean, int, int)
     **/
    List<User> findReaders(String query, int page, int size) throws DatabaseException;

    /**
     * Counts readers
     * @see UserService#countReaders(String, Boolean, Boolean)
     **/
    int countReaders(String query) throws DatabaseException;

    /**
     * Blocks reader by id
     * @param userId id of reader
     */
    void blockReader(Integer userId) throws DatabaseException;

    /**
     * Unlocks reader by id
     * @param userId id of reader
     */
    void unblockReader(Integer userId) throws DatabaseException;

    /**
     * Pays fine reader by id (makes reader's fine equal to zero)
     * @param userId id of reader
     */
    int payReaderFine(Integer userId) throws DatabaseException;

    /**
     * Recalculate fine of reader by id
     * @param userId id of reader
     */
    void recalculateReaderFineById(Integer userId) throws DatabaseException;

    /**
     * Recalculate fine of reader
     * @param user reader
     */
    void recalculateReaderFine(User user) throws DatabaseException;

    /**
     * Refreshes reader
     * @param user reader
     */
    void refreshReader(User user) throws DatabaseException;

    /**
     * Finds librarians
     * @param query librarian search query by name or email (optional)
     * @param page pagination parameter
     * @param size pagination parameter
     * @return list of users matching query parameters
     */
    List<User> findLibrarians(String query, int page, int size) throws DatabaseException;

    /**
     * Counts librarians
     * @param query librarian search query by name or email (optional)
     * @return count of users matching query parameters
     */
    int countLibrarians(String query) throws DatabaseException;

    /**
     * Deletes librarian by id
     * @param userId id of user
     */
    void deleteLibrarian(Integer userId) throws DatabaseException;

    /**
     * Finds user by login credentials
     * @param email user email
     * @param password user password
     * @return operation result that contains user entity if such
     *         exists or credentials validation information otherwise
     */
    UserFindResult findUserByEmailAndPassword(String email, String password) throws DatabaseException;

    /**
     * Edits user
     * @param id id of user to be changed
     * @param firstname firstname of user
     * @param lastname lastname of user
     * @param email email of user
     * @param password password of user
     * @return operation result that contains user entity if operation finishes successfully
     *         or editing parameters validation information otherwise
     */
    UserCreationEditionResult editUser(int id, String firstname, String lastname, String email, String password) throws DatabaseException;

    /**
     * Sends link to email containing secret access code to get access to account if password was lost
     * @param email email address to send link
     * @param password new password
     * @param url url of http request mapping that process secret code verification and give access to account
     * @param language active language
     * @return operation result that contains sha-256 hash of secret code that was sent to email or
     *         email and password validation information otherwise
     */
    SendAccessLinkResult sendAccessLink(String email, String password, String url, String language) throws DatabaseException;

    /**
     * Edits user
     * @param id id of user to be changed
     * @param firstname firstname of user
     * @param lastname lastname of user
     * @param email email of user
     * @return operation result that contains user entity if operation finishes successfully
     *         or editing parameters validation information otherwise
     */
    UserCreationEditionResult editUser(int id, String firstname, String lastname, String email) throws DatabaseException;

    /**
     * Creates new librarian
     * @param firstname firstname of librarian
     * @param lastname lastname of librarian
     * @param email email of librarian
     * @param password password of librarian
     * @return operation operation result that contains user entity if librarian creation finishes successfully
     *         or creation parameters validation information otherwise
     */
    UserCreationEditionResult createLibrarian(String firstname, String lastname, String email, String password) throws DatabaseException, CaptchaVerificationException;

    /**
     * Creates new reader
     * @param firstname firstname of reader
     * @param lastname lastname of reader
     * @param email email of reader
     * @param password password of reader
     * @param captchaResponse response of google recaptha
     * @return operation operation result that contains user entity if reader creation finishes successfully
     *         or creation parameters validation information otherwise
     */
    UserCreationEditionResult createReader(String firstname, String lastname, String email, String password, String captchaResponse) throws DatabaseException, CaptchaVerificationException;
}
