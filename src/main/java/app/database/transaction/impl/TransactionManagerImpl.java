package app.database.transaction.impl;

import app.database.pool.ConnectionPool;
import app.database.transaction.TransactionExecutor;
import app.database.transaction.TransactionManager;
import app.enums.TransactionResult;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Service class to execute transaction
 **/
public class TransactionManagerImpl implements TransactionManager {
    private final ConnectionPool connectionPool;

    public TransactionManagerImpl(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    /**
     * Executes database operations in single transaction
     * Transaction commits if executor returns TransactionResult.COMMIT, rollbacks otherwise
     * If executor throws DatabaseException transaction rollbacks
     * @param executor database operations function to execute in single transaction
     **/
    @Override
    public void execute(TransactionExecutor executor) throws DatabaseException {
        try (Connection connection = connectionPool.getConnection()) {

            connection.setAutoCommit(false);

            try {
                TransactionResult operation = executor.execute(connection);
                if (operation == TransactionResult.COMMIT) {
                    connection.commit();
                } else {
                    connection.rollback();
                }
            } catch (DatabaseException exception) {
                connection.rollback();
                throw new DatabaseException("Transaction execution failed", exception);
            }

        } catch (SQLException exception) {
            throw new DatabaseException("Connection creation failed", exception);
        }
    }
}
