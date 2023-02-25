package app.service.mapper.impl;


import app.dto.LibrarianDTO;
import app.dto.ReaderDTO;
import app.entity.User;
import app.enums.UserRole;
import app.service.mapper.UserMapper;
import app.service.utils.FormatUtil;

public class UserMapperImpl implements UserMapper {
    private final FormatUtil formatUtil;

    public UserMapperImpl(FormatUtil formatUtil) {
        this.formatUtil = formatUtil;
    }

    @Override
    public ReaderDTO toReaderDTO(User user) {
        if (user.getRole() != UserRole.READER) throw new RuntimeException("Invalid entity");
        ReaderDTO dto = new ReaderDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFirstname(user.getFirstname());
        dto.setLastname(user.getLastname());
        dto.setFullname(user.getFirstname()+" "+user.getLastname());
        dto.setIsBlocked(user.isBlocked());
        dto.setFine(formatUtil.formatPrice(user.getFine()));
        return dto;
    }

    @Override
    public LibrarianDTO toLibrarianDTO(User user) {
        if (user.getRole() != UserRole.LIBRARIAN) throw new RuntimeException("Invalid entity");
        LibrarianDTO dto = new LibrarianDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFirstname(user.getFirstname());
        dto.setLastname(user.getLastname());
        dto.setFullname(user.getFirstname()+" "+user.getLastname());
        return dto;
    }
}
