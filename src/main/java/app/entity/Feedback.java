package app.entity;


import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Feedback {
    private User user;
    private Book book;
    private int grade;
    private String text;
    private LocalDate creationDate;
}
