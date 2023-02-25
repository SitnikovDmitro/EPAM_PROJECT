package app.dto;

import app.enums.OrderState;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO of order entity
 * @see app.entity.Order
 **/
@Getter
@Setter
public class OrderDTO {
    private Integer code;
    private Integer userId;
    private String userFullname;
    private Integer bookId;
    private String bookTitle;
    private Boolean bookIsValuable;
    private Boolean bookHasFreeCopies;
    private OrderState state;
    private LocalDate creationDate;
    private LocalDate deadlineDate;

    private String stateText;
}
