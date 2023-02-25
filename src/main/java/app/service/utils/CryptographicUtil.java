package app.service.utils;

/**
 * Provides cryptographic utilities
 **/
public interface CryptographicUtil {
    /**
     * Generates SHA-256  hash
     * @param password password
     * @return SHA-256 hash encoded in string with 44 length by base 64 encoder
     **/
    String generateHash(String password);


    /**
     * Generates random code
     * @return random code of 20 random latin small letters
     **/
    String generateRandomCode();
}
