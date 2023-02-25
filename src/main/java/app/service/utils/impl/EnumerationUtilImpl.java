package app.service.utils.impl;

import app.enums.BookGenre;
import app.enums.BookLanguage;
import app.enums.OrderState;
import app.service.utils.EnumerationUtil;

public class EnumerationUtilImpl implements EnumerationUtil {
    @Override
    public String getBookGenreLocalizationKey(BookGenre genre) {
        switch (genre) {
            case ADVENTURES: return "adventures";
            case HORROR: return "horror";
            case COMEDY: return "comedy";
            case ROMANCE: return "romance";
            case THRILLER: return "thriller";
            case DRAMA: return "drama";
            case ENCYCLOPEDIA: return "encyclopedia";
            default: throw new IllegalStateException("Unexpected value: " + genre);
        }
    }

    @Override
    public String getOrderStateLocalizationKey(OrderState state) {
        switch (state) {
            case WAITING_FOR_CONFIRMATION: return "waitingForConfirmation";
            case CONFIRMED: return "confirmed";
            case CLOSED: return "closed";
            case CANCELLED: return "cancelled";
            case DELETED: return "deleted";
            default: throw new IllegalStateException("Unexpected value: " + state);
        }
    }

    @Override
    public String getBookLanguageLocalizationKey(BookLanguage language) {
        switch (language) {
            case ENGLISH: return "english";
            case RUSSIAN: return "russian";
            case JAPANESE: return "japanese";
            case UKRAINIAN: return "ukrainian";
            default: throw new IllegalStateException("Unexpected value: " + language);
        }
    }

    @Override
    public String getBookLanguageShortLocalizationKey(BookLanguage language) {
        switch (language) {
            case ENGLISH: return "eng";
            case RUSSIAN: return "rus";
            case JAPANESE: return "jpn";
            case UKRAINIAN: return "ukr";
            default: throw new IllegalStateException("Unexpected value: " + language);
        }
    }
}
