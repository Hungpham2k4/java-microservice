package com.microservice.post_service.specification;

import com.microservice.post_service.entity.Post;
import com.microservice.post_service.form.PostFilterForm;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class PostSpecification {
    public static Specification<Post> buildSpec(PostFilterForm form) {
        return form == null ? null : (Root<Post> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            var predicates = new ArrayList<Predicate>(); // danh sách điều kiện

            // Tìm kiếm
            addSearchPredicate(root, form.getSearch(), builder, predicates);

            // Lọc theo ngày tạo
            addDatePredicates(root, form, builder, predicates);

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static void addSearchPredicate(Root<Post> root, String search, CriteriaBuilder builder, ArrayList<Predicate> predicates) {
        Predicate searchPredicate = PostSpecificationUtils.buildSearchPredicate(root, search, builder);
        if (searchPredicate != null) {
            predicates.add(searchPredicate);
        }
    }

    private static void addDatePredicates(Root<Post> root, PostFilterForm form, CriteriaBuilder builder, ArrayList<Predicate> predicates) {
        LocalDateTime minCreatedAt = getMinCreatedDate(form);
        Predicate minCreatedPredicate = PostSpecificationUtils.buildMinCreatedDatePredicate(root, minCreatedAt, builder);
        if (minCreatedPredicate != null) {
            predicates.add(minCreatedPredicate);
        }

        LocalDateTime maxCreatedAt = getMaxCreatedDate(form);
        Predicate maxCreatedPredicate = PostSpecificationUtils.buildMaxCreatedDatePredicate(root, maxCreatedAt, builder);
        if (maxCreatedPredicate != null) {
            predicates.add(maxCreatedPredicate);
        }
    }

    private static LocalDateTime getMinCreatedDate(PostFilterForm form) {
        return form.getMinCreatedDate() != null ?
                LocalDateTime.of(form.getMinCreatedDate(), LocalTime.MIN) : null;
    }

    private static LocalDateTime getMaxCreatedDate(PostFilterForm form) {
        return form.getMaxCreatedDate() != null ?
                LocalDateTime.of(form.getMaxCreatedDate(), LocalTime.MAX) : null;
    }

}
