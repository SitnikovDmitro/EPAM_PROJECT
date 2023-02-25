package app.service.mapper;

import app.dto.OrderDTO;
import app.entity.Order;

public interface OrderMapper {
    OrderDTO toOrderDTO(Order order, String language);
}
