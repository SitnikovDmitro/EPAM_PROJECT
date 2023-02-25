package app.exceptions;

/**
 * Throws when captcha verification fails
 **/
public class CaptchaVerificationException extends Exception {
    public CaptchaVerificationException() {}

    public CaptchaVerificationException(String message) {
        super(message);
    }

    public CaptchaVerificationException(Throwable cause) {
        super(cause);
    }

    public CaptchaVerificationException(String message, Throwable cause) {
        super(message, cause);
    }
}
