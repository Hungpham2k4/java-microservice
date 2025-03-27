package com.microservice.order_service.service;

import com.microservice.order_service.dto.OrderDto;
import com.microservice.order_service.form.OrderCreateForm;
import com.microservice.order_service.form.OrderUpdateForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    OrderDto createOrder(OrderCreateForm form, Long userId);

    OrderDto getOrder(Long orderId);

    void deleteOrder(Long orderId);

    List<OrderDto> getAllOrders();

    Page<OrderDto> getOrdersByUserId(Long userId, Pageable pageable);
    OrderDto updateStatusOrder(OrderUpdateForm orderUpdateForm, Long orderId);
}
