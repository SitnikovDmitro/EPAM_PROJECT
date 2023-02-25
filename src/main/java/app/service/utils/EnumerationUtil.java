package app.service.utils;

import app.enums.BookGenre;
import app.enums.BookLanguage;
import app.enums.OrderState;

/**
 * Provides utilities for receiving localization keys for enumerations
 **/
public interface EnumerationUtil {
    /**
     * Returns genre localization key
     * @param genre book genre
     * @return string localization key of given genre
     **/
    String getBookGenreLocalizationKey(BookGenre genre);

    /**
     * Returns state localization key
     * @param state order state
     * @return string localization key of given state
     **/
    String getOrderStateLocalizationKey(OrderState state);

    /**
     * Returns language localization key
     * @param language book language
     * @return string localization key of given language
     **/
    String getBookLanguageLocalizationKey(BookLanguage language);

    /**
     * Returns language localization key
     * @param language book language
     * @return string localization key of given language in short form
     **/
    String getBookLanguageShortLocalizationKey(BookLanguage language);
}
