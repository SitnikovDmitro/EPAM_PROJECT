package app.service.mapper;

import app.dto.LibrarianDTO;
import app.dto.ReaderDTO;
import app.entity.User;

public interface UserMapper {
    ReaderDTO toReaderDTO(User user);

    LibrarianDTO toLibrarianDTO(User user);
}
