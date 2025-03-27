package com.microservice.cart_service.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class CartDto {
    private Long id;
    private Long userId; // ID của người dùng
    private List<CartItemDto> items; // Danh sách sản phẩm trong giỏ hàng
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
