package app.service.validation;

import app.exceptions.CaptchaVerificationException;
import app.exceptions.DatabaseException;
import app.tuples.BookCreationEditionResult;
import app.tuples.UserCreationEditionResult;
import app.tuples.SendAccessLinkResult;

/**
 * Validation service
 **/
public interface Validator {
    /**
     * Checks if user login credentials are invalid
     * @param email user email address
     * @param password user password
     * @return true if user with given credentials does not exist and false otherwise
     **/
    boolean areCredentialsInvalid(String email, String password);

    /**
     * Validates user firstname
     * @param firstname user firstname
     * @param result object storing validation result
     **/
    void validateUserFirstname(String firstname, UserCreationEditionResult result);

    /**
     * Validates user lastname
     * @param lastname user lastname
     * @param result object storing validation result
     **/
    void validateUserLastname(String lastname, UserCreationEditionResult result);

    /**
     * Validates user email address
     * @param email user email address
     * @param result object storing validation result
     **/
    void validateUserNewEmail(String email, UserCreationEditionResult result) throws DatabaseException;

    /**
     * Validates user email address
     * @param email user email address
     * @param result object storing validation result
     **/
    void validateUserExistingEmail(String email, SendAccessLinkResult result) throws DatabaseException;

    /**
     * Validates user email address
     * @param email user email address
     * @param emailException user email address which is excluded from duplication checking
     * @param result object storing validation result
     **/
    void validateUserNewEmail(String email, String emailException, UserCreationEditionResult result) throws DatabaseException;

    /**
     * Validates user password
     * @param password user password
     * @param result object storing validation result
     **/
    void validateUserPassword(String password, UserCreationEditionResult result);

    /**
     * Validates user password
     * @param password user password
     * @param result object storing validation result
     **/
    void validateUserPassword(String password, SendAccessLinkResult result);

    /**
     * Validates book ISBN
     * @param isbn book ISBN
     * @param result object storing validation result
     **/
    void validateBookNewIsbn(String isbn, BookCreationEditionResult result) throws DatabaseException;

    /**
     * Validates book ISBN
     * @param isbn book ISBN
     * @param isbnException book ISBN which is excluded from duplication checking
     * @param result object storing validation result
     **/
    void validateBookNewIsbn(String isbn, int isbnException, BookCreationEditionResult result) throws DatabaseException;

    /**
     * Validates book title
     * @param title book title
     * @param result object storing validation result
     **/
    void validateBookTitle(String title, BookCreationEditionResult result);

    /**
     * Validates book author
     * @param author book author
     * @param result object storing validation result
     **/
    void validateBookAuthor(String author, BookCreationEditionResult result);

    /**
     * Validates book description
     * @param description book description
     * @param result object storing validation result
     **/
    void validateBookDescription(String description, BookCreationEditionResult result);

    /**
     * Validates book publication date
     * @param publicationDate book publication date
     * @param result object storing validation result
     **/
    void validateBookPublicationDate(String publicationDate, BookCreationEditionResult result);

    /**
     * Validates book total copies number
     * @param totalCopiesNumber book total copies number
     * @param result object storing validation result
     **/
    void validateBookTotalCopiesNumber(String totalCopiesNumber, BookCreationEditionResult result);

    /**
     * Validates book total copies number
     * @param totalCopiesNumber book total copies number
     * @param copiesInUseNumber copies of books that is in use
     * @param result object storing validation result
     **/
    void validateBookTotalCopiesNumber(String totalCopiesNumber, int copiesInUseNumber, BookCreationEditionResult result);

    /**
     * Validates book genre
     * @param genre book genre
     * @param result object storing validation result
     **/
    void validateBookGenre(String genre, BookCreationEditionResult result);

    /**
     * Validates book language
     * @param language book language
     * @param result object storing validation result
     **/
    void validateBookLanguage(String language, BookCreationEditionResult result);

    /**
     * Validates publisher title
     * @param title publisher language
     * @param result object storing validation result
     **/
    void validatePublisherTitle(String title, BookCreationEditionResult result);

    /**
     * Validates google recaptcha v3
     * @param captchaResponse captcha response
     * @param result object storing validation result
     **/
    void validateCaptcha(String captchaResponse, UserCreationEditionResult result) throws CaptchaVerificationException;
}
