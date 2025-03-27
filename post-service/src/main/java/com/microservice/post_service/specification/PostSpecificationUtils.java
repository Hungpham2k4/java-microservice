package com.microservice.post_service.specification;

import com.microservice.post_service.entity.Post;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.time.LocalDateTime;

public class PostSpecificationUtils {

    public static Predicate buildSearchPredicate(Root<Post> root, String search, CriteriaBuilder builder) {
        if (search != null) {
            return builder.like(root.get("title"), "%" + search + "%");
        }
        return null;
    }

    public static Predicate buildMinCreatedDatePredicate(Root<Post> root, LocalDateTime minCreatedDate, CriteriaBuilder builder) {
        if (minCreatedDate != null) {
            return builder.greaterThanOrEqualTo(root.get("createdAt"), minCreatedDate);
        }
        return null;
    }

    public static Predicate buildMaxCreatedDatePredicate(Root<Post> root, LocalDateTime maxCreatedDate, CriteriaBuilder builder) {
        if (maxCreatedDate != null) {
            return builder.lessThanOrEqualTo(root.get("createdAt"), maxCreatedDate);
        }
        return null;
    }
}
