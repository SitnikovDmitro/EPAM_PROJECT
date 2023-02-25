package app.database.transaction;

import app.enums.TransactionResult;
import app.exceptions.DatabaseException;

import java.sql.Connection;

/**
 * Database operations function to execute in single transaction
 **/
@FunctionalInterface
public interface TransactionExecutor {
    /**
     * Database operations function to execute in single transaction. Return
     * {@link TransactionResult#COMMIT} to commit transaction and
     * apply changes and {@link TransactionResult#ROLLBACK} to rollback
     * transaction and cancel changes
     * @param connection with disabled automatic commit
     * @return transaction result
     **/
    TransactionResult execute(Connection connection) throws DatabaseException;
}
