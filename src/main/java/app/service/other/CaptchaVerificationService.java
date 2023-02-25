package app.service.other;

import app.exceptions.CaptchaVerificationException;

/**
 * Service for verifying captcha
 **/
public interface CaptchaVerificationService {
    /**
     * Verifies captcha
     * @param captchaResponse response of captcha
     * @return true if captcha is valid and false otherwise
     **/
    boolean isCaptchaValid(String captchaResponse) throws CaptchaVerificationException;
}
