package app.exceptions;

/**
 * Throws when database operation error occurred in such situations as
 * losing database connection, invalid sql queries executing, updating and deleting
 * non-existent records and others
 **/
public class DatabaseException extends Exception {
    public DatabaseException() {}

    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(Throwable cause) {
        super(cause);
    }

    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
