package app.tuples;

import app.entity.User;
import lombok.Getter;
import lombok.Setter;

/**
 * Object containing validation result of user find attempt
 */
@Getter
@Setter
public class UserFindResult {
    private Boolean success = false;
    private User user = null;
    private String validationFeedbackKey = null;
}
