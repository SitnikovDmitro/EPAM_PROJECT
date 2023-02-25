package app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO of user entity with reader role
 * @see app.entity.User
 **/
@Getter
@Setter
public class ReaderDTO {
    private Integer id;
    private Boolean isBlocked;
    private String fine;
    private String email;
    private String firstname;
    private String lastname;
    private String fullname;
}
