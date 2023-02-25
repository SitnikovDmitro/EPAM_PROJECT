package app.enums;

/**
 * Sort criteria for ordering a book list
 **/
public enum BookSortCriteria {
    POPULARITY,
    ALPHABET,
    PUBLICATION;

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
    public static BookSortCriteria ofInt(int value) {
        if (value < 0 || value >= values().length) throw new IllegalStateException("Unexpected value: " + value);
        return values()[value];
    }
}
