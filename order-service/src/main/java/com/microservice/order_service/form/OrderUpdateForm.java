package com.microservice.order_service.form;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderUpdateForm {

    @NotNull(message = "Trạng thái không được để trống")
    private String statusOrder; // Trạng thái đơn hàng
}
