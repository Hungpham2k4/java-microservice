package com.microservice.comment_service.specification;

import com.microservice.comment_service.entity.Comment;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;

public class CommentSpecificationUtils {

    public static Predicate buildSearchPredicate(Root<Comment> root, String search, CriteriaBuilder builder) {
        var predicates = new ArrayList<Predicate>();

        if (search != null) {
            var hasNameLike = builder.like(
                    root.get("name"),
                    "%" + search + "%"
            );
            var hasEmailLike = builder.like(
                    root.get("email"),
                    "%" + search + "%"
            );

            predicates.add(builder.or(hasNameLike, hasEmailLike));
        }
        return builder.and(predicates.toArray(new Predicate[0]));
    }

    public static Predicate buildPostIdPredicate(Root<Comment> root, Long postId, CriteriaBuilder builder) {
        if (postId != null) {
            return builder.equal(root.get("post").get("id"), postId);
        }
        return null;
    }
}
