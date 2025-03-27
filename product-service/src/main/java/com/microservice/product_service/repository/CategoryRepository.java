package com.microservice.product_service.repository;

import com.microservice.product_service.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {
    Page<Category> findByParent(Long parentId, Pageable pageable); // Tìm danh mục theo ID danh mục cha
    Page<Category> findByNameContaining(String name, Pageable pageable); // Tìm danh mục theo tên
}
