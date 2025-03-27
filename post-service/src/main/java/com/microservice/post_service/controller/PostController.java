package com.microservice.post_service.controller;

import com.microservice.post_service.dto.PostDto;
import com.microservice.post_service.form.PostCreateForm;
import com.microservice.post_service.form.PostFilterForm;
import com.microservice.post_service.form.PostUpdateForm;
import com.microservice.post_service.service.PostService;
import com.microservice.post_service.validation.PostIdExists;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@AllArgsConstructor
public class PostController {
    private PostService postService;

    @GetMapping("api/v1/posts")
    public Page<PostDto> findAll(PostFilterForm form, Pageable pageable) {
        return postService.findAll(form, pageable);
    }

    @GetMapping("api/v1/posts/{id}")
    public PostDto findById(@PathVariable("id") @PostIdExists Long id){
        return postService.findById(id);
    }

    @PostMapping("api/v1/posts")
    public PostDto create(@RequestBody @Valid PostCreateForm form){
        return postService.create(form);
    }

    @PutMapping("api/v1/posts/{id}")
    public PostDto update(
           @RequestBody PostUpdateForm form,
           @PathVariable("id") @PostIdExists Long id
    ){
        return postService.update(form, id);
    }

    @DeleteMapping("api/v1/posts/{id}")
    public void deleteById(@PathVariable("id") @PostIdExists Long id){
        postService.deleteById(id);
    }
}
