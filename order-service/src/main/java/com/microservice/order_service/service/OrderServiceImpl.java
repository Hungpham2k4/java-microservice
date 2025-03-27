package com.microservice.order_service.service;

import com.microservice.order_service.dto.OrderDto;
import com.microservice.order_service.entity.*;
import com.microservice.order_service.form.OrderCreateForm;
import com.microservice.order_service.form.OrderUpdateForm;
import com.microservice.order_service.repository.CartRepository;
import com.microservice.order_service.repository.OrderRepository;
import com.microservice.order_service.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final ModelMapper modelMapper;

    @Override
    public OrderDto createOrder(OrderCreateForm form, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Cart cart = cartRepository.findById(form.getCartId())
                .orElseThrow(() -> new EntityNotFoundException("Cart not found"));

        Order order = new Order();
        order.setUser(user);
        order.setRecipientName(form.getRecipientName());
        order.setPhoneNumber(form.getPhoneNumber());
        order.setAddress(form.getAddress());
        order.setStatusOrder(StatusOrder.valueOf(form.getStatusOrder())); // Chuyển đổi trạng thái
        order.setTotalPrice(cart.getItems().stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum());
        // Trong phương thức createOrder của OrderServiceImpl
        order.setItems(cart.getItems().stream()
                .map(cartItem -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrder(order); // Thiết lập liên kết với Order
                    orderItem.setProduct(cartItem.getProduct());
                    orderItem.setQuantity(cartItem.getQuantity());
                    orderItem.setPrice(cartItem.getProduct().getPrice());
                    return orderItem;
                }).toList());

        Order savedOrder = orderRepository.save(order);
        return modelMapper.map(savedOrder, OrderDto.class);
    }

    @Override
    public OrderDto getOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        return modelMapper.map(order, OrderDto.class);
    }

    @Override
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }

    @Override
    public List<OrderDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(order -> modelMapper.map(order, OrderDto.class))
                .toList();
    }

    @Override
    public OrderDto updateStatusOrder(OrderUpdateForm orderUpdateForm, Long orderId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        order.setStatusOrder(StatusOrder.valueOf(orderUpdateForm.getStatusOrder()));
        Order savedOrder = orderRepository.save(order);
        return modelMapper.map(savedOrder, OrderDto.class);
    }

    @Override
    public Page<OrderDto> getOrdersByUserId(Long userId, Pageable pageable) {
        Page<Order> orders = orderRepository.findByUserId(userId, pageable);
        return orders.map(order -> modelMapper.map(order, OrderDto.class));
    }
}
