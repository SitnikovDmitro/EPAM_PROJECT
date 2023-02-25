package app.service.entity.impl;

import app.constants.Constants;
import app.database.dao.UserDAO;
import app.database.transaction.TransactionManager;
import app.entity.User;
import app.enums.TransactionResult;
import app.enums.UserRole;
import app.exceptions.CaptchaVerificationException;
import app.exceptions.DatabaseException;
import app.exceptions.InvalidStateException;
import app.tuples.UserCreationEditionResult;
import app.tuples.SendAccessLinkResult;
import app.tuples.UserFindResult;
import app.service.entity.UserService;
import app.service.other.MailService;
import app.service.utils.CryptographicUtil;
import app.service.validation.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    private final UserDAO userDAO;
    private final Validator validator;
    private final CryptographicUtil cryptographicUtil;
    private final MailService mailService;
    private final TransactionManager transactionManager;

    public UserServiceImpl(UserDAO userDAO, Validator validator, CryptographicUtil cryptographicUtil, MailService mailService, TransactionManager transactionManager) {
        this.userDAO = userDAO;
        this.validator = validator;
        this.cryptographicUtil = cryptographicUtil;
        this.mailService = mailService;
        this.transactionManager = transactionManager;
    }

    @Override
    public Optional<User> findUserByEmail(String email) throws DatabaseException {
        return userDAO.findUserByEmail(email);
    }

    @Override
    public Optional<User> findReaderById(int readerId) throws DatabaseException {
        return userDAO.findUserById(readerId).filter(user -> user.getRole() == UserRole.READER);
    }


    @Override
    public List<User> findReaders(String query, Boolean onlyBlocked, Boolean onlyWithFine, int page, int size) throws DatabaseException {
        return userDAO.findUsers(query, UserRole.READER, onlyBlocked, onlyWithFine, page, size);
    }

    @Override
    public int countReaders(String query, Boolean onlyBlocked, Boolean onlyWithFine) throws DatabaseException {
        return userDAO.countUsers(query, UserRole.READER, onlyBlocked, onlyWithFine);
    }

    @Override
    public List<User> findReaders(String query, int page, int size) throws DatabaseException {
        return findReaders(query, false, false, page, size);
    }

    @Override
    public int countReaders(String query) throws DatabaseException {
        return userDAO.countUsers(query, UserRole.READER, false, false);
    }

    @Override
    public void blockReader(Integer userId) throws DatabaseException {
        logger.debug("Blocking reader with id = "+userId+" started");

        User user = userDAO.findUserById(userId).orElseThrow();
        if (user.getRole() != UserRole.READER) throw new InvalidStateException("User is not a reader");
        user.setBlocked(true);

        transactionManager.execute(connection -> {
            userDAO.updateUser(user, connection);
            return TransactionResult.COMMIT;
        });

        logger.debug("Blocking reader with id = "+userId+" successfully finished");
    }

    @Override
    public void unblockReader(Integer userId) throws DatabaseException {
        logger.debug("Unlocking reader with id = "+userId+" started");

        User user = userDAO.findUserById(userId).orElseThrow();
        if (user.getRole() != UserRole.READER) throw new InvalidStateException("User is not a reader");
        user.setBlocked(false);

        transactionManager.execute(connection -> {
            userDAO.updateUser(user, connection);
            return TransactionResult.COMMIT;
        });

        logger.debug("Unlocking reader with id = "+userId+" successfully finished");
    }

    @Override
    public int payReaderFine(Integer userId) throws DatabaseException {
        logger.debug("Paying fine of reader with id = "+userId+" started");

        User user = userDAO.findUserById(userId).orElseThrow();
        if (user.getRole() != UserRole.READER) throw new InvalidStateException("User is not a reader");
        if (user.getFine() <= 0) throw new InvalidStateException("User does not have fine");

        int fine = user.getFine();
        user.setFine(0);

        transactionManager.execute(connection -> {
            userDAO.updateUser(user, connection);
            return TransactionResult.COMMIT;
        });

        logger.debug("Paying fine of reader with id = "+userId+" successfully finished");
        return fine;
    }

    @Override
    public void recalculateReaderFineById(Integer userId) throws DatabaseException {
        recalculateReaderFine(userDAO.findUserById(userId).orElseThrow());
    }

    @Override
    public void recalculateReaderFine(User user) throws DatabaseException {
        logger.debug("Recalculation fine of reader with id = "+user.getId()+" started");

        if (user.getRole() != UserRole.READER) throw new InvalidStateException("User is not a reader");

        LocalDate currentDate = LocalDate.now();
        if (user.getLastFineRecalculationDate().equals(currentDate)) {
            logger.debug("Recalculation fine of reader with id = "+user.getId()+" is redundant because of it has already been calculated at this date");
            return;
        }

        user.setFine(user.getFine() + userDAO.countMissedDays(user.getId(), currentDate) * Constants.FINE_PER_DAY);
        user.setLastFineRecalculationDate(currentDate);

        transactionManager.execute(connection -> {
            userDAO.updateUser(user, connection);
            return TransactionResult.COMMIT;
        });

        logger.debug("Recalculation fine of reader with id = "+user.getId()+" successfully finished");
    }

    @Override
    public void refreshReader(User user) throws DatabaseException {
        logger.debug("Refreshing of reader with id = "+user.getId()+" started");

        User reader = userDAO.findUserById(user.getId()).orElseThrow();
        recalculateReaderFine(reader);

        user.setId(reader.getId());
        user.setFine(reader.getFine());
        user.setBlocked(reader.isBlocked());
        user.setEmail(reader.getEmail());
        user.setFirstname(reader.getFirstname());
        user.setLastname(reader.getLastname());
        user.setPasswordHash(reader.getPasswordHash());
        user.setRole(reader.getRole());
        user.setLastFineRecalculationDate(reader.getLastFineRecalculationDate());

        logger.debug("Refreshing of reader with id = "+user.getId()+" successfully finished");
    }



    @Override
    public List<User> findLibrarians(String query, int page, int size) throws DatabaseException {
        return userDAO.findUsers(query, UserRole.LIBRARIAN, false, false, page, size);
    }

    @Override
    public int countLibrarians(String query) throws DatabaseException {
        return userDAO.countUsers(query, UserRole.LIBRARIAN, false, false);
    }

    @Override
    public void deleteLibrarian(Integer userId) throws DatabaseException {
        logger.debug("Deletion librarian with id = "+userId+" started");

        User user = userDAO.findUserById(userId).orElseThrow();
        if (user.getRole() != UserRole.LIBRARIAN) throw new InvalidStateException("User is not a librarian");

        transactionManager.execute(connection -> {
            userDAO.deleteUserById(user.getId(), connection);
            return TransactionResult.COMMIT;
        });

        logger.debug("Deletion librarian with id = "+userId+" successfully finished");
    }


    @Override
    public UserFindResult findUserByEmailAndPassword(String email, String password) throws DatabaseException {
        logger.debug("Extraction user by email = "+email+" and password = "+password+" started");

        UserFindResult result = new UserFindResult();

        if (validator.areCredentialsInvalid(email, password)) {
            result.setValidationFeedbackKey("invalidCredentials");
            logger.debug("Extraction user by email = "+email+" and password = "+password+" failed because of email or password are invalid (empty or too long)");
            return result;
        }

        Optional<User> user = userDAO.findUserByEmail(email);

        if (user.isEmpty() || !user.get().getPasswordHash().equals(cryptographicUtil.generateHash(password))) {
            result.setValidationFeedbackKey("invalidCredentials");
            logger.debug("Extraction user by email = "+email+" and password = "+password+" failed because of user with given credentials is not exists");
            return result;
        }

        result.setSuccess(true);
        result.setUser(user.get());

        logger.debug("Extraction user by email = "+email+" and password = "+password+" successfully finished");

        return result;
    }

    @Override
    public SendAccessLinkResult sendAccessLink(String email, String password, String url, String language) throws DatabaseException {
        logger.debug("Sending access link message for user with email = "+email+" and new password = "+password+" started");

        SendAccessLinkResult result = new SendAccessLinkResult();

        validator.validateUserExistingEmail(email, result);
        validator.validateUserPassword(password, result);

        result.setSuccess(result.getEmailValid() && result.getPasswordValid());

        if (!result.getSuccess()) {
            logger.info("Sending access link failed because of invalid parameters");
            return result;
        }

        String accessCode = cryptographicUtil.generateRandomCode();
        result.setAccessCodeHash(cryptographicUtil.generateHash(accessCode));

        mailService.sendAccessLinkAsync(email, url, accessCode, language);

        logger.debug("Sending access link for user with email = "+email+" and new password = "+password+" successfully finished");

        return result;
    }

    @Override
    public UserCreationEditionResult editUser(int id, String firstname, String lastname, String email) throws DatabaseException {
        return editUser(id, false, firstname, lastname, email, null);
    }

    @Override
    public UserCreationEditionResult editUser(int id, String firstname, String lastname, String email, String password) throws DatabaseException {
        return editUser(id, true, firstname, lastname, email, password);
    }

    @Override
    public UserCreationEditionResult createLibrarian(String firstname, String lastname, String email, String password) throws DatabaseException, CaptchaVerificationException {
        return createUser(false, firstname, lastname, email, password, UserRole.LIBRARIAN, null);
    }

    @Override
    public UserCreationEditionResult createReader(String firstname, String lastname, String email, String password, String captchaResponse) throws DatabaseException, CaptchaVerificationException {
        return createUser(true, firstname, lastname, email, password, UserRole.READER, captchaResponse);
    }

    private UserCreationEditionResult editUser(int id, boolean changePassword, String firstname, String lastname, String email, String password) throws DatabaseException {
        logger.debug("Edition user with id = "+id+" started");

        User user = userDAO.findUserById(id).orElseThrow();

        UserCreationEditionResult result = new UserCreationEditionResult();
        validator.validateUserFirstname(firstname, result);
        validator.validateUserLastname(lastname, result);
        validator.validateUserNewEmail(email, user.getEmail(), result);

        if (changePassword) {
            validator.validateUserPassword(password, result);
        } else {
            result.setPasswordValid(true);
        }

        result.setCaptchaValid(true);

        result.setSuccess(result.getFirstnameValid() && result.getLastnameValid() && result.getEmailValid() && result.getPasswordValid());

        if (!result.getSuccess()) {
            logger.info("Edition user with id = "+id+" failed because of invalid edition parameters");
            return result;
        }

        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setEmail(email);
        if (changePassword) user.setPasswordHash(cryptographicUtil.generateHash(password));

        transactionManager.execute(connection -> {
            userDAO.updateUser(user, connection);
            return TransactionResult.COMMIT;
        });

        result.setUser(user);

        logger.debug("Edition user with id = "+id+" successfully finished");
        return result;
    }

    private UserCreationEditionResult createUser(boolean validateCaptcha, String firstname, String lastname, String email, String password, UserRole userRole, String captchaResponse) throws DatabaseException, CaptchaVerificationException {
        logger.debug("Creation user with firstname = "+firstname+", email = "+email+"... started");

        UserCreationEditionResult result = new UserCreationEditionResult();
        validator.validateUserFirstname(firstname, result);
        validator.validateUserLastname(lastname, result);
        validator.validateUserNewEmail(email, result);
        validator.validateUserPassword(password, result);

        if (validateCaptcha) {
            validator.validateCaptcha(captchaResponse, result);
        } else {
            result.setCaptchaValid(true);
        }

        result.setSuccess(result.getFirstnameValid() && result.getLastnameValid() && result.getEmailValid() && result.getPasswordValid() && result.getCaptchaValid());

        if (!result.getSuccess()) {
            logger.info("Creation user failed because of invalid creation parameters");
            return result;
        }

        User user = new User(0, 0, false, email, firstname, lastname, cryptographicUtil.generateHash(password), userRole, LocalDate.now());

        transactionManager.execute(connection -> {
            userDAO.createUser(user, connection);
            return TransactionResult.COMMIT;
        });

        result.setUser(user);

        logger.debug("Creation user successfully finished");
        return result;
    }
}
