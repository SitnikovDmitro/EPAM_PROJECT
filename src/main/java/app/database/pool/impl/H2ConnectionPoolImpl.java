package app.database.pool.impl;

import app.database.pool.ConnectionPool;
import app.exceptions.InitializationError;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.h2.jdbcx.JdbcConnectionPool;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Implementation of connection pool with in-memory H2 database using {@link JdbcConnectionPool}
 **/
public class H2ConnectionPoolImpl  implements ConnectionPool {
    private static final Logger logger = LogManager.getLogger(H2ConnectionPoolImpl.class);

    private final JdbcConnectionPool cp;

    private H2ConnectionPoolImpl() {
        try {
            JsonNode node = new ObjectMapper().readTree(getClass().getClassLoader().getResource("h2-database-properties.json"));
            cp = JdbcConnectionPool.create(node.get("url").asText(), node.get("user").asText(), node.get("password").asText());

            logger.info("Connection pool impl initialization finished successfully");
        } catch (Exception exception) {
            logger.fatal("Connection pool impl  initialization failed", exception);
            throw new InitializationError("Connection pool initialization failed", exception);
        }
    }

    private static H2ConnectionPoolImpl instance = null;

    public static H2ConnectionPoolImpl getInstance(){
        if (instance==null) {
            instance = new H2ConnectionPoolImpl();
        }

        return instance;
    }

    /**
     * Gets connection from connection pool
     * @return connection from connection pool
     **/
    @Override
    public Connection getConnection() throws SQLException {
        return cp.getConnection();
    }
}
