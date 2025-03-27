package com.microservice.comment_service.mapper;

import com.microservice.comment_service.dto.CommentDto;
import com.microservice.comment_service.entity.Comment;
import com.microservice.comment_service.form.CommentCreateForm;
import com.microservice.comment_service.form.CommentUpdateForm;

public class CommentMapper {
    public static CommentDto map(Comment comment) {
        var dto = new CommentDto();
        dto.setId(comment.getId());
        dto.setName(comment.getName());
        dto.setEmail(comment.getEmail());
        dto.setBody(comment.getBody());
        dto.setCreatedAt(comment.getCreatedAt());
        dto.setUpdatedAt(comment.getUpdatedAt());
        return dto;
    }

    public static Comment map(CommentCreateForm form) {
        var comment = new Comment();
        comment.setName(form.getName());
        comment.setEmail(form.getEmail());
        comment.setBody(form.getBody());
        return comment;
    }

    public static void map(CommentUpdateForm form, Comment comment) {
        comment.setName(form.getName());
        comment.setEmail(form.getEmail());
        comment.setBody(form.getBody());
    }
}
