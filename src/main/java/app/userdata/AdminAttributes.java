package app.userdata;

import app.entity.Book;
import app.entity.User;
import app.enums.BookFormat;
import app.enums.BookGenre;
import app.enums.BookLanguage;
import app.enums.BookSortCriteria;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Encapsulated session attributes for user with admin role
 **/
@Getter
@Setter
public class AdminAttributes {
    private User admin;

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

    private Integer readersCount;
    private String readersQuery;
    private boolean readersBlockedOnly;
    private boolean readersWithFineOnly;

    private Integer librariansCount;
    private String librariansQuery;
}
