package app.tuples;

import app.entity.Book;
import lombok.Getter;
import lombok.Setter;

/**
 * Object containing validation result of book creation or edition attempt
 */
@Getter
@Setter
public class BookCreationEditionResult {
    private Boolean success = false;
    private Book book = null;
    private Boolean isbnValid = false;
    private String isbnValidationFeedbackKey = null;
    private Boolean titleValid = false;
    private String titleValidationFeedbackKey = null;
    private Boolean authorValid = false;
    private String authorValidationFeedbackKey = null;
    private Boolean descriptionValid = false;
    private String descriptionValidationFeedbackKey = null;
    private Boolean publicationDateValid = false;
    private String publicationDateValidationFeedbackKey = null;
    private Boolean genreValid = false;
    private String genreValidationFeedbackKey = null;
    private Boolean languageValid = false;
    private String languageValidationFeedbackKey = null;
    private Boolean totalCopiesNumberValid = false;
    private String totalCopiesNumberValidationFeedbackKey = null;
    private Boolean publisherTitleValid = false;
    private String publisherTitleValidationFeedbackKey = null;
}
