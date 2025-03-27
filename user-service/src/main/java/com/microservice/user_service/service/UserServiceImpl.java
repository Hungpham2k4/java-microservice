package com.microservice.user_service.service;

import com.microservice.user_service.dto.UserDto;
import com.microservice.user_service.entity.Role;
import com.microservice.user_service.entity.User;
import com.microservice.user_service.form.UserUpdateForm;
import com.microservice.user_service.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private ModelMapper modelMapper;

//    @Override
//    public UserDto create(UserCreateForm userCreateForm) {
//        var user = modelMapper.map(userCreateForm, User.class);
//        var encodedPassword = passwordEncoder.encode(user.getPassword());
//        user.setPassword(encodedPassword);
//        var savedUser = userRepository.save(user);
//        return modelMapper.map(savedUser, UserDto.class);
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmail(username, username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        String role = user.getRole().name();
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                AuthorityUtils.createAuthorityList(role)
        );
    }

    @Override
    public Page<UserDto> getAllUsers(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return users.map(user -> modelMapper.map(user, UserDto.class));
    }

    @Override
    public UserDto getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return modelMapper.map(user.get(), UserDto.class);
        }
        return null;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void resetPassword(String email, String newPassword) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public UserDto updateUser(UserUpdateForm userUpdateForm, Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setName(userUpdateForm.getName());
            user.setEmail(userUpdateForm.getEmail());
            if (!user.getPassword().equals(userUpdateForm.getPassword())) {
                user.setPassword(passwordEncoder.encode(userUpdateForm.getPassword()));
            } else {
                user.setPassword(user.getPassword());
            }
            user.setRole(Role.valueOf(userUpdateForm.getRole()));
            userRepository.save(user);
        }
        return modelMapper.map(userUpdateForm, UserDto.class);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
