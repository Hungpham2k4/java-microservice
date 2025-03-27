package com.microservice.user_service.service;
import com.microservice.user_service.dto.UserDto;
import com.microservice.user_service.entity.User;
import com.microservice.user_service.form.UserUpdateForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserService {
//    UserDto create(UserCreateForm userCreateForm);
UserDetails loadUserByUsername(String username);
    Page<UserDto> getAllUsers(Pageable pageable);
    Optional<User> findByEmail(String email);
    void resetPassword(String email, String newPassword);
    UserDto getUserById(Long id);
    UserDto updateUser(UserUpdateForm userUpdateForm, Long id);
    void deleteUser(Long id);
}
