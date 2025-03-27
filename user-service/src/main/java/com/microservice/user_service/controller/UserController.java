package com.microservice.user_service.controller;

import com.microservice.user_service.dto.UserDto;
import com.microservice.user_service.entity.User;
import com.microservice.user_service.form.UserUpdateForm;
import com.microservice.user_service.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor
@Validated
public class UserController {
    private UserService userService;

    @GetMapping("api/v1/user")
    public Page<UserDto> getUsers(Pageable pageable){
        return userService.getAllUsers(pageable);
    }

    @GetMapping("api/v1/user/checkmail/{email}")
    public Optional<User> findByEmail(@PathVariable String email){
        return userService.findByEmail(email);
    }

    @GetMapping("api/v1/user/{userId}")
    public UserDto getUserById(@PathVariable Long userId){
        return userService.getUserById(userId);
    }

    @PutMapping("api/v1/user/{userId}")
    public UserDto updateUser(@RequestBody @Valid UserUpdateForm userUpdateForm, @PathVariable Long userId){
        userService.updateUser(userUpdateForm, userId);
        return userService.getUserById(userId);
    }

    @DeleteMapping("api/v1/user/{userId}")
    public void deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
    }
}
