package com.microservice.product_service.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductFilterForm {
    private Long productCategories; // ID danh mục để lọc
    private String productName; // Tên sản phẩm để lọc
}
