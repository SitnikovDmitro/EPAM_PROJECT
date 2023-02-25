package app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

/**
 * DTO of book entity
 * @see app.entity.Book
 **/
@Getter
@Setter
public class BookDetailedDTO {
    private Integer id;
    private Integer isbn;
    private Integer genre;
    private Integer language;
    private Integer totalCopiesNumber;
    private Integer freeCopiesNumber;
    private Boolean hasElectronicFormat;
    private Boolean isValuable;
    private Boolean isInUserBookmarks;
    private Boolean isInUserOrders;
    private String title;
    private String author;
    private String description;
    private String averageGrade;
    private String publisherTitle;
    private String publicationDate;

    private String genreText;
    private String languageText;
    private String formatText;

    private List<FeedbackDTO> feedbacks;


}
