package app.service.other.impl;

import app.exceptions.CaptchaVerificationException;
import app.exceptions.InitializationError;
import app.service.other.CaptchaVerificationService;
import app.utils.ResourceUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class CaptchaVerificationServiceImpl implements CaptchaVerificationService {
    private static final Logger logger = LogManager.getLogger(CaptchaVerificationServiceImpl.class);

    private final String secretKey;

    public CaptchaVerificationServiceImpl() {
        try {
            Map<String, String> properties = ResourceUtil.getResourceAsMap("captcha-properties.json");
            secretKey = properties.get("secretKey");

            logger.info("Captcha verification service impl initialization finished successfully");
        } catch (Exception exception) {
            logger.fatal("Captcha verification service impl initialization failed", exception);
            throw new InitializationError("Captcha verification service impl initialization failed", exception);
        }
    }


    @Override
    public boolean isCaptchaValid(String captchaResponse) throws CaptchaVerificationException {
        if (captchaResponse == null || captchaResponse.isBlank()) {
            return false;
        }

        try {
            String url = "https://www.google.com/recaptcha/api/siteverify";
            String params = "secret=" + secretKey + "&response=" + captchaResponse;

            HttpURLConnection http = (HttpURLConnection) new URL(url).openConnection();
            http.setDoOutput(true);
            http.setRequestMethod("POST");
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

            try (OutputStream out = http.getOutputStream()) {
                out.write(params.getBytes(StandardCharsets.UTF_8));
                out.flush();
            }

            try (InputStream res = http.getInputStream()) {
                JsonNode node = new ObjectMapper().readTree(res);
                return node.get("success").asBoolean();
            }
        } catch (IOException exception) {
            throw new CaptchaVerificationException(exception);
        }
    }
}
