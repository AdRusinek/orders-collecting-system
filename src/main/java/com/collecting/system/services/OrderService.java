package com.collecting.system.services;

import com.collecting.system.dto.OrderDto;

import java.util.List;

/**
 * Created by Adrian Rusinek on 24.07.2020
 **/
public interface OrderService {

    void deleteOrderById(Long id);

    OrderDto getOrderById(Long id);

    OrderDto placeOrder(OrderDto orderDto);

    OrderDto updateOrder(Long id, OrderDto orderDto);

    List<OrderDto> listOrders(Boolean showRealTimeOrders);
}
