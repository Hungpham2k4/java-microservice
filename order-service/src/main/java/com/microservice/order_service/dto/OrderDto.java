package com.microservice.order_service.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrderDto {
    private Long id;
    private Long userId; // ID của người dùng
    private List<OrderItemDto> items; // Danh sách sản phẩm trong đơn hàng
    private Double totalPrice; // Tổng giá trị đơn hàng
    private String recipientName; // Tên người nhận
    private String address; // Địa chỉ giao hàng
    private String phoneNumber; // Số điện thoại người nhận
    private String statusOrder;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
