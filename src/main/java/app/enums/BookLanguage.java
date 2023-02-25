package app.enums;

import java.util.Objects;
import java.util.Optional;

/**
 * Language of a book
 **/
public enum BookLanguage {
    ENGLISH,
    RUSSIAN,
    JAPANESE,
    UKRAINIAN;

    /**
     * Encode enumeration into integer value to store in database
     * @return encoded integer value
     **/
    public int toInt() {
        return this.ordinal();
    }

    /**
     * Decode enumeration from integer value, extracted from database or http request
     * @param value integer value
     * @return decoded enumeration
     **/
    public static BookLanguage ofInt(int value) {
        if (value < 0 || value >= values().length) throw new IllegalStateException("Unexpected value: " + value);
        return values()[value];
    }

    /**
     * Decode enumeration from integer value, extracted from database or http request wrapped in optional
     * @param value integer value
     * @return decoded enumeration in optional wrapper
     **/
    public static Optional<BookLanguage> ofIntOptional(Integer value) {
        if (Objects.isNull(value)) return Optional.empty();
        if (value < 0 || value >= values().length) return Optional.empty();
        return Optional.of(ofInt(value));
    }
}
