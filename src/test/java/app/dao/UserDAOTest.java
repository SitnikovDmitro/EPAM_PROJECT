package app.dao;

import app.database.dao.UserDAO;
import app.database.dao.impl.UserDAOImpl;
import app.database.pool.ConnectionPool;
import app.database.pool.impl.H2ConnectionPoolImpl;
import app.database.transaction.TransactionManager;
import app.database.transaction.impl.TransactionManagerImpl;
import app.entity.User;
import app.enums.TransactionResult;
import app.enums.UserRole;
import app.database.initialization.DatabaseInitializer;
import app.exceptions.DatabaseException;
import app.utils.DeepEqualUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

public class UserDAOTest {
    private final UserDAO userDAO;
    private final TransactionManager transactionManager;

    public UserDAOTest() {
        ConnectionPool connectionPool = H2ConnectionPoolImpl.getInstance();

        userDAO = new UserDAOImpl(connectionPool);
        transactionManager = new TransactionManagerImpl(connectionPool);
    }

    @BeforeEach
    public void initialize() throws DatabaseException {
        DatabaseInitializer.getInstance().initialize();
    }

    @Test
    public void findUsersTestCase1() throws Exception {
        List<User> list1 = userDAO.findAll();
        List<User> list2 = userDAO.findUsers(null, null, false, false, 1, 1000);

        Assertions.assertEquals(list1.size(), list2.size());

        Assertions.assertTrue(list1.stream().allMatch(
                userA -> list2.stream().anyMatch(
                        userB -> DeepEqualUtil.getInstance().equals(userA, userB)
                )
        ));
    }

    @Test
    public void findUsersTestCase2() throws Exception {
        List<User> list = userDAO.findUsers(null, UserRole.READER, true, false, 1, 1000);

        Assertions.assertTrue(list.stream().allMatch(User::isBlocked));
    }

    @Test
    public void findUsersTestCase3() throws Exception {
        List<User> list = userDAO.findUsers(null, UserRole.READER, false, true, 1, 1000);

        Assertions.assertTrue(list.stream().allMatch(user -> user.getFine() > 0));
    }

    @Test
    public void findUsersTestCase4() throws Exception {
        List<User> list = userDAO.findUsers("m", UserRole.READER, false, false, 1, 1000);

        Assertions.assertTrue(list.stream().allMatch(user -> user.getFirstname().toLowerCase().contains("m") || user.getLastname().toLowerCase().contains("m") || user.getEmail().toLowerCase().contains("m")));
    }

    @Test
    public void findUsersTestCase5() throws Exception {
        List<User> list1 = userDAO.findAll();
        List<User> list2 = userDAO.findUsers(null, UserRole.LIBRARIAN, false, false, 1, 1000);
        list1.removeIf(user -> user.getRole() != UserRole.LIBRARIAN);

        Assertions.assertEquals(list1.size(), list2.size());

        Assertions.assertTrue(list1.stream().allMatch(
                userA -> list2.stream().anyMatch(
                        userB -> DeepEqualUtil.getInstance().equals(userA, userB)
                )
        ));
    }

    @Test
    public void findUsersTestCase6() throws Exception {
        List<User> list = userDAO.findUsers("m", UserRole.LIBRARIAN, false, false, 1, 1000);

        Assertions.assertTrue(list.stream().allMatch(user -> user.getFirstname().toLowerCase().contains("m") || user.getLastname().toLowerCase().contains("m") || user.getEmail().toLowerCase().contains("m")));
    }

    @Test
    public void findUsersTestCase7() throws Exception {
        List<User> list1 = userDAO.findUsers(null, null, false, false, 1, 1000);

        Assertions.assertEquals(list1.size(), 11);

        List<User> list2 = userDAO.findUsers(null, null, false, false, 1, 2);
        Assertions.assertEquals(list2.size(), 2);
        Assertions.assertTrue(DeepEqualUtil.getInstance().equals(list1.get(0), list2.get(0)));
        Assertions.assertTrue(DeepEqualUtil.getInstance().equals(list1.get(1), list2.get(1)));

        List<User> list3 = userDAO.findUsers(null, null, false, false, 2, 10);
        Assertions.assertEquals(list3.size(), 1);
        Assertions.assertTrue(DeepEqualUtil.getInstance().equals(list1.get(10), list3.get(0)));
    }

    @Test
    public void countUsersTest() throws Exception {
        Assertions.assertEquals(userDAO.countUsers(null, null, false, false), 11);
        Assertions.assertEquals(userDAO.countUsers(null, UserRole.ADMIN, false, false), 1);
        Assertions.assertEquals(userDAO.countUsers(null, UserRole.READER, true, false), 1);
        Assertions.assertEquals(userDAO.countUsers(null, UserRole.LIBRARIAN, false, false), 3);
    }

    @Test
    public void findUserByIdTest() throws Exception {
        User userA1 = userDAO.findUserById(2).orElseThrow();
        User userB1 = new User(2, 0, false,"mary@gmail.com", "Mary", "Smith", "A6xnQhbz4Vx2HuGl4lXwZ5U2I8iziLRFnhP5eNfIRvQ=", UserRole.LIBRARIAN, LocalDate.of(2023, 1, 1));

        User userA2 = userDAO.findUserById(1).orElseThrow();
        User userB2 = new User(1, 0, false,"admin@gmail.com", "Mark", "Brown", "mvFbM25qlhmShTffMLLmojdlafz51+dz7M7eZWBlKaA=", UserRole.ADMIN, LocalDate.of(2023, 1, 1));

        User userA3 = userDAO.findUserById(11).orElseThrow();
        User userB3 = new User(11, 10, false,"mary01@gmail.com", "Mary", "Stone", "alUXdu6t2r5dVS6kA/q6qoGCX28F19lFQ5zNTn3AkCk=", UserRole.READER, LocalDate.of(2023, 1, 1));

        Assertions.assertTrue(DeepEqualUtil.getInstance().equals(userA1, userB1));
        Assertions.assertTrue(DeepEqualUtil.getInstance().equals(userA2, userB2));
        Assertions.assertTrue(DeepEqualUtil.getInstance().equals(userA3, userB3));
    }



    @Test
    public void deleteUserByIdTest() throws Exception {
        transactionManager.execute(connection -> {
            userDAO.deleteUserById(2, connection);
            return TransactionResult.COMMIT;
        });
        List<User> list2 = userDAO.findAll();
        transactionManager.execute(connection -> {
            userDAO.deleteUserById(3, connection);
            return TransactionResult.COMMIT;
        });
        List<User> list3 = userDAO.findAll();
        transactionManager.execute(connection -> {
            userDAO.deleteUserById(4, connection);
            return TransactionResult.COMMIT;
        });
        List<User> list4 = userDAO.findAll();

        Assertions.assertTrue(list2.stream().allMatch(user -> user.getId() != 2));
        Assertions.assertTrue(list3.stream().allMatch(user -> user.getId() != 2 && user.getId() != 3));
        Assertions.assertTrue(list4.stream().allMatch(user -> user.getId() != 2 && user.getId() != 3 && user.getId() != 4));
    }

    @Test
    public void createUserTestCase1() throws Exception {
        User userA = new User(0, 0, false,"maryaa@gmail.com", "Mary", "Smith", "NHKtu8uWd9G0U2XTfZbRwzIX10VWdXf9m9XCdmolgyA=", UserRole.LIBRARIAN, LocalDate.of(2023, 1, 1));

        transactionManager.execute(connection -> {
            userDAO.createUser(userA, connection);
            return TransactionResult.COMMIT;
        });

        User userB = userDAO.findUserById(userA.getId()).orElseThrow();

        Assertions.assertTrue(userA.getId() != 0);
        Assertions.assertTrue(DeepEqualUtil.getInstance().equals(userA, userB));
    }

    @Test
    public void createUserTestCase2() throws Exception {
        User userA = new User(0, 10, true,"alan@gmail.com", "Alan", "Walk", "NHKtu8uWd9G0U2XTfZbRwzIX10VWdXf9m9XCdmolgyA=", UserRole.ADMIN, LocalDate.of(2023, 1, 1));
        transactionManager.execute(connection -> {
            userDAO.createUser(userA, connection);
            return TransactionResult.COMMIT;
        });
        User userB = userDAO.findUserById(userA.getId()).orElseThrow();

        Assertions.assertTrue(userA.getId() != 0);
        Assertions.assertTrue(DeepEqualUtil.getInstance().equals(userA, userB));
    }

    @Test
    public void updateUserTest() throws Exception {
        User userA = userDAO.findUserById(1).orElseThrow();
        userA.setBlocked(true);
        userA.setFine(22);
        userA.setRole(UserRole.ADMIN);
        transactionManager.execute(connection -> {
            userDAO.updateUser(userA, connection);
            return TransactionResult.COMMIT;
        });
        User userB = userDAO.findUserById(1).orElseThrow();

        Assertions.assertTrue(DeepEqualUtil.getInstance().equals(userA, userB));
    }

    @Test
    public void existsUserByEmailTest() throws Exception {
        Assertions.assertTrue(userDAO.existsUserByEmail("admin@gmail.com"));
        Assertions.assertTrue(userDAO.existsUserByEmail("john@gmail.com"));
        Assertions.assertTrue(userDAO.existsUserByEmail("mark@gmail.com"));
        Assertions.assertTrue(userDAO.existsUserByEmail("mary01@gmail.com"));

        Assertions.assertFalse(userDAO.existsUserByEmail("jack@gmail.com"));
        Assertions.assertFalse(userDAO.existsUserByEmail("smg@gmail.com"));
        Assertions.assertFalse(userDAO.existsUserByEmail("sda@gmail.com"));
        Assertions.assertFalse(userDAO.existsUserByEmail("marymary@gmail.com"));
    }

    @Test
    public void findUserByEmailTest() throws Exception {
        Assertions.assertTrue(userDAO.findUserByEmail("admin@gmail.com").isPresent());
        Assertions.assertTrue(userDAO.findUserByEmail("john@gmail.com").isPresent());
        Assertions.assertTrue(userDAO.findUserByEmail("mark@gmail.com").isPresent());
        Assertions.assertTrue(userDAO.findUserByEmail("mary01@gmail.com").isPresent());

        Assertions.assertFalse(userDAO.findUserByEmail("jack@gmail.com").isPresent());
        Assertions.assertFalse(userDAO.findUserByEmail("smg@gmail.com").isPresent());
        Assertions.assertFalse(userDAO.findUserByEmail("sda@gmail.com").isPresent());
        Assertions.assertFalse(userDAO.findUserByEmail("marymary@gmail.com").isPresent());
    }
}
