package com.microservice.category_service.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryFilterForm {
    private Long parent; // ID danh mục cha để lọc
    private String name;   // Tên danh mục để lọc
    private String status; // Trạng thái danh mục để lọc
}
