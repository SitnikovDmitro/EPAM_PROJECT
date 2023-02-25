package app.service.mapper.impl;




import app.dto.BookBriefDTO;
import app.dto.BookDetailedDTO;
import app.dto.FeedbackDTO;
import app.entity.Book;
import app.entity.Feedback;
import app.service.mapper.BookMapper;
import app.service.other.Localizator;
import app.service.utils.EnumerationUtil;
import app.service.utils.FormatUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;


public class BookMapperImpl implements BookMapper {
    private final Localizator localizator;
    private final FormatUtil formatUtil;
    private final EnumerationUtil enumerationUtil;

    public BookMapperImpl(Localizator localizator, FormatUtil formatUtil, EnumerationUtil enumerationUtil) {
        this.localizator = localizator;
        this.formatUtil = formatUtil;
        this.enumerationUtil = enumerationUtil;
    }

    @Override
    public BookBriefDTO toBriefDTO(Book book, String language) {
        BookBriefDTO dto = new BookBriefDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setFreeCopiesNumber(book.getFreeCopiesNumber());
        dto.setTotalCopiesNumber(book.getTotalCopiesNumber());
        dto.setAverageGrade(formatUtil.roundAverageGrade(book.getGradesSum(), book.getGradesNumber()));
        dto.setPropertiesText(generateProperties(book, language));
        return dto;
    }

    @Override
    public BookDetailedDTO toDetailedDTO(Book book, List<Feedback> feedbacks, boolean isBookInUserBookmarks, boolean isBookInUserOrders, String language) {
        List<FeedbackDTO> dtos = new ArrayList<>();
        for (Feedback feedback : feedbacks) {
            FeedbackDTO dto = new FeedbackDTO();
            dto.setGrade(feedback.getGrade());
            dto.setText(feedback.getText());
            dto.setUserFullname(feedback.getUser().getFirstname()+" "+feedback.getUser().getLastname());
            dto.setCreationDate(feedback.getCreationDate().toString());
            dtos.add(dto);
        }

        BookDetailedDTO dto = new BookDetailedDTO();
        dto.setId(book.getId());
        dto.setIsbn(book.getIsbn());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setDescription(book.getDescription());
        dto.setGenre(book.getGenre().toInt());
        dto.setLanguage(book.getLanguage().toInt());
        dto.setAverageGrade(formatUtil.formatAverageGrade(book.getGradesSum(), book.getGradesNumber()));
        dto.setPublicationDate(book.getPublicationDate().toString());
        dto.setTotalCopiesNumber(book.getTotalCopiesNumber());
        dto.setFreeCopiesNumber(book.getFreeCopiesNumber());
        dto.setHasElectronicFormat(book.isHasElectronicFormat());
        dto.setIsInUserBookmarks(isBookInUserBookmarks);
        dto.setIsInUserOrders(isBookInUserOrders);
        dto.setIsValuable(book.isValuable());
        dto.setPublisherTitle(book.getPublisher().getTitle());
        dto.setFeedbacks(dtos);

        dto.setGenreText(localizator.localize(language, enumerationUtil.getBookGenreLocalizationKey(book.getGenre())));
        dto.setLanguageText(localizator.localize(language, enumerationUtil.getBookLanguageLocalizationKey(book.getLanguage())));
        dto.setFormatText(generateFormat(book, language));
        return dto;
    }

    private String generateFormat(Book book, String language) {
        StringJoiner result = new StringJoiner(", ");
        result.add(localizator.localize(language, "printed"));
        if (book.isHasElectronicFormat()) result.add(localizator.localize(language, "eBook"));
        if (book.isValuable()) result.add(localizator.localize(language, "valuableBook"));
        return result.toString();
    }

    private String generateProperties(Book book, String language) {
        StringJoiner joiner = new StringJoiner(", ");
        joiner.add(book.getAuthor());
        joiner.add(localizator.localize(language, enumerationUtil.getBookGenreLocalizationKey(book.getGenre())));
        joiner.add(localizator.localize(language, enumerationUtil.getBookLanguageShortLocalizationKey(book.getLanguage())));
        joiner.add(Integer.toString(book.getPublicationDate().getYear()));
        if (book.isHasElectronicFormat()) joiner.add(localizator.localize(language, "eBook"));
        return joiner.toString();
    }
}



