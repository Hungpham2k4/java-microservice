package com.microservice.category_service.specification;

import com.microservice.category_service.entity.Category;
import com.microservice.category_service.form.CategoryFilterForm;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;

public class CategorySpecification {
    public static Specification<Category> buildSpec(CategoryFilterForm form) {
        return form == null ? null : (Root<Category> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            var predicates = new ArrayList<Predicate>(); // danh sách điều kiện

            // Lọc theo ID danh mục cha
            if (form.getParent() != null) {
                predicates.add(builder.equal(root.get("parentId"), form.getParent()));
            }

            // Lọc theo tên danh mục
            if (form.getName() != null) {
                predicates.add(builder.like(root.get("name"), "%" + form.getName() + "%"));
            }

            // Lọc theo trạng thái
            if (form.getStatus() != null) {
                predicates.add(builder.equal(root.get("status"), form.getStatus()));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
