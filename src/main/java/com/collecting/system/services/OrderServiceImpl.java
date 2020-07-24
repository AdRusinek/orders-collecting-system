package com.collecting.system.services;

import com.collecting.system.domain.Order;
import com.collecting.system.dto.OrderDto;
import com.collecting.system.exceptions.OrderIdException;
import com.collecting.system.repositories.OrderRepository;
import com.collecting.system.web.mappers.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by Adrian Rusinek on 24.07.2020
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;

    @Override
    public OrderDto placeOrder(OrderDto orderDto) {
        log.info("New order '" + orderDto.getOrderName() +
                "' have been placed for customer '" + orderDto.getCustomerFullName() + "'.");
        return orderMapper.orderToOrderDto(orderRepository.save(orderMapper.orderDtoToOrder(orderDto)));
    }

    @Override
    public void deleteOrderById(Long id) {
        findAndValidate(id);

        log.info("Deleting order with id '" + id + "'.");
        orderRepository.deleteById(id);
    }

    @Override
    public OrderDto getOrderById(Long id) {
        log.info("Attempt of getting order with id '" + id + "'.");
        return orderMapper.orderToOrderDto(findAndValidate(id));
    }

    @Cacheable(cacheNames = "orderListCache", condition = "#showRealTimeOrders == false ")
    @Override
    public List<OrderDto> listOrders(Boolean showRealTimeOrders) {
        log.info("Getting all orders.");
        return orderMapper.orderToOrdersDto(orderRepository.findAll());
    }

    @Override
    public OrderDto updateOrder(Long id, OrderDto updatedOrder) {
        Order foundOrder = findAndValidate(id);

        foundOrder.setOrderName(updatedOrder.getOrderName());
        foundOrder.setCustomerFullName(updatedOrder.getCustomerFullName());

        log.info("Updating order with id '" + id + "'.");

        return orderMapper.orderToOrderDto(orderRepository.save(foundOrder));
    }

    private Order findAndValidate(Long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);

        if (!optionalOrder.isPresent()) {
            throw new OrderIdException("Order with id '" + id + "' does not exist.");
        }

        return optionalOrder.get();
    }
}
