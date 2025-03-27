package com.microservice.cart_service.form;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemCreateForm {

    @NotNull(message = "ID giỏ hàng không được để trống")
    private Long cartId; // ID giỏ hàng

    @NotNull(message = "ID sản phẩm không được để trống")
    private Long productId; // ID sản phẩm

    @NotNull(message = "Số lượng không được để trống")
    private Integer quantity; // Số lượng sản phẩm
}
