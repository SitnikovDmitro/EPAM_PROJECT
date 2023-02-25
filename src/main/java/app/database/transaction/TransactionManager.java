package app.database.transaction;

import app.exceptions.DatabaseException;

/**
 * Service class to execute transaction
 **/
public interface TransactionManager {
    /**
     * Executes database operations in single transaction
     * Transaction commits if executor returns TransactionResult.COMMIT, rollbacks otherwise
     * If executor throws DatabaseException transaction rollbacks
     * @param executor database operations function to execute in single transaction
     **/
    void execute(TransactionExecutor executor) throws DatabaseException;
}
