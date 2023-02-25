package app.userdata;

import app.entity.Book;
import app.entity.User;
import app.enums.BookFormat;
import app.enums.BookGenre;
import app.enums.BookLanguage;
import app.enums.BookSortCriteria;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Encapsulated session attributes for guest user
 **/
@Getter
@Setter
public class GuestAttributes {
    private Integer booksCount;
    private String booksQuery;
    private String booksPublisherQuery;
    private String booksTitleQuery;
    private String booksAuthorQuery;
    private Integer booksIsbn;
    private BookGenre booksGenre;
    private BookLanguage booksLanguage;
    private BookFormat booksFormat;
    private BookSortCriteria booksSortCriteria;
    private boolean booksAdvancedSearchEnabled;

    private String accessCodeHash;
    private String accessEmail;
    private String accessPasswordHash;

    private String registerFirstname;
    private String registerLastname;
    private String registerEmail;
    private String registerPasswordHash;
}
