package com.microservice.post_service.service;

import com.microservice.post_service.dto.PostDto;
import com.microservice.post_service.form.PostCreateForm;
import com.microservice.post_service.form.PostFilterForm;
import com.microservice.post_service.form.PostUpdateForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
    Page<PostDto> findAll(PostFilterForm postFilterForm, Pageable pageable);

    PostDto findById(Long id);

    PostDto create(PostCreateForm form);

    PostDto update(PostUpdateForm form, Long id);

    void deleteById(Long id);
}
