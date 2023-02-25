package app.dao;

import app.database.dao.PublisherDAO;
import app.database.dao.impl.PublisherDAOImpl;
import app.database.initialization.DatabaseInitializer;
import app.database.pool.ConnectionPool;
import app.database.pool.impl.H2ConnectionPoolImpl;
import app.database.transaction.TransactionManager;
import app.database.transaction.impl.TransactionManagerImpl;
import app.entity.Publisher;
import app.enums.TransactionResult;
import app.exceptions.DatabaseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class PublisherDAOTest {
    private final PublisherDAO publisherDAO;
    private final TransactionManager transactionManager;

    public PublisherDAOTest() {
        ConnectionPool connectionPool = H2ConnectionPoolImpl.getInstance();

        publisherDAO = new PublisherDAOImpl(connectionPool);

        transactionManager = new TransactionManagerImpl(connectionPool);
    }

    @BeforeEach
    public void initialize() throws DatabaseException {
        DatabaseInitializer.getInstance().initialize();
    }


    @Test
    public void savePublisherTestCase1() throws Exception {
        Publisher publisher = new Publisher(1, "Pearson");

        transactionManager.execute(connection -> {
            publisherDAO.savePublisher(publisher, connection);
            return TransactionResult.COMMIT;
        });
    }

    @Test
    public void savePublisherTestCase2() throws Exception {
        Publisher publisher = new Publisher(1, "New");

        transactionManager.execute(connection -> {
            publisherDAO.savePublisher(publisher, connection);
            return TransactionResult.COMMIT;
        });

        Assertions.assertEquals("New", publisherDAO.findPublishersByTitle("New").get(0).getTitle());
        Assertions.assertEquals("Pearson", publisherDAO.findPublishersByTitle("Pearson").get(0).getTitle());
    }

    @Test
    public void findPublishersTest() throws Exception {
        List<Publisher> publisher = publisherDAO.findPublishersByTitle("Bloo");

        Assertions.assertEquals(1, publisher.size());
        Assertions.assertEquals(4, publisher.get(0).getId());
        Assertions.assertEquals("Bloomsbury", publisher.get(0).getTitle());
    }
}