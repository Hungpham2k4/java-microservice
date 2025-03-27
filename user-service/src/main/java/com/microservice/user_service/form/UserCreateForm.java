package com.microservice.user_service.form;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class UserCreateForm {
    @NotBlank
    @Length(max = 50)
    private String name;

    @NotBlank
    @Length(max = 50)
    private String username;

    @NotBlank
    @Length(max = 50)
    @Email
    private String email;

    @NotBlank
    @Length(min = 8, max = 32)
    private String password;

    @NotBlank
    @Pattern(regexp = "ADMIN|EMPLOYEE|MANAGER")
    private String role;
}
