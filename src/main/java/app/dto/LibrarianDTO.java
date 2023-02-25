package app.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO of user entity with librarian role
 * @see app.entity.User
 **/
@Getter
@Setter
public class LibrarianDTO {
    private Integer id;
    private String email;
    private String firstname;
    private String lastname;
    private String fullname;
}
