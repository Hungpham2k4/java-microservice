package com.microservice.product_service.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ProductDto {
    private Long id;
    private String name; // Tên sản phẩm
    private Double price; // Giá sản phẩm
    private String description; // Mô tả sản phẩm
    private String detail; // Chi tiết sản phẩm
    private String images; // Hình ảnh sản phẩm
    private String thumbnail; // Hình thu nhỏ sản phẩm
    private String status; // Trạng thái sản phẩm
    private String slug; // Slug sản phẩm
    private long quantity; // Số lượng sản phẩm
    private LocalDateTime createdAt; // Thời gian tạo
    private LocalDateTime updatedAt; // Thời gian cập nhật
    private List<ProductCategoryDto> productCategories;
}
