package app.service.mapper.impl;

import app.dto.BookCreationEditionResultDTO;
import app.dto.UserCreationEditionResultDTO;
import app.dto.SendAccessLinkResultDTO;
import app.dto.UserFindResultDTO;
import app.tuples.BookCreationEditionResult;
import app.tuples.UserCreationEditionResult;
import app.tuples.SendAccessLinkResult;
import app.tuples.UserFindResult;
import app.service.mapper.OperationResultMapper;
import app.service.other.Localizator;

public class OperationResultMapperImpl implements OperationResultMapper {
    private final Localizator localizator;

    public OperationResultMapperImpl(Localizator localizator) {
        this.localizator = localizator;
    }

    @Override
    public BookCreationEditionResultDTO toDTO(BookCreationEditionResult result, String language) {
        BookCreationEditionResultDTO dto = new BookCreationEditionResultDTO();

        dto.setSuccess(result.getSuccess());
        dto.setIsbnValid(result.getIsbnValid());
        if (!result.getIsbnValid()) dto.setIsbnValidationFeedback(localizator.localize(language, result.getIsbnValidationFeedbackKey()));
        dto.setTitleValid(result.getTitleValid());
        if (!result.getTitleValid()) dto.setTitleValidationFeedback(localizator.localize(language, result.getTitleValidationFeedbackKey()));
        dto.setAuthorValid(result.getAuthorValid());
        if (!result.getAuthorValid()) dto.setAuthorValidationFeedback(localizator.localize(language, result.getAuthorValidationFeedbackKey()));
        dto.setDescriptionValid(result.getDescriptionValid());
        if (!result.getDescriptionValid()) dto.setDescriptionValidationFeedback(localizator.localize(language, result.getDescriptionValidationFeedbackKey()));
        dto.setPublicationDateValid(result.getPublicationDateValid());
        if (!result.getPublicationDateValid()) dto.setPublicationDateValidationFeedback(localizator.localize(language, result.getPublicationDateValidationFeedbackKey()));
        dto.setGenreValid(result.getGenreValid());
        if (!result.getGenreValid()) dto.setGenreValidationFeedback(localizator.localize(language, result.getGenreValidationFeedbackKey()));
        dto.setLanguageValid(result.getLanguageValid());
        if (!result.getLanguageValid()) dto.setLanguageValidationFeedback(localizator.localize(language, result.getLanguageValidationFeedbackKey()));
        dto.setTotalCopiesNumberValid(result.getTotalCopiesNumberValid());
        if (!result.getTotalCopiesNumberValid()) dto.setTotalCopiesNumberValidationFeedback(localizator.localize(language, result.getTotalCopiesNumberValidationFeedbackKey()));
        dto.setPublisherTitleValid(result.getPublisherTitleValid());
        if (!result.getPublisherTitleValid()) dto.setPublisherTitleValidationFeedback(localizator.localize(language, result.getPublisherTitleValidationFeedbackKey()));

        return dto;
    }

    @Override
    public UserCreationEditionResultDTO toDTO(UserCreationEditionResult result, String language) {
        UserCreationEditionResultDTO dto = new UserCreationEditionResultDTO();

        dto.setSuccess(result.getSuccess());
        dto.setFirstnameValid(result.getFirstnameValid());
        if (!result.getFirstnameValid()) dto.setFirstnameValidationFeedback(localizator.localize(language, result.getFirstnameValidationFeedbackKey()));
        dto.setLastnameValid(result.getLastnameValid());
        if (!result.getLastnameValid()) dto.setLastnameValidationFeedback(localizator.localize(language, result.getLastnameValidationFeedbackKey()));
        dto.setEmailValid(result.getEmailValid());
        if (!result.getEmailValid()) dto.setEmailValidationFeedback(localizator.localize(language, result.getEmailValidationFeedbackKey()));
        dto.setPasswordValid(result.getPasswordValid());
        if (!result.getPasswordValid()) dto.setPasswordValidationFeedback(localizator.localize(language, result.getPasswordValidationFeedbackKey()));
        dto.setCaptchaValid(result.getCaptchaValid());
        if (!result.getCaptchaValid()) dto.setCaptchaValidationFeedback(localizator.localize(language, result.getCaptchaValidationFeedbackKey()));
        return dto;
    }

    @Override
    public UserFindResultDTO toDTO(UserFindResult result, String language) {
        UserFindResultDTO dto = new UserFindResultDTO();

        dto.setSuccess(result.getSuccess());
        if (!result.getSuccess()) dto.setValidationFeedback(localizator.localize(language, result.getValidationFeedbackKey()));
        if (result.getSuccess()) dto.setRole(result.getUser().getRole().toInt());

        return dto;
    }

    @Override
    public SendAccessLinkResultDTO toDTO(SendAccessLinkResult result, String language) {
        SendAccessLinkResultDTO dto = new SendAccessLinkResultDTO();

        dto.setSuccess(result.getSuccess());
        dto.setEmailValid(result.getEmailValid());
        if (!result.getEmailValid()) dto.setEmailValidationFeedback(localizator.localize(language, result.getEmailValidationFeedbackKey()));
        dto.setPasswordValid(result.getPasswordValid());
        if (!result.getPasswordValid()) dto.setPasswordValidationFeedback(localizator.localize(language, result.getPasswordValidationFeedbackKey()));

        return dto;
    }
}
