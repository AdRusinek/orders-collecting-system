package com.collecting.system.web;

import com.collecting.system.domain.Order;
import com.collecting.system.dto.OrderDto;
import com.collecting.system.repositories.OrderRepository;
import com.collecting.system.web.mappers.OrderMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Adrian Rusinek on 24.07.2020
 **/
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {

    private final String API_V1_ORDERS = "/api/v1/orders/";

    @Value("${secret-header}")
    private String secretHeader;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void updateOrderReturnsBadRequestDueToMissingOrderName() throws Exception {
        Order savedOrder = createAndSaveOrder();
        savedOrder.setOrderName(null);

        mockMvc.perform(patch(API_V1_ORDERS + savedOrder.getId())
                .contentType("application/json")
                .header("secret", secretHeader)
                .content(objectMapper.writeValueAsString(savedOrder)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateOrderReturnsForbiddenDueToWrongSecretHeader() throws Exception {
        Order savedOrder = createAndSaveOrder();
        long savedOrderId = savedOrder.getId();

        savedOrder.setId(null);

        mockMvc.perform(patch(API_V1_ORDERS + savedOrderId)
                .contentType("application/json")
                .header("secret", "wrong")
                .content(objectMapper.writeValueAsString(savedOrder)))
                .andExpect(status().isForbidden());
    }

    @Test
    void updateOrderReturnsOk() throws Exception {
        Order savedOrder = createAndSaveOrder();
        long savedOrderId = savedOrder.getId();

        savedOrder.setId(null);
        savedOrder.setOrderName("water");

        mockMvc.perform(patch(API_V1_ORDERS + savedOrderId)
                .contentType("application/json")
                .header("secret", secretHeader)
                .content(objectMapper.writeValueAsString(savedOrder)))
                .andExpect(status().isOk());

        assertThat(orderRepository.findById(savedOrderId).get().getOrderName()).isEqualTo("water");
    }

    @Test
    void placeOrderReturnsOk() throws Exception {
        Order order = createAndSaveOrder();
        order.setId(null);

        mockMvc.perform(post(API_V1_ORDERS)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(orderMapper.orderToOrderDto(order))))
                .andExpect(status().isCreated());
    }

    @Test
    void placeOrderReturnsLackOfCustomerNameError() throws Exception {
        OrderDto order = OrderDto.builder().orderName("order").build();

        mockMvc.perform(post(API_V1_ORDERS).contentType("application/json")
                .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void listAllOrders() throws Exception {
        mockMvc.perform(get(API_V1_ORDERS))
                .andExpect(status().isOk());
    }

    @Test
    void getOrderById() throws Exception {
        Order order = createAndSaveOrder();

        mockMvc.perform(get(API_V1_ORDERS + order.getId()))
                .andExpect(status().isOk());
    }


    @Test
    void getOrderByIdIdNotFound() throws Exception {
        mockMvc.perform(get(API_V1_ORDERS + "-1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteOrderByIdReturnsOk() throws Exception {
        Order order = createAndSaveOrder();

        mockMvc.perform(delete(API_V1_ORDERS + order.getId())
                .header("secret", secretHeader))
                .andExpect(status().isNoContent())
                .andReturn();

        assertThat(orderRepository.findById(order.getId())).isEmpty();
    }


    @Test
    void deleteOrderByIdReturnsWrongSecret() throws Exception {
        Order order = createAndSaveOrder();

        mockMvc.perform(delete(API_V1_ORDERS + order.getId())
                .header("secret", "wrong-secret"))
                .andExpect(status().isForbidden())
                .andReturn();

        assertThat(orderRepository.findById(order.getId())).isNotEmpty();
    }

    private Order createAndSaveOrder() {
        Order order = orderRepository
                .save(orderMapper.orderDtoToOrder(OrderDto.builder()
                        .customerFullName("somebody")
                        .orderName("pizza")
                        .build()));

        order.setDateOfPurchase(null);
        order.setLastModifiedDate(null);
        return order;
    }
}
