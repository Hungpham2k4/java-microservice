package com.microservice.product_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCategoryDto {
    private Long id; // ID của ProductCategory
    private CategoryDto category; // Danh mục liên kết
}
