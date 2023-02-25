package app.enums;

/**
 * State of order
 **/
public enum OrderState {
    WAITING_FOR_CONFIRMATION,
    CONFIRMED,
    CLOSED,
    CANCELLED,
    DELETED;

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
    public static OrderState ofInt(int value) {
        if (value < 0 || value >= values().length) throw new IllegalStateException("Unexpected value: " + value);
        return values()[value];
    }
}
