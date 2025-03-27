package com.microservice.auth_service.user.services;


import com.microservice.auth_service.dto.UserDto;
import com.microservice.auth_service.model.User;
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
}
