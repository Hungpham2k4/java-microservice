package com.microservice.category_service.service;

import com.microservice.category_service.dto.CategoryDto;
import com.microservice.category_service.form.CategoryCreateForm;
import com.microservice.category_service.form.CategoryFilterForm;
import com.microservice.category_service.form.CategoryUpdateForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    CategoryDto createCategory(CategoryCreateForm categoryCreateForm);

    CategoryDto updateCategory(CategoryUpdateForm categoryUpdateForm, long id);

    void deleteCategory(long id);

    CategoryDto getCategory(long id);

    Page<CategoryDto> getAllCategories(CategoryFilterForm categoryFilterForm, Pageable pageable);
}
