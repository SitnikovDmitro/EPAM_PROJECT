package app.database.initialization;

import app.database.pool.ConnectionPool;
import app.database.pool.impl.H2ConnectionPoolImpl;
import app.exceptions.DatabaseException;
import app.exceptions.InitializationError;


import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class for database initialization (for testing and demonstration)
 **/
public final class DatabaseInitializer {
    private static DatabaseInitializer instance = null;

    private final ConnectionPool connectionPool;

    private final List<String> commands;

    private DatabaseInitializer (ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;

        try {
            executeSqlCommands(extractSqlCommands("sql-scripts/schema.sql"));
            commands = extractSqlCommands("sql-scripts/data.sql");
        } catch (Exception exception) {
            throw new InitializationError("Database initialization failed", exception);
        }
    }

    /**
     * Deletes all records from all tables and fill them with test data (without book data tables)
     **/
    public void initialize() throws DatabaseException {
        executeSqlCommands(commands);
        createBlobs();
    }

    /**
     * Deletes all records from all tables and fill them with test data (with book data tables)
     **/
    public void initializeWithBookData() throws DatabaseException {
        executeSqlCommands(commands);
        createBlobs();
    }

    private List<String> extractSqlCommands(String resourceName) throws IOException {
        List<String> commands = new ArrayList<>();

        try (InputStream stream = getClass().getClassLoader().getResourceAsStream(resourceName)) {

            if (Objects.isNull(stream)) {
                throw new IOException("Recourse not found");
            }

            String content = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
            for (String command : content.replace('\n', ' ').replace('\t', ' ').replace('\r', ' ').split(";")) {
                if (!command.isBlank()) {
                    commands.add(command.trim()+";");
                }
            }
        }

        return commands;
    }

    private void executeSqlCommands(List<String> commands) throws DatabaseException {
        try(Connection con = connectionPool.getConnection();
            Statement stmt = con.createStatement()) {
            for (String command : commands) stmt.executeUpdate(command);
        } catch (SQLException exception) {
            throw new DatabaseException("Could not execute commands", exception);
        }
    }

    private void createBlobs() throws DatabaseException {
        try(Connection con = connectionPool.getConnection()) {

            for (int i = 1; i <= 20; i++) {
                try (PreparedStatement stmt = con.prepareStatement("INSERT INTO booksCover (bookId, data) VALUES (?, ?);");
                     InputStream stream = getClass().getClassLoader().getResourceAsStream("book-data/cover/"+i+".png")) {

                    if (stream == null) throw new IOException("Resource not found : book-data/cover/"+i+".png");

                    stmt.setInt(1, i);
                    stmt.setBytes(2, stream.readAllBytes());

                    stmt.executeUpdate();
                }
            }

            for (int i : List.of(1, 3, 8, 9, 12)) {
                try (PreparedStatement stmt = con.prepareStatement("INSERT INTO booksContent (bookId, data) VALUES (?, ?);");
                     InputStream stream = getClass().getClassLoader().getResourceAsStream("book-data/content/"+i+".pdf")) {

                    if (stream == null) throw new IOException("Resource not found : book-data/content/"+i+".pdf");

                    stmt.setInt(1, i);
                    stmt.setBytes(2, stream.readAllBytes());

                    stmt.executeUpdate();
                }
            }
        } catch (SQLException exception) {
            throw new DatabaseException("Could not execute commands", exception);
        } catch (IOException exception) {
            throw new DatabaseException("Could not load recourse", exception);
        }
    }

    public static DatabaseInitializer getInstance(){
        if (instance==null) instance = new DatabaseInitializer(H2ConnectionPoolImpl.getInstance());
        return instance;
    }
}
