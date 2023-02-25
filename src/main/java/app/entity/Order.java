package app.entity;

import app.enums.OrderState;


import lombok.*;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    private int code;
    private User user;
    private Book book;
    private OrderState state;
    private LocalDate creationDate;
    private LocalDate deadlineDate;
}
