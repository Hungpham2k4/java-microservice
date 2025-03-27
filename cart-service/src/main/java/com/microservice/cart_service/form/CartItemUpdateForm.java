package com.microservice.cart_service.form;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemUpdateForm {
    @NotNull(message = "ID giỏ hàng không được để trống")
    private Long cartId; // ID giỏ hàng

    @NotNull(message = "ID mục giỏ hàng không được để trống")
    private Long cartItemId; // ID của item trong giỏ hàng

    @NotNull(message = "Số lượng không được để trống")
    private Integer quantity; // Số lượng mới
}
