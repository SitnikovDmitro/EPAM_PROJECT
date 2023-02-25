package app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * DTO of feedback entity
 * @see app.entity.Feedback
 **/
@Getter
@Setter
public class FeedbackDTO {
    private Integer grade;
    private String text;
    private String userFullname;
    private String creationDate;
}
