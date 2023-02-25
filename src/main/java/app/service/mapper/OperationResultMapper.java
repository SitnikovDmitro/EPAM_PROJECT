package app.service.mapper;

import app.dto.BookCreationEditionResultDTO;
import app.dto.UserCreationEditionResultDTO;
import app.dto.SendAccessLinkResultDTO;
import app.dto.UserFindResultDTO;
import app.tuples.BookCreationEditionResult;
import app.tuples.UserCreationEditionResult;
import app.tuples.SendAccessLinkResult;
import app.tuples.UserFindResult;

/**
 * Mapper converting operation results to dto
 **/
public interface OperationResultMapper {
    /**
     * Converts book creation or edition result to dto
     * @param result book creation or edition result
     * @param language language
     * @return dto
     */
    BookCreationEditionResultDTO toDTO(BookCreationEditionResult result, String language);

    /**
     * Converts book creation or edition result to dto
     * @param result user creation or edition result
     * @param language language
     * @return dto
     */
    UserCreationEditionResultDTO toDTO(UserCreationEditionResult result, String language);

    /**
     * Converts book creation or edition result to dto
     * @param result user find result
     * @param language language
     * @return dto
     */
    UserFindResultDTO toDTO(UserFindResult result, String language);

    /**
     * Converts book creation or edition result to dto
     * @param result send access link result
     * @param language language
     * @return dto
     */
    SendAccessLinkResultDTO toDTO(SendAccessLinkResult result, String language);
}
