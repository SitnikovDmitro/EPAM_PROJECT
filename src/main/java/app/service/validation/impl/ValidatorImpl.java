package app.service.validation.impl;

import app.database.dao.BookDAO;
import app.database.dao.UserDAO;

import app.enums.BookGenre;
import app.enums.BookLanguage;
import app.exceptions.CaptchaVerificationException;
import app.exceptions.DatabaseException;
import app.service.other.CaptchaVerificationService;
import app.service.validation.Validator;
import app.tuples.BookCreationEditionResult;
import app.tuples.UserCreationEditionResult;
import app.tuples.SendAccessLinkResult;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class ValidatorImpl implements Validator {
    private static final String NAME_REGEX = "[А-Яа-яA-Za-zіїёІЇЁ]+";
    private static final String EMAIL_REGEX = "[A-Za-z0-9.]+@([A-Za-z0-9-]+\\.)+[A-Za-z0-9-]{2,4}";
    private static final String TEXT_REGEX = "[ 0-9А-Яа-яA-Za-zіїёІЇЁ.’,?!-]+";

    private final BookDAO bookDAO;
    private final UserDAO userDAO;
    private final CaptchaVerificationService captchaVerificationService;

    public ValidatorImpl(BookDAO bookDAO, UserDAO userDAO, CaptchaVerificationService captchaVerificationService) {
        this.bookDAO = bookDAO;
        this.userDAO = userDAO;
        this.captchaVerificationService = captchaVerificationService;
    }

    private boolean isEmpty(String string) {
        return Objects.isNull(string) || string.isBlank();
    }

    private boolean isNameInvalid(String string) {
        return !string.matches(NAME_REGEX);
    }

    private boolean isTextInvalid(String string) {
        return !string.matches(TEXT_REGEX);
    }

    private boolean isEmailInvalid(String string) {
        return !string.matches(EMAIL_REGEX);
    }

    private Integer tryParseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException | NullPointerException ignored) {
            return null;
        }
    }

    private LocalDate tryParseLocalDate(String value) {
        try {
            return LocalDate.parse(value);
        } catch (DateTimeParseException | NullPointerException ignored) {
            return null;
        }
    }

    @Override
    public boolean areCredentialsInvalid(String email, String password) {
        return email == null || email.isBlank() || email.length() > 50 || password == null || password.isBlank() || password.length() > 50;
    }

    @Override
    public void validateUserFirstname(String firstname, UserCreationEditionResult result) {
        if (isEmpty(firstname)) {
            result.setFirstnameValidationFeedbackKey("firstnameIsEmpty");
        } else if (firstname.length() > 50) {
            result.setFirstnameValidationFeedbackKey("firstnameIsTooLong");
        } else if (isNameInvalid(firstname)) {
            result.setFirstnameValidationFeedbackKey("firstnameContainsInvalidCharacters");
        } else {
            result.setFirstnameValid(true);
        }
    }

    @Override
    public void validateUserLastname(String lastname, UserCreationEditionResult result) {
        if (isEmpty(lastname)) {
            result.setLastnameValidationFeedbackKey("lastnameIsEmpty");
        } else if (lastname.length() > 50) {
            result.setLastnameValidationFeedbackKey("lastnameIsTooLong");
        } else if (isNameInvalid(lastname)) {
            result.setLastnameValidationFeedbackKey("lastnameContainsInvalidCharacters");
        } else {
            result.setLastnameValid(true);
        }
    }

    @Override
    public void validateUserNewEmail(String email, UserCreationEditionResult result) throws DatabaseException {
        if (isEmpty(email)) {
            result.setEmailValidationFeedbackKey("emailIsEmpty");
        } else if (email.length() > 50) {
            result.setEmailValidationFeedbackKey("emailIsTooLong");
        } else if (isEmailInvalid(email)) {
            result.setEmailValidationFeedbackKey("emailDoesNotMatchPattern");
        } else if (userDAO.existsUserByEmail(email)) {
            result.setEmailValidationFeedbackKey("emailIsDuplicated");
        } else {
            result.setEmailValid(true);
        }
    }

    @Override
    public void validateUserExistingEmail(String email, SendAccessLinkResult result) throws DatabaseException {
        if (isEmpty(email)) {
            result.setEmailValidationFeedbackKey("emailIsEmpty");
        } else if (email.length() > 50) {
            result.setEmailValidationFeedbackKey("emailIsTooLong");
        } else if (isEmailInvalid(email)) {
            result.setEmailValidationFeedbackKey("emailDoesNotMatchPattern");
        } else if (!userDAO.existsUserByEmail(email)) {
            result.setEmailValidationFeedbackKey("emailIsNotExist");
        } else {
            result.setEmailValid(true);
        }
    }

    @Override
    public void validateUserNewEmail(String email, String emailException, UserCreationEditionResult result) throws DatabaseException {
        if (isEmpty(email)) {
            result.setEmailValidationFeedbackKey("emailIsEmpty");
        } else if (email.length() > 50) {
            result.setEmailValidationFeedbackKey("emailIsTooLong");
        } else if (isEmailInvalid(email)) {
            result.setEmailValidationFeedbackKey("emailDoesNotMatchPattern");
        } else if (!email.equals(emailException) && userDAO.existsUserByEmail(email)) {
            result.setEmailValidationFeedbackKey("emailIsDuplicated");
        } else {
            result.setEmailValid(true);
        }
    }

    @Override
    public void validateUserPassword(String password, UserCreationEditionResult result) {
        if (isEmpty(password)) {
            result.setPasswordValidationFeedbackKey("passwordIsEmpty");
        } else if (password.length() > 50) {
            result.setPasswordValidationFeedbackKey("passwordIsTooLong");
        } else if (password.length() < 8) {
            result.setPasswordValidationFeedbackKey("passwordIsTooShort");
        } else {
            result.setPasswordValid(true);
        }
    }

    @Override
    public void validateUserPassword(String password, SendAccessLinkResult result) {
        if (isEmpty(password)) {
            result.setPasswordValidationFeedbackKey("passwordIsEmpty");
        } else if (password.length() > 50) {
            result.setPasswordValidationFeedbackKey("passwordIsTooLong");
        } else if (password.length() < 8) {
            result.setPasswordValidationFeedbackKey("passwordIsTooShort");
        } else {
            result.setPasswordValid(true);
        }
    }

    @Override
    public void validateBookNewIsbn(String isbn, BookCreationEditionResult result) throws DatabaseException {
        Integer parsedIsbn = tryParseInt(isbn);

        if (isEmpty(isbn)) {
            result.setIsbnValidationFeedbackKey("isbnIsEmpty");
        } else if (Objects.isNull(parsedIsbn)) {
            result.setIsbnValidationFeedbackKey("isbnIsInvalid");
        } else if (parsedIsbn < 0) {
            result.setIsbnValidationFeedbackKey("isbnIsNegative");
        } else if (bookDAO.existsBookByIsbn(parsedIsbn)) {
            result.setIsbnValidationFeedbackKey("isbnIsDuplicated");
        } else {
            result.setIsbnValid(true);
        }
    }

    @Override
    public void validateBookNewIsbn(String isbn, int isbnException, BookCreationEditionResult result) throws DatabaseException {
        Integer parsedIsbn = tryParseInt(isbn);

        if (isEmpty(isbn)) {
            result.setIsbnValidationFeedbackKey("isbnIsEmpty");
        } else if (Objects.isNull(parsedIsbn)) {
            result.setIsbnValidationFeedbackKey("isbnIsInvalid");
        } else if (parsedIsbn < 0) {
            result.setIsbnValidationFeedbackKey("isbnIsNegative");
        } else if (!parsedIsbn.equals(isbnException) && bookDAO.existsBookByIsbn(parsedIsbn)) {
            result.setIsbnValidationFeedbackKey("isbnIsDuplicated");
        }  else {
            result.setIsbnValid(true);
        }
    }

    @Override
    public void validateBookTitle(String title, BookCreationEditionResult result) {
        if (isEmpty(title)) {
            result.setTitleValidationFeedbackKey("titleIsEmpty");
        } else if (title.length() > 50) {
            result.setTitleValidationFeedbackKey("titleIsTooLong");
        } else if (isTextInvalid(title)) {
            result.setTitleValidationFeedbackKey("titleContainsInvalidCharacters");
        } else {
            result.setTitleValid(true);
        }
    }

    @Override
    public void validateBookAuthor(String author, BookCreationEditionResult result) {
        if (isEmpty(author)) {
            result.setAuthorValidationFeedbackKey("authorIsEmpty");
        } else if (author.length() > 50) {
            result.setAuthorValidationFeedbackKey("authorIsTooLong");
        } else if (isTextInvalid(author)) {
            result.setAuthorValidationFeedbackKey("authorContainsInvalidCharacters");
        } else {
            result.setAuthorValid(true);
        }
    }

    @Override
    public void validateBookDescription(String description, BookCreationEditionResult result) {
        if (isEmpty(description)) {
            result.setDescriptionValidationFeedbackKey("descriptionIsEmpty");
        } else if (description.length() > 500) {
            result.setDescriptionValidationFeedbackKey("descriptionIsTooLong");
        } else if (isTextInvalid(description)) {
            result.setDescriptionValidationFeedbackKey("descriptionContainsInvalidCharacters");
        } else {
            result.setDescriptionValid(true);
        }
    }

    @Override
    public void validateBookPublicationDate(String publicationDate, BookCreationEditionResult result) {
        LocalDate parsedPublicationDate = tryParseLocalDate(publicationDate);

        if (isEmpty(publicationDate)) {
            result.setPublicationDateValidationFeedbackKey("publicationDateIsNotSelected");
        } else if (Objects.isNull(parsedPublicationDate)) {
            result.setPublicationDateValidationFeedbackKey("publicationDateIsInvalid");
        } else if (parsedPublicationDate.isAfter(LocalDate.now())) {
            result.setPublicationDateValidationFeedbackKey("publicationDateIsFuture");
        } else {
            result.setPublicationDateValid(true);
        }
    }

    @Override
    public void validateBookTotalCopiesNumber(String totalCopiesNumber, BookCreationEditionResult result) {
        Integer parsedTotalCopiesNumber= tryParseInt(totalCopiesNumber);

        if (isEmpty(totalCopiesNumber)) {
            result.setTotalCopiesNumberValidationFeedbackKey("copiesNumberIsEmpty");
        } else if (Objects.isNull(parsedTotalCopiesNumber)) {
            result.setTotalCopiesNumberValidationFeedbackKey("copiesNumberIsInvalid");
        } else if (parsedTotalCopiesNumber < 0) {
            result.setTotalCopiesNumberValidationFeedbackKey("copiesNumberIsNegative");
        } else {
            result.setTotalCopiesNumberValid(true);
        }
    }

    @Override
    public void validateBookTotalCopiesNumber(String totalCopiesNumber, int copiesInUseNumber, BookCreationEditionResult result) {
        Integer parsedTotalCopiesNumber= tryParseInt(totalCopiesNumber);

        if (isEmpty(totalCopiesNumber)) {
            result.setTotalCopiesNumberValidationFeedbackKey("copiesNumberIsEmpty");
        } else if (Objects.isNull(parsedTotalCopiesNumber)) {
            result.setTotalCopiesNumberValidationFeedbackKey("copiesNumberIsInvalid");
        } else if (parsedTotalCopiesNumber < 0) {
            result.setTotalCopiesNumberValidationFeedbackKey("copiesNumberIsNegative");
        } else if (parsedTotalCopiesNumber < copiesInUseNumber) {
            result.setTotalCopiesNumberValidationFeedbackKey("totalCopiesNumberIsLessThanCopiesNumberInUse");
        } else {
            result.setTotalCopiesNumberValid(true);
        }
    }

    @Override
    public void validateBookGenre(String genre, BookCreationEditionResult result) {
        Integer parsedGenre = tryParseInt(genre);

        if (isEmpty(genre)) {
            result.setGenreValidationFeedbackKey("genreIsNotSelected");
        } else if (BookGenre.ofIntOptional(parsedGenre).isEmpty()) {
            result.setGenreValidationFeedbackKey("genreIsInvalid");
        } else {
            result.setGenreValid(true);
        }
    }

    @Override
    public void validateBookLanguage(String language, BookCreationEditionResult result) {
        Integer parsedLanguage = tryParseInt(language);

        if (isEmpty(language)) {
            result.setLanguageValidationFeedbackKey("languageIsNotSelected");
        } else if (BookLanguage.ofIntOptional(parsedLanguage).isEmpty()) {
            result.setLanguageValidationFeedbackKey("languageIsInvalid");
        } else {
            result.setLanguageValid(true);
        }
    }

    @Override
    public void validatePublisherTitle(String title, BookCreationEditionResult result) {
        if (isEmpty(title)) {
            result.setPublisherTitleValidationFeedbackKey("publisherIsEmpty");
        } else if (title.length() > 50) {
            result.setPublisherTitleValidationFeedbackKey("publisherIsTooLong");
        } else if (isTextInvalid(title)) {
            result.setPublisherTitleValidationFeedbackKey("publisherContainsInvalidCharacters");
        } else {
            result.setPublisherTitleValid(true);
        }
    }

    @Override
    public void validateCaptcha(String captchaResponse, UserCreationEditionResult result) throws CaptchaVerificationException {
        if (!captchaVerificationService.isCaptchaValid(captchaResponse)) {
            result.setCaptchaValidationFeedbackKey("captchaIsNotPassed");
        } else {
            result.setCaptchaValid(true);
        }
    }
}
