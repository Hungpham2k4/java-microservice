package com.microservice.product_service.service;

import com.microservice.product_service.dto.ProductDto;
import com.microservice.product_service.form.ProductCreateForm;
import com.microservice.product_service.form.ProductFilterForm;
import com.microservice.product_service.form.ProductUpdateForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    ProductDto create(ProductCreateForm productCreateForm);

    ProductDto update(ProductUpdateForm productUpdateForm, Long productId);

    void delete(Long productId);

    Page<ProductDto> findAll(ProductFilterForm productFilterForm, Pageable pageable);

    ProductDto findById(Long productId);

    Page<ProductDto> findByCategoryId(Long categoryId, Pageable pageable);

    Page<ProductDto> findByNameContaining(String name, Pageable pageable);

    Page<ProductDto> findByStatus(String status, Pageable pageable);
}
