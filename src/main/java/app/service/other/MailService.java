package app.service.other;

import app.exceptions.MailException;

/**
 * Mail service
 **/
public interface MailService {
    /**
     * Sends email message containing access link with given access code
     * @param emailAddress email address to send message
     * @param url url address using in forming access link
     * @param accessCode secret access code using in forming access link
     * @param language language to localize link description
     */
    void sendAccessLink(String emailAddress, String url, String accessCode, String language) throws MailException;

    /**
     * Sends email message in asynchronous thread containing access link with given access code
     * @param emailAddress email address to send message
     * @param url url address using in forming access link
     * @param accessCode secret access code using in forming access link
     * @param language language to localize link description
     */
    void sendAccessLinkAsync(String emailAddress, String url, String accessCode, String language);

    /**
     * Sends email message containing fine payment information
     * @param emailAddress email address to send message
     * @param fine fine of user
     * @param language language to localize message
     */
    void sendFinePaymentMessage(String emailAddress, int fine, String language) throws MailException;

    /**
     * Sends email message in asynchronous thread containing fine payment information
     * @param emailAddress email address to send message
     * @param fine fine of user
     * @param language language to localize message
     */
    void sendFinePaymentMessageAsync(String emailAddress, int fine, String language);
}
