package com.microservice.cart_service.controller;

import com.microservice.cart_service.dto.CartDto;
import com.microservice.cart_service.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/carts")
public class CartController {
    private final CartService cartService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{userId}")
    public CartDto createCart(@PathVariable Long userId) {
        return cartService.createCart(userId);
    }

    @GetMapping("/{userId}")
    public CartDto getCart(@PathVariable Long userId) {
        return cartService.getCart(userId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{cartId}")
    public void deleteCart(@PathVariable Long cartId) {
        cartService.deleteCart(cartId);
    }
}
