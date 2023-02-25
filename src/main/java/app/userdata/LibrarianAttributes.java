package app.userdata;

import app.entity.Book;
import app.entity.Order;
import app.entity.User;
import app.enums.OrderSortCriteria;
import app.enums.OrderState;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Encapsulated session attributes for user with librarian role
 **/
@Getter
@Setter
public class LibrarianAttributes {
    private User librarian;

    private Integer ordersCount;
    private Integer ordersCode;
    private Integer ordersReaderId;
    private Integer ordersBookId;
    private String ordersReaderFullname;
    private String ordersBookTitle;
    private OrderState ordersState;
    private OrderSortCriteria ordersSortCriteria;

    private Integer readersCount;
    private String readersQuery;

    private Integer booksCount;
    private String booksQuery;
}
