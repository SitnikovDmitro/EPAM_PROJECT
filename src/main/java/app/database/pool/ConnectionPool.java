package app.database.pool;

import java.sql.Connection;
import java.sql.SQLException;


/**
 * Connection pool interface
 */
public interface ConnectionPool {
    /**
     * Gets connection from connection pool
     * @return connection from connection pool
     **/
    Connection getConnection() throws SQLException;
}
