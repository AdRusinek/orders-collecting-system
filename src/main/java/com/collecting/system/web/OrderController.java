package com.collecting.system.web;

import com.collecting.system.dto.OrderDto;
import com.collecting.system.services.ErrorService;
import com.collecting.system.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by Adrian Rusinek on 22.07.2020
 **/
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/orders")
public class OrderController {

    private final OrderService orderService;
    private final ErrorService errorService;
    @Value("${secret-header}")
    private String secretHeader;

    @GetMapping
    public ResponseEntity<?> listOrders(@RequestParam(value = "showRealTimeOrders", required = false) Boolean showRealTimeOrders) {

        if (showRealTimeOrders == null) {
            showRealTimeOrders = false;
        }

        return new ResponseEntity<>(orderService.listOrders(showRealTimeOrders), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Long id) {
        return new ResponseEntity<>(orderService.getOrderById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> placeOrder(@Valid @RequestBody OrderDto orderDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return errorService.validateErrors(bindingResult);
        }

        return new ResponseEntity<>(orderService.placeOrder(orderDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderById(@PathVariable Long id, @RequestHeader(name = "secret") String secret) {

        if (secret.equals(secretHeader)) {
            orderService.deleteOrderById(id);
            return new ResponseEntity<>("Order with id '" + id + "' was deleted.", HttpStatus.NO_CONTENT);
        }

        return respondNotAuthorized("delete");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@Valid @RequestBody OrderDto orderDto, @PathVariable Long id, BindingResult bindingResult,
                                         @RequestHeader(name = "secret") String secret) {
        if (bindingResult.hasErrors()) {
            return errorService.validateErrors(bindingResult);
        }

        if (secret.equals(secretHeader)) {
            return new ResponseEntity<>(orderService.updateOrder(id, orderDto), HttpStatus.OK);
        }

        return respondNotAuthorized("update");
    }

    private ResponseEntity<?> respondNotAuthorized(String operation) {
        return new ResponseEntity<>("You are not authorized to " + operation + " order.", HttpStatus.FORBIDDEN);
    }
}
