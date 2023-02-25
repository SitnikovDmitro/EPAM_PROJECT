package app.enums;

/**
 * Sort criteria for ordering an order list
 **/
public enum OrderSortCriteria {
    CREATION_DATE_FROM_NEWER_TO_OLDER,
    CREATION_DATE_FROM_OLDER_TO_NEWER,
    DEADLINE_DATE_FROM_NEWER_TO_OLDER,
    DEADLINE_DATE_FROM_OLDER_TO_NEWER;

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
    public static OrderSortCriteria ofInt(int value) {
        if (value < 0 || value >= values().length) throw new IllegalStateException("Unexpected value: " + value);
        return values()[value];
    }
}
