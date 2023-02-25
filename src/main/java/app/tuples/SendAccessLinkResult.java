package app.tuples;

import lombok.Getter;
import lombok.Setter;

/**
 * Object containing validation result of send access link attempt
 */
@Getter
@Setter
public class SendAccessLinkResult {
    private Boolean success = false;
    private String accessCodeHash = null;
    private Boolean emailValid = false;
    private String emailValidationFeedbackKey = null;
    private Boolean passwordValid = false;
    private String passwordValidationFeedbackKey = null;
}
