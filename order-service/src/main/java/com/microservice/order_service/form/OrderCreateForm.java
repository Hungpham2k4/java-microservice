package com.microservice.order_service.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderCreateForm {

    @NotNull(message = "ID giỏ hàng không được để trống")
    private Long cartId; // ID giỏ hàng

    @NotBlank(message = "Tên người nhận không được để trống")
    private String recipientName; // Tên người nhận

    @NotBlank(message = "Địa chỉ không được để trống")
    private String address; // Địa chỉ giao hàng

    @NotBlank(message = "Số điện thoại không được để trống")
    private String phoneNumber; // Số điện thoại người nhận

    @NotNull(message = "Trạng thái không được để trống")
    private String statusOrder; // Trạng thái đơn hàng
}
