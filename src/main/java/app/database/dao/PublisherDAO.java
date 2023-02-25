package app.database.dao;

import app.database.transaction.TransactionManager;
import app.entity.Publisher;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.util.List;

/**
 * Provides database operations with table of publishers
 **/
public interface PublisherDAO {
    /**
     * Creates new publisher. Used with {@link TransactionManager} in
     * {@link app.database.transaction.TransactionExecutor#execute(Connection)}
     * @param publisher publisher to create
     * @param connection connection with database
     **/
    void savePublisher(Publisher publisher, Connection connection) throws DatabaseException;

    /**
     * Finds publishers by title
     * @param titleQuery title query (optional)
     * @return list of found publishers
     **/
    List<Publisher> findPublishersByTitle(String titleQuery) throws DatabaseException;
}
