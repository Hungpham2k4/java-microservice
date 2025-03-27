package com.microservice.cart_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemDto {
    private Long id;
    private Long cartId;
    private Long productId; // ID của sản phẩm
    private Integer quantity; // Số lượng sản phẩm
}
