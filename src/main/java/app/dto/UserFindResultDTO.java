package app.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO of attempt operation to find user by email and password
 * @see app.tuples.UserFindResult
 **/
@Getter
@Setter
public class UserFindResultDTO {
    private Boolean success = false;
    private String validationFeedback = null;
    private Integer role = null;
}
