package app.service.mapper.impl;


import app.dto.OrderDTO;
import app.entity.Order;
import app.service.mapper.OrderMapper;
import app.service.other.Localizator;
import app.service.utils.EnumerationUtil;

public class OrderMapperImpl implements OrderMapper {
    private final Localizator localizator;
    private final EnumerationUtil enumerationUtil;

    public OrderMapperImpl(Localizator localizator, EnumerationUtil enumerationUtil) {
        this.localizator = localizator;
        this.enumerationUtil = enumerationUtil;
    }

    @Override
    public OrderDTO toOrderDTO(Order order, String language) {
        OrderDTO dto = new OrderDTO();
        dto.setCode(order.getCode());
        dto.setUserId(order.getUser().getId());
        dto.setUserFullname(order.getUser().getFirstname()+" "+order.getUser().getLastname());
        dto.setBookId(order.getBook().getId());
        dto.setBookTitle(order.getBook().getTitle());
        dto.setBookIsValuable(order.getBook().isValuable());
        dto.setBookHasFreeCopies(order.getBook().getFreeCopiesNumber() > 0);
        dto.setState(order.getState());
        dto.setCreationDate(order.getCreationDate());
        dto.setDeadlineDate(order.getDeadlineDate());
        dto.setStateText(localizator.localize(language, enumerationUtil.getOrderStateLocalizationKey(order.getState())));
        return dto;
    }
}
