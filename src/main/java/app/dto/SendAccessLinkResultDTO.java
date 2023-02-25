package app.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO of user attempt operation to send access link
 * @see app.tuples.SendAccessLinkResult
 **/
@Getter
@Setter
public class SendAccessLinkResultDTO {
    private Boolean success = false;

    private Boolean emailValid = false;
    private String emailValidationFeedback = null;
    private Boolean passwordValid = false;
    private String passwordValidationFeedback = null;
}
