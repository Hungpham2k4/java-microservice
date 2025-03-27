package com.microservice.order_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDto {
    private Long id;
    private Long productId; // ID của sản phẩm
    private int quantity; // Số lượng sản phẩm
    private Double price; // Giá của sản phẩm
}
