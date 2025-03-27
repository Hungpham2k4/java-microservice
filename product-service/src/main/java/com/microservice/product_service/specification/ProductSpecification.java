package com.microservice.product_service.specification;


import com.microservice.product_service.entity.Product;
import com.microservice.product_service.form.ProductFilterForm;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;

public class ProductSpecification {
    public static Specification<Product> buildSpec(ProductFilterForm form) {
        return form == null ? null : (Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            var predicates = new ArrayList<Predicate>(); // danh sách điều kiện

            // Lọc theo ID danh mục
            if (form.getProductCategories() != null) {
                predicates.add(builder.equal(root.get("categoryId"), form.getProductCategories()));
            }

            // Lọc theo tên sản phẩm
            if (form.getProductName() != null) {
                predicates.add(builder.like(root.get("productName"), "%" + form.getProductName() + "%"));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
