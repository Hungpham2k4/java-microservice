package com.microservice.cart_service.service;


import com.microservice.cart_service.dto.CartDto;

public interface CartService {
    CartDto createCart(Long userId);

    CartDto getCart(Long userId);

    void deleteCart(Long cartId);
}
