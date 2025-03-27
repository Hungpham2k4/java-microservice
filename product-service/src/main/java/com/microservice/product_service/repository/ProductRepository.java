package com.microservice.product_service.repository;

import com.microservice.product_service.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    Page<Product> findByProductCategories_Category_IdIn(List<Long> categoryId, Pageable pageable); // Tìm sản phẩm theo ID danh mục
    Page<Product> findByNameContaining(String name, Pageable pageable); // Tìm sản phẩm theo tên
    Page<Product> findByStatus(String status, Pageable pageable); // Tìm sản phẩm theo trạng thái
}
