package app.service;

import app.database.dao.BookDAO;
import app.database.dao.UserDAO;

import app.database.dao.impl.BookDAOImpl;
import app.database.pool.ConnectionPool;
import app.database.transaction.TransactionManager;
import app.database.transaction.impl.TransactionManagerImpl;
import app.entity.User;
import app.enums.UserRole;

import app.exceptions.CaptchaVerificationException;
import app.service.other.CaptchaVerificationService;
import app.service.other.MailService;
import app.tuples.UserCreationEditionResult;
import app.tuples.UserFindResult;
import app.service.entity.UserService;
import app.service.entity.impl.UserServiceImpl;
import app.service.utils.CryptographicUtil;
import app.service.utils.impl.CryptographicUtilImpl;
import app.service.validation.Validator;
import app.service.validation.impl.ValidatorImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UserServiceTest {
    BookDAO bookDAO;
    UserDAO userDAO;
    UserService userService;

    @BeforeEach
    public void initialize() throws SQLException, CaptchaVerificationException {
        Connection connection = Mockito.mock(Connection.class);
        ConnectionPool connectionPool = Mockito.mock(ConnectionPool.class);

        Mockito.doReturn(connection).when(connectionPool).getConnection();

        TransactionManager transactionManager = new TransactionManagerImpl(connectionPool);

        userDAO = Mockito.mock(UserDAO.class);
        bookDAO = new BookDAOImpl(connectionPool);

        CaptchaVerificationService captchaVerificationService = Mockito.mock(CaptchaVerificationService.class);
        Mockito.doReturn(true).when(captchaVerificationService).isCaptchaValid(Mockito.any());

        Validator validator = new ValidatorImpl(bookDAO, userDAO, captchaVerificationService);
        CryptographicUtil cryptographicUtil = new CryptographicUtilImpl();
        MailService mailService = Mockito.mock(MailService.class);

        userService = new UserServiceImpl(userDAO, validator, cryptographicUtil, mailService, transactionManager);
    }

    public User getTestUser() {
        User user = new User();
        user.setId(4);
        user.setEmail("elon@gmail.com");
        user.setFirstname("Elon");
        user.setLastname("Musk");
        user.setBlocked(false);
        user.setFine(0);
        user.setRole(UserRole.LIBRARIAN);
        user.setLastFineRecalculationDate(LocalDate.of(2023, 1, 1));
        user.setPasswordHash("iNQmb9TmM40TuEX88olXnSCciXgjuSF9o+Fhk28DFYk=");
        return user;
    }


    @Test
    public void blockReaderTest() throws Exception {
        User user = Mockito.mock(User.class);
        Mockito.doReturn(UserRole.READER).when(user).getRole();
        Mockito.doReturn(false).when(user).isBlocked();
        Mockito.doReturn(Optional.of(user)).when(userDAO).findUserById(11);

        userService.blockReader(11);

        Mockito.verify(userDAO, Mockito.times(1)).updateUser(Mockito.any(User.class), Mockito.any(Connection.class));
    }

    @Test
    public void unblockReaderTest() throws Exception {
        User user = Mockito.mock(User.class);
        Mockito.doReturn(UserRole.READER).when(user).getRole();
        Mockito.doReturn(true).when(user).isBlocked();
        Mockito.doReturn(Optional.of(user)).when(userDAO).findUserById(5);

        userService.unblockReader(5);

        Mockito.verify(userDAO, Mockito.times(1)).updateUser(Mockito.any(User.class), Mockito.any(Connection.class));
    }

    @Test
    public void payReaderFineTest() throws Exception {
        User user = Mockito.mock(User.class);
        Mockito.doReturn(UserRole.READER).when(user).getRole();
        Mockito.doReturn(1000).when(user).getFine();
        Mockito.doReturn(Optional.of(user)).when(userDAO).findUserById(5);

        userService.payReaderFine(5);

        Mockito.verify(userDAO, Mockito.times(1)).updateUser(Mockito.any(User.class), Mockito.any(Connection.class));
    }

    @Test
    public void deleteLibrarianTest() throws Exception {
        User user = Mockito.mock(User.class);
        Mockito.doReturn(UserRole.LIBRARIAN).when(user).getRole();
        Mockito.doReturn(2).when(user).getId();
        Mockito.doReturn(Optional.of(user)).when(userDAO).findUserById(2);

        userService.deleteLibrarian(2);

        Mockito.verify(userDAO, Mockito.times(1)).deleteUserById(Mockito.anyInt(), Mockito.any(Connection.class));
    }

    @Test
    public void getUserByEmailAndPasswordTestCase1() throws Exception {
        User user = Mockito.mock(User.class);
        Mockito.doReturn("mvFbM25qlhmShTffMLLmojdlafz51+dz7M7eZWBlKaA=").when(user).getPasswordHash();
        Mockito.doReturn(Optional.of(user)).when(userDAO).findUserByEmail("admin@gmail.com");

        UserFindResult result = userService.findUserByEmailAndPassword("admin@gmail.com", "0000");

        Assertions.assertNull(result.getValidationFeedbackKey());
        Assertions.assertTrue(result.getSuccess());
    }

    @Test
    public void getUserByEmailAndPasswordTestCase3() throws Exception {
        User user = Mockito.mock(User.class);
        Mockito.doReturn("2ixXXkjudlG56ZpREha4J+9t6nygjnfHBVsYudkYqEI=").when(user).getPasswordHash();
        Mockito.doReturn(Optional.of(user)).when(userDAO).findUserByEmail("john@gmail.com");

        UserFindResult result = userService.findUserByEmailAndPassword("john@gmail.com", "124");

        Assertions.assertEquals(result.getValidationFeedbackKey(), "invalidCredentials");
        Assertions.assertFalse(result.getSuccess());
        Assertions.assertNull(result.getUser());
    }


   @Test
    public void editUserTestCase1() throws Exception {
        Mockito.doReturn(Optional.of(getTestUser())).when(userDAO).findUserById(4);
        Mockito.doReturn(false).when(userDAO).existsUserByEmail("e@m.ml");

        UserCreationEditionResult result = userService.editUser(4, "E", "M", "e@m.ml");

        Assertions.assertTrue(result.getSuccess());
        Assertions.assertNotNull(result.getUser());

        Mockito.verify(userDAO, Mockito.times(1)).updateUser(Mockito.any(User.class), Mockito.any(Connection.class));
    }

    @Test
    public void editUserTestCase2() throws Exception {
        Mockito.doReturn(Optional.of(getTestUser())).when(userDAO).findUserById(4);

        UserCreationEditionResult result = userService.editUser(4, "E1", "M", "em");

        Assertions.assertTrue(result.getLastnameValid());
        Assertions.assertFalse(result.getFirstnameValid());
        Assertions.assertFalse(result.getEmailValid());
        Assertions.assertEquals(result.getFirstnameValidationFeedbackKey(), "firstnameContainsInvalidCharacters");
        Assertions.assertEquals(result.getEmailValidationFeedbackKey(), "emailDoesNotMatchPattern");
        Assertions.assertFalse(result.getSuccess());
        Assertions.assertNull(result.getUser());

        Mockito.verify(userDAO, Mockito.never()).updateUser(Mockito.any(User.class), Mockito.any(Connection.class));
    }

    @Test
    public void editUserTestCase3() throws Exception {
        Mockito.doReturn(Optional.of(getTestUser())).when(userDAO).findUserById(4);

        UserCreationEditionResult result = userService.editUser(4, "E", "M", "em", "pass");

        Assertions.assertTrue(result.getLastnameValid());
        Assertions.assertTrue(result.getFirstnameValid());
        Assertions.assertFalse(result.getEmailValid());
        Assertions.assertFalse(result.getPasswordValid());
        Assertions.assertEquals(result.getPasswordValidationFeedbackKey(), "passwordIsTooShort");
        Assertions.assertEquals(result.getEmailValidationFeedbackKey(), "emailDoesNotMatchPattern");
        Assertions.assertFalse(result.getSuccess());
        Assertions.assertNull(result.getUser());

        Mockito.verify(userDAO, Mockito.never()).updateUser(Mockito.any(User.class), Mockito.any(Connection.class));
    }

    @Test
    public void createReaderTestCase1() throws Exception {
        Mockito.doReturn(false).when(userDAO).existsUserByEmail("n@n.ml");

        UserCreationEditionResult result = userService.createReader("New", "New", "n@n.ml", "12345678", null);

        Assertions.assertTrue(result.getSuccess());

        Mockito.verify(userDAO, Mockito.times(1)).createUser(Mockito.any(User.class), Mockito.any(Connection.class));
    }

    @Test
    public void createReaderTestCase2() throws Exception {
        UserCreationEditionResult result = userService.createReader("New", "New1", "nn", "1234567", null);

        Assertions.assertTrue(result.getFirstnameValid());
        Assertions.assertFalse(result.getLastnameValid());
        Assertions.assertFalse(result.getEmailValid());
        Assertions.assertFalse(result.getPasswordValid());
        Assertions.assertEquals(result.getLastnameValidationFeedbackKey(), "lastnameContainsInvalidCharacters");
        Assertions.assertEquals(result.getEmailValidationFeedbackKey(), "emailDoesNotMatchPattern");
        Assertions.assertEquals(result.getPasswordValidationFeedbackKey(), "passwordIsTooShort");
        Assertions.assertFalse(result.getSuccess());
        Assertions.assertNull(result.getUser());

        Mockito.verify(userDAO, Mockito.never()).createUser(Mockito.any(User.class), Mockito.any(Connection.class));
    }

    @Test
    public void createLibrarianTestCase1() throws Exception {
        Mockito.doReturn(false).when(userDAO).existsUserByEmail("abcdn@nabcd.ml");

        UserCreationEditionResult result = userService.createLibrarian("Ім", "Пр", "abcdn@nabcd.ml", "12345678");

        Assertions.assertTrue(result.getSuccess());

        Mockito.verify(userDAO, Mockito.times(1)).createUser(Mockito.any(User.class), Mockito.any(Connection.class));
    }

    @Test
    public void createLibrarianTestCase2() throws Exception {
        Mockito.doReturn(true).when(userDAO).existsUserByEmail("admin@gmail.com");

        UserCreationEditionResult result = userService.createLibrarian("Ім1", "Ім", "admin@gmail.com", "");

        Assertions.assertFalse(result.getFirstnameValid());
        Assertions.assertTrue(result.getLastnameValid());
        Assertions.assertFalse(result.getEmailValid());
        Assertions.assertFalse(result.getPasswordValid());
        Assertions.assertEquals(result.getFirstnameValidationFeedbackKey(), "firstnameContainsInvalidCharacters");
        Assertions.assertEquals(result.getEmailValidationFeedbackKey(), "emailIsDuplicated");
        Assertions.assertEquals(result.getPasswordValidationFeedbackKey(), "passwordIsEmpty");
        Assertions.assertFalse(result.getSuccess());
        Assertions.assertNull(result.getUser());

        Mockito.verify(userDAO, Mockito.never()).createUser(Mockito.any(User.class), Mockito.any(Connection.class));
    }
}
