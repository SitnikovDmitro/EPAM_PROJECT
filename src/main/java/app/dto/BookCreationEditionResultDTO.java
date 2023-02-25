package app.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO of book creation or edition result tuple
 * @see app.tuples.BookCreationEditionResult
 **/
@Getter
@Setter
public class BookCreationEditionResultDTO {
    private Boolean success = false;
    private Boolean isbnValid = false;
    private String isbnValidationFeedback = null;
    private Boolean titleValid = false;
    private String titleValidationFeedback = null;
    private Boolean authorValid = false;
    private String authorValidationFeedback = null;
    private Boolean descriptionValid = false;
    private String descriptionValidationFeedback = null;
    private Boolean publicationDateValid = false;
    private String publicationDateValidationFeedback = null;
    private Boolean genreValid = false;
    private String genreValidationFeedback = null;
    private Boolean languageValid = false;
    private String languageValidationFeedback = null;
    private Boolean totalCopiesNumberValid = false;
    private String totalCopiesNumberValidationFeedback = null;
    private Boolean publisherTitleValid = false;
    private String publisherTitleValidationFeedback = null;
}
