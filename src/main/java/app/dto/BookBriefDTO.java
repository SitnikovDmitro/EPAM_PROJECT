package app.dto;

import app.enums.BookGenre;
import app.enums.BookLanguage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * DTO of book entity
 * @see app.entity.Book
 **/
@Getter
@Setter
public class BookBriefDTO {
    private Integer id;
    private String title;
    private Integer averageGrade;
    private Integer freeCopiesNumber;
    private Integer totalCopiesNumber;
    private String propertiesText;
}
