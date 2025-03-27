package com.microservice.product_service.service;

import com.microservice.product_service.dto.ProductDto;
import com.microservice.product_service.entity.Category;
import com.microservice.product_service.entity.Product;
import com.microservice.product_service.entity.ProductCategory;
import com.microservice.product_service.form.ProductCreateForm;
import com.microservice.product_service.form.ProductFilterForm;
import com.microservice.product_service.form.ProductUpdateForm;
import com.microservice.product_service.repository.CategoryRepository;
import com.microservice.product_service.repository.ProductRepository;
import com.microservice.product_service.specification.ProductSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;

    private void createProductCategories(Product product, List<Long> categoryIds) {
        if (product.getProductCategories() == null) {
            product.setProductCategories(new ArrayList<>());
        } else {
            product.getProductCategories().clear(); // Xóa các liên kết cũ
        }
        for (Long categoryId : categoryIds) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new EntityNotFoundException("Category not found"));
            ProductCategory productCategory = new ProductCategory();
            productCategory.setProduct(product);
            productCategory.setCategory(category);
            product.getProductCategories().add(productCategory);
        }
    }

    private void updateProductCategories(Product product, List<Long> categoryIds) {
        if (product.getProductCategories() == null) {
            product.setProductCategories(new ArrayList<>());
        }

        // Xóa các liên kết cũ không còn trong danh sách mới
        product.getProductCategories().removeIf(pc ->
                pc == null || pc.getCategory() == null || !categoryIds.contains(pc.getCategory().getId())
        );

        // Tạo danh sách mới để tránh lỗi orphanRemoval
        List<ProductCategory> updatedProductCategories = new ArrayList<>();

        for (Long categoryId : categoryIds) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new EntityNotFoundException("Category not found"));

            boolean exists = product.getProductCategories().stream()
                    .anyMatch(pc -> pc != null && pc.getCategory() != null && pc.getCategory().getId().equals(categoryId));

            if (!exists) {
                ProductCategory productCategory = new ProductCategory();
                productCategory.setProduct(product);
                productCategory.setCategory(category);
                updatedProductCategories.add(productCategory);
            }
        }
        // Gán lại danh sách mới vào product
        product.getProductCategories().addAll(updatedProductCategories);
    }


    @Override
    public ProductDto create(ProductCreateForm productCreateForm) {
        Product product = modelMapper.map(productCreateForm, Product.class);
        createProductCategories(product, productCreateForm.getProductCategories());
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDto.class);
    }

    @Override
    public ProductDto update(ProductUpdateForm productUpdateForm, Long productId) {
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        // Ánh xạ các trường khác, nhưng không ánh xạ productCategories
        modelMapper.map(productUpdateForm, product);
        // Cập nhật productCategories thủ công
        updateProductCategories(product, productUpdateForm.getProductCategories());
        var updatedProduct = productRepository.saveAndFlush(product);
        return modelMapper.map(updatedProduct, ProductDto.class);
    }

    @Override
    public void delete(Long productId) {
        productRepository.deleteById(productId);
    }
    @Override
    public Page<ProductDto> findAll(ProductFilterForm filterForm, Pageable pageable) {
        var spec = ProductSpecification.buildSpec(filterForm);
        Page<Product> products = productRepository.findAll(spec ,pageable);
        return products.map(product -> modelMapper.map(product, ProductDto.class));
    }

    @Override
    public ProductDto findById(Long productId) {
        return productRepository.findById(productId)
                .map(product -> modelMapper.map(product, ProductDto.class))
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
    }

    @Override
    public Page<ProductDto> findByCategoryId(Long categoryId, Pageable pageable) {
        List<Long> categoryIds = List.of(categoryId);
        Page<Product> products = productRepository.findByProductCategories_Category_IdIn(categoryIds, pageable);
        return products.map(product -> modelMapper.map(product, ProductDto.class));
    }

    @Override
    public Page<ProductDto> findByNameContaining(String name, Pageable pageable) {
        Page<Product> products = productRepository.findByNameContaining(name, pageable);
        return products.map(product -> modelMapper.map(product, ProductDto.class));
    }

    @Override
    public Page<ProductDto> findByStatus(String status, Pageable pageable) {
        Page<Product> products = productRepository.findByStatus(status.toUpperCase(Locale.ROOT), pageable);
        return products.map(product -> modelMapper.map(product, ProductDto.class));
    }

}
