package app.entity;

import app.enums.BookGenre;
import app.enums.BookLanguage;
import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {
    private int id;
    private Publisher publisher;
    private int isbn;
    private int totalCopiesNumber;
    private int freeCopiesNumber;
    private int gradesSum;
    private int gradesNumber;
    private boolean hasElectronicFormat;
    private boolean isValuable;
    private boolean isDeleted;
    private String title;
    private String author;
    private String description;
    private BookGenre genre;
    private BookLanguage language;
    private LocalDate publicationDate;
}
