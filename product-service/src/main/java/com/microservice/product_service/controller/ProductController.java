package com.microservice.product_service.controller;

import com.microservice.product_service.dto.ProductDto;
import com.microservice.product_service.form.ProductCreateForm;
import com.microservice.product_service.form.ProductFilterForm;
import com.microservice.product_service.form.ProductUpdateForm;
import com.microservice.product_service.service.IpfsService;
import com.microservice.product_service.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/products")
public class ProductController {
    private final ProductService productService;
    private final IpfsService ipfsService;


    @GetMapping
    public Page<ProductDto> findAll(ProductFilterForm filterForm, Pageable pageable) {
        return productService.findAll(filterForm ,pageable);
    }

    @GetMapping("/{id}")
    public ProductDto findById(@PathVariable("id") Long id) {
        return productService.findById(id);
    }

    @GetMapping("/category/{categoryId}")
    public Page<ProductDto> findByCategoryId(@PathVariable("categoryId") Long categoryId, Pageable pageable) {
        return productService.findByCategoryId(categoryId, pageable);
    }

    @GetMapping("/search")
    public Page<ProductDto> findByNameContaining(@RequestParam("name") String name, Pageable pageable) {
        return productService.findByNameContaining(name, pageable);
    }

    @GetMapping("/status/{status}")
    public Page<ProductDto> findByStatus(@PathVariable("status") String status, Pageable pageable) {
        return productService.findByStatus(status, pageable);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ProductDto create(@RequestBody @Valid ProductCreateForm productCreateForm) {
        return productService.create(productCreateForm);
    }

    @PutMapping("/{id}")
    public ProductDto update(
            @PathVariable("id") Long id,
            @RequestBody @Valid ProductUpdateForm productUpdateForm
    ) {
        return productService.update(productUpdateForm, id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        productService.delete(id);
    }

    @PostMapping("/upload-image")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String ipfsUrl = ipfsService.uploadToIpfs(file);
            return ResponseEntity.ok(ipfsUrl);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Upload failed: " + e.getMessage());
        }
    }
}
