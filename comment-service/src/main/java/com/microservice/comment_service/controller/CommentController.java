package com.microservice.comment_service.controller;

import com.microservice.comment_service.dto.CommentDto;
import com.microservice.comment_service.form.CommentCreateForm;
import com.microservice.comment_service.form.CommentFilterForm;
import com.microservice.comment_service.form.CommentUpdateForm;
import com.microservice.comment_service.service.CommentService;
import com.microservice.comment_service.validation.CommentIdExists;
import com.microservice.comment_service.validation.PostIdExists;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@AllArgsConstructor
public class CommentController {
    private CommentService commentService;

    @GetMapping("api/v1/comments")
    public Page<CommentDto> findAll(CommentFilterForm commentFilterForm, Pageable pageable){
        return commentService.findAll(commentFilterForm ,pageable);
    }

    @GetMapping("api/v1/posts/{postId}/comments")
    public Page<CommentDto> findByPostId(@PathVariable("postId") @PostIdExists Long postId, Pageable pageable) {
        return commentService.findByPostId(postId, pageable);
    }

    @GetMapping("api/v1/comments/{id}")
    public CommentDto findById(@PathVariable("id") @CommentIdExists Long id){
        return commentService.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("api/v1/posts/{postId}/comments")
    public CommentDto create(@PathVariable @PostIdExists Long postId, @RequestBody @Valid CommentCreateForm form){
        return commentService.create(form, postId);
    }

    @PutMapping("api/v1/comments/{id}")
    public CommentDto update(
            @RequestBody @Valid CommentUpdateForm form,
            @PathVariable("id") @CommentIdExists Long id
    ){
        return commentService.update(form, id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("api/v1/comments/{id}")
    public void deleteById(@PathVariable("id") @CommentIdExists Long id){
        commentService.deleteById(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("api/v1/posts/{postId}/comments")
    public void deleteAllByPostId(@PathVariable("postId") @PostIdExists Long postId) {
        commentService.deleteAllByPostId(postId);
    }

}
