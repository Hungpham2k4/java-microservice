package com.microservice.post_service.validation;

import com.microservice.post_service.repository.PostRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PostIdExistsValidator implements ConstraintValidator<PostIdExists, Long> {
    private PostRepository postRepository;
    @Override
    public boolean isValid(Long id, ConstraintValidatorContext constraintValidatorContext) {
        return postRepository.existsById(id);
    }
}
