package app.entity;

import app.enums.UserRole;
import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private int id;
    private int fine;
    private boolean isBlocked;
    private String email;
    private String firstname;
    private String lastname;
    private String passwordHash;
    private UserRole role;
    private LocalDate lastFineRecalculationDate;
}
