package app.service.other;

/**
 * Localization service
 **/
public interface Localizator {
    /**
     * Provides localization
     * @param language language
     * @param key word or phrase key
     * @return translated word or phrase by key and language
     **/
    String localize(String language, String key);
}
