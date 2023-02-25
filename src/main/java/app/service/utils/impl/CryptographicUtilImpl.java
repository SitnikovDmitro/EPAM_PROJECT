package app.service.utils.impl;

import app.service.utils.CryptographicUtil;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;

public class CryptographicUtilImpl implements CryptographicUtil {
    @Override
    public String generateHash(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            String result = Base64.getEncoder().encodeToString(encodedHash);
            if (result.length() != 44) throw new AssertionError("Base 64 encoded SHA-256 hash length must be 44");
            return result;
        } catch (NoSuchAlgorithmException exception) {
            throw new Error("SHA-256 hash algorithm is not found", exception);
        }
    }

    @Override
    public String generateRandomCode() {
        Random random = new Random();
        char[] code = new char[20];
        for (int i = 0; i < code.length; i++) {
            code[i] = (char)('a'+Math.abs(random.nextInt(26)));
        }
        return String.valueOf(code);
    }
}
