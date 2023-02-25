package app.database.pool.impl;

import app.database.pool.ConnectionPool;

import app.exceptions.InitializationError;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Implementation of connection pool with MySQL database using {@link MysqlDataSource}
 **/
public class MySQLConnectionPoolImpl implements ConnectionPool {
    private static final Logger logger = LogManager.getLogger(MySQLConnectionPoolImpl.class);

    private final MysqlDataSource ds;

    private MySQLConnectionPoolImpl() {
        try {
            JsonNode node = new ObjectMapper().readTree(getClass().getClassLoader().getResource("mysql-database-properties.json"));
            ds = new MysqlConnectionPoolDataSource();
            ds.setPassword(node.get("password").asText());
            ds.setUser(node.get("user").asText());
            ds.setURL(node.get("url").asText());

            logger.info("Connection pool impl initialization finished successfully");
        } catch (Exception exception) {
            logger.fatal("Connection pool impl  initialization failed", exception);
            throw new InitializationError("Connection pool initialization failed", exception);
        }
    }

    private static MySQLConnectionPoolImpl instance = null;

    public static MySQLConnectionPoolImpl getInstance(){
        if (true) throw new UnsupportedOperationException();

        if (instance==null) {
            instance = new MySQLConnectionPoolImpl();
        }

        return instance;
    }

    /**
     * Gets connection from connection pool
     * @return connection from connection pool
     **/
    @Override
    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}