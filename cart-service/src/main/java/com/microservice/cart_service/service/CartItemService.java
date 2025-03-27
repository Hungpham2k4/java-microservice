package com.microservice.cart_service.service;

import com.microservice.cart_service.dto.CartItemDto;
import com.microservice.cart_service.form.CartItemCreateForm;
import com.microservice.cart_service.form.CartItemUpdateForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CartItemService {
    CartItemDto addCartItem(CartItemCreateForm form);

    void updateCartItem(CartItemUpdateForm form);

    void deleteCartItem(Long cartId, Long cartItemId);

    Page<CartItemDto> getCartItems(Long cartId, Pageable pageable);
    void deleteCartItems(Long cartId, List<Long> cartItemIds);
}
