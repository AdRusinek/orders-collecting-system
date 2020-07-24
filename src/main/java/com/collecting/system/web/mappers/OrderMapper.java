package com.collecting.system.web.mappers;

import com.collecting.system.domain.Order;
import com.collecting.system.dto.OrderDto;
import org.mapstruct.Mapper;

import java.util.List;


/**
 * Created by Adrian Rusinek on 24.07.2020
 **/
@Mapper
public interface OrderMapper {

    OrderDto orderToOrderDto(Order order);

    List<OrderDto> orderToOrdersDto(List<Order> orders);

    Order orderDtoToOrder(OrderDto orderDto);
}
