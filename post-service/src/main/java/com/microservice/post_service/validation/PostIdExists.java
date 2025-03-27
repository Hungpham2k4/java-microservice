package com.microservice.post_service.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(
        validatedBy = {PostIdExistsValidator.class}
)
@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface PostIdExists {
    String message() default "Id bài viết không tồn tại";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
