package com.microservice.category_service.controller;

import com.microservice.category_service.dto.CategoryDto;
import com.microservice.category_service.form.CategoryCreateForm;
import com.microservice.category_service.form.CategoryFilterForm;
import com.microservice.category_service.form.CategoryUpdateForm;
import com.microservice.category_service.service.CategoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public Page<CategoryDto> getAllCategories(CategoryFilterForm categoryFilterForm, Pageable pageable) {
        return categoryService.getAllCategories(categoryFilterForm ,pageable);
    }

    @GetMapping("/{id}")
    public CategoryDto getCategory(@PathVariable("id") Long id) {
        return categoryService.getCategory(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CategoryDto createCategory(@RequestBody @Valid CategoryCreateForm categoryCreateForm) {
        return categoryService.createCategory(categoryCreateForm);
    }

    @PutMapping("/{id}")
    public CategoryDto updateCategory(
            @PathVariable("id") Long id,
            @RequestBody @Valid CategoryUpdateForm categoryUpdateForm
    ) {
        return categoryService.updateCategory(categoryUpdateForm, id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable("id") Long id) {
        categoryService.deleteCategory(id);
    }
}
