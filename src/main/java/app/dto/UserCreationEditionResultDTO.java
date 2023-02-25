package app.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO of user creation or edition result tuple
 * @see app.tuples.UserCreationEditionResult
 **/
@Getter
@Setter
public class UserCreationEditionResultDTO {
    private Boolean success = false;
    private Boolean firstnameValid = false;
    private String firstnameValidationFeedback = null;
    private Boolean lastnameValid = false;
    private String lastnameValidationFeedback = null;
    private Boolean emailValid = false;
    private String emailValidationFeedback = null;
    private Boolean passwordValid = false;
    private String passwordValidationFeedback = null;
    private Boolean captchaValid = false;
    private String captchaValidationFeedback = null;
}
