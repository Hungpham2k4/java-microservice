package com.microservice.order_service.controller;

import com.microservice.order_service.dto.OrderDto;
import com.microservice.order_service.form.OrderCreateForm;
import com.microservice.order_service.form.OrderUpdateForm;
import com.microservice.order_service.service.OrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/orders")
public class OrderController {
    private final OrderService orderService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{userId}")
    public OrderDto createOrder(@PathVariable Long userId, @RequestBody @Valid OrderCreateForm form) {
        return orderService.createOrder(form, userId);
    }

    @GetMapping("/{orderId}")
    public OrderDto getOrder(@PathVariable Long orderId) {
        return orderService.getOrder(orderId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{orderId}")
    public void deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
    }

    @GetMapping
    public List<OrderDto> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/user/{userId}")
    public Page<OrderDto> getOrdersByUserId(@PathVariable Long userId, Pageable pageable) {
        return orderService.getOrdersByUserId(userId, pageable);
    }

    @PutMapping("/{orderId}")
    public OrderDto updateStatusOrder(@PathVariable Long orderId, @RequestBody @Valid OrderUpdateForm form) {
        orderService.updateStatusOrder(form, orderId);
        return orderService.getOrder(orderId);
    }
}
