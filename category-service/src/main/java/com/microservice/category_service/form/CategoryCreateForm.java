package com.microservice.category_service.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryCreateForm {
    @NotBlank(message = "Tên danh mục không được để trống")
    private String name; // Tên danh mục

    private Long parent; // ID danh mục cha (nếu có)

    @NotNull(message = "Trạng thái không được để trống")
    private String status; // Trạng thái danh mục
}
