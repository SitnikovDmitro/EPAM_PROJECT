package app.tuples;

import app.entity.User;
import lombok.Getter;
import lombok.Setter;

/**
 * Object containing validation result of user creation or edition attempt
 */
@Getter
@Setter
public class UserCreationEditionResult {
    private Boolean success = false;
    private User user = null;
    private Boolean firstnameValid = false;
    private String firstnameValidationFeedbackKey = null;
    private Boolean lastnameValid = false;
    private String lastnameValidationFeedbackKey = null;
    private Boolean emailValid = false;
    private String emailValidationFeedbackKey = null;
    private Boolean passwordValid = false;
    private String passwordValidationFeedbackKey = null;
    private Boolean captchaValid = false;
    private String captchaValidationFeedbackKey = null;
}
