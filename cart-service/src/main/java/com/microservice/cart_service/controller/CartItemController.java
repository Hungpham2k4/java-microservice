package com.microservice.cart_service.controller;

import com.microservice.cart_service.dto.CartItemDto;
import com.microservice.cart_service.form.CartItemCreateForm;
import com.microservice.cart_service.form.CartItemUpdateForm;
import com.microservice.cart_service.service.CartItemService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/cart-items")
public class CartItemController {
    private final CartItemService cartItemService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CartItemDto addCartItem(@RequestBody @Valid CartItemCreateForm form) {
        return cartItemService.addCartItem(form);
    }

    @PutMapping
    public void updateCartItem(@RequestBody @Valid CartItemUpdateForm form) {
        cartItemService.updateCartItem(form);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{cartId}/{cartItemId}")
    public void deleteCartItem(@PathVariable long cartId, @PathVariable long cartItemId) {
        cartItemService.deleteCartItem(cartId, cartItemId);
    }

    @GetMapping("/{cartId}")
    public Page<CartItemDto> getCartItems(@PathVariable Long cartId, Pageable pageable) {
        return cartItemService.getCartItems(cartId, pageable);
    }
}
