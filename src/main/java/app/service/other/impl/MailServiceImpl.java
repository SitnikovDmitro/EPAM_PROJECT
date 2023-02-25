package app.service.other.impl;

import app.exceptions.InitializationError;
import app.exceptions.MailException;
import app.service.other.CustomTemplate;
import app.service.other.Localizator;
import app.service.other.MailService;
import app.service.utils.FormatUtil;
import app.utils.ResourceUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class MailServiceImpl implements MailService {
    private static final Logger logger = LogManager.getLogger(MailServiceImpl.class);

    private final Localizator localizator;
    private final FormatUtil formatUtil;

    private final CustomTemplate accessMessageTemplate;
    private final CustomTemplate informationMessageTemplate;

    private final String fromAddress;
    private final String fromAddressPassword;

    public MailServiceImpl(FormatUtil formatUtil, Localizator localizator)  {
        this.formatUtil = formatUtil;
        this.localizator = localizator;

        try {
            Map<String, String> properties = ResourceUtil.getResourceAsMap("mail-properties.json");
            fromAddress = properties.get("address");
            fromAddressPassword = properties.get("password");

            accessMessageTemplate = new CustomTemplateImpl(ResourceUtil.getResourceAsString("templates/access-message-content.html"));
            informationMessageTemplate = new CustomTemplateImpl(ResourceUtil.getResourceAsString("templates/information-message-content.html"));

            logger.info("Mail service impl initialization finished successfully");
        } catch (Exception exception) {
            logger.fatal("Mail service impl initialization failed", exception);
            throw new InitializationError("Mail service impl initialization failed", exception);
        }
    }



    private void sendMessage(String address, String subject, String content) throws MessagingException {
        logger.info("Sending email message with subject "+subject+" to address "+address+" started");

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromAddress, fromAddressPassword);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(fromAddress));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(address));
        message.setSubject(subject);

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(content, "text/html; charset=utf-8");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        message.setContent(multipart);

        Transport.send(message);

        logger.info("Sending email message with subject "+subject+" to address "+address+" finished successfully");
    }

    @Override
    public void sendAccessLink(String emailAddress, String url, String accessCode, String language) throws MailException {


        Map<String, String> parameters = new HashMap<>();
        parameters.put("link", url+"/access/get?accessCode="+accessCode);
        parameters.put("descriptionText", localizator.localize(language,"accessLinkMessage"));
        parameters.put("buttonText", localizator.localize(language,"getAccess"));

        String subject = localizator.localize(language, "accessMessage");
        String content = accessMessageTemplate.generate(parameters);

        try {
            sendMessage(emailAddress, subject, content);
        } catch (MessagingException exception) {
            throw new MailException(exception);
        }
    }

    @Override
    public void sendFinePaymentMessage(String emailAddress, int fine, String language) throws MailException {
        String result = localizator.localize(language, "finePaymentSuccessfullyFinished")+" "+formatUtil.formatPrice(fine) + localizator.localize(language, "grn");

        Map<String, String> parameters = new HashMap<>();
        parameters.put("header", localizator.localize(language,"info"));
        parameters.put("paragraph", result);

        String subject = localizator.localize(language, "finePaymentMessage");
        String content = informationMessageTemplate.generate(parameters);

        try {
            sendMessage(emailAddress, subject, content);
        } catch (MessagingException exception) {
            throw new MailException(exception);
        }
    }

    @Override
    public void sendFinePaymentMessageAsync(String emailAddress, int fine, String language) {
        Thread thread = new Thread(() -> sendFinePaymentMessage(emailAddress, fine, language));
        thread.start();
    }


    @Override
    public void sendAccessLinkAsync(String emailAddress, String url, String accessCode, String language) {
        Thread thread = new Thread(() -> sendAccessLink(emailAddress, url, accessCode, language));
        thread.start();
    }
}
