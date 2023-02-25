package app.service.mapper;

import app.dto.BookBriefDTO;
import app.dto.BookDetailedDTO;
import app.entity.Book;
import app.entity.Feedback;

import java.sql.SQLException;
import java.util.List;

/**
 * Mapper converting book entity to dto
 **/
public interface BookMapper {
    /**
     * Converts book entity to dto
     * @param book book entity
     * @param language language
     * @return book dto
     **/
    BookBriefDTO toBriefDTO(Book book, String language);

    /**
     * Converts book entity to dto
     * @param book book entity
     * @param feedbacks list of feedbacks on this book
     * @param isBookInUserBookmarks specifies if this book is in bookmarks of user
     * @param isBookInUserOrders specifies if this book is in orders of user
     * @param language language
     * @return book dto
     **/
    BookDetailedDTO toDetailedDTO(Book book, List<Feedback> feedbacks, boolean isBookInUserBookmarks, boolean isBookInUserOrders, String language);
}
