package com.microservice.post_service.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class PostUpdateForm {
    @NotBlank(message = "Tiêu đề không được bỏ trống")
    @Length(max = 50, message = "Tiêu đề có tối đa 50 kí tự")
    private String title;

    @NotBlank(message = "Mô tả không được bỏ trống")
    @Length(max = 100, message = "Tiêu đề có tối đa 50 kí tự")
    private String description;

    @NotBlank(message = "Nội dung không được bỏ trống")
    @Length(max = 150, message = "Tiêu đề có tối đa 50 kí tự")
    private String content;
}
