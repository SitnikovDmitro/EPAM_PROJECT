package app.exceptions;

/**
 * Throws when something has invalid state
 **/
public class InvalidStateException extends RuntimeException {
    public InvalidStateException() {
        super();
    }

    public InvalidStateException(String message) {
        super(message);
    }

    public InvalidStateException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidStateException(Throwable cause) {
        super(cause);
    }
}
