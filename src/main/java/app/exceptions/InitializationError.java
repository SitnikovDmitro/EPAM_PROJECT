package app.exceptions;

/**
 * Throws when important application component initialization fails
 **/
public class InitializationError extends Error {
    public InitializationError() {
        super();
    }

    public InitializationError(String message) {
        super(message);
    }

    public InitializationError(String message, Throwable cause) {
        super(message, cause);
    }

    public InitializationError(Throwable cause) {
        super(cause);
    }
}
