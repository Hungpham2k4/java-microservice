package com.microservice.comment_service.specification;

import com.microservice.comment_service.entity.Comment;
import com.microservice.comment_service.form.CommentFilterForm;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;

public class CommentSpecification {
    public static Specification<Comment> buildSpec(CommentFilterForm form) {
        return form == null ? null : (Root<Comment> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            var predicates = new ArrayList<Predicate>();

            // Sử dụng phương thức tiện ích để xây dựng predicate cho search
            Predicate searchPredicate = CommentSpecificationUtils.buildSearchPredicate(root, form.getSearch(), builder);
            if (searchPredicate != null) {
                predicates.add(searchPredicate);
            }

            // Sử dụng phương thức tiện ích để xây dựng predicate cho postId
            Predicate postIdPredicate = CommentSpecificationUtils.buildPostIdPredicate(root, form.getPostId(), builder);
            if (postIdPredicate != null) {
                predicates.add(postIdPredicate);
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
