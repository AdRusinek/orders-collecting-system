package com.collecting.system.services;

import com.collecting.system.dto.OrderDto;

import java.util.List;

/**
 * Created by Adrian Rusinek on 24.07.2020
 **/
public interface OrderService {

    OrderDto placeOrder(OrderDto orderDto);

    void deleteOrderById(Long id);

    OrderDto getOrderById(Long id);

    List<OrderDto> listOrders(Boolean showRealTimeOrders);

    OrderDto updateOrder(Long id, OrderDto orderDto);
}
