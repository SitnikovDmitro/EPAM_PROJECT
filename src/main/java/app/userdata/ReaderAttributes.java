package app.userdata;

import app.entity.Book;
import app.entity.Order;
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
 * Encapsulated session attributes for user with reader role
 **/
@Getter
@Setter
public class ReaderAttributes {
    private User reader;

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
    private boolean booksInBookmarksOnly;
    private boolean booksAdvancedSearchEnabled;

    private Integer ordersCount;
}
