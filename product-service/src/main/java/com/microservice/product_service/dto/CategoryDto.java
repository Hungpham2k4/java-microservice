package com.microservice.product_service.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CategoryDto {
    private Long id;
    private String name; // Tên danh mục
    private Long parent; // ID của danh mục cha
    private String status; // Trạng thái
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
