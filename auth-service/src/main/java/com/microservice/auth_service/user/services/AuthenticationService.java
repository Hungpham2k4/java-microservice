package com.microservice.auth_service.user.services;

import com.microservice.auth_service.dto.request.LoginRequestDTO;
import com.microservice.auth_service.dto.request.RegisterRequestDTO;
import com.microservice.auth_service.dto.response.AuthenticationResponseDTO;
import com.microservice.auth_service.dto.response.RegisterResponseDTO;
import com.microservice.auth_service.exception.CustomException;
import com.microservice.auth_service.model.Role;
import com.microservice.auth_service.model.User;
import com.microservice.auth_service.user.repo.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public RegisterResponseDTO register(RegisterRequestDTO registerRequestDTO) {
        User user = User.builder()
                .username(registerRequestDTO.getUsername())
                .name(registerRequestDTO.getName())
                .email(registerRequestDTO.getEmail())
                .password(passwordEncoder.encode(registerRequestDTO.getPassword()))
                .role(Role.toEnum(registerRequestDTO.getRole()))
                .build();

        Optional<User> userFoundByEmail = userRepository.findByEmail(user.getEmail());
        Optional<User> userFoundByUsername = userRepository.findByUsername(user.getUsername());
        if (userFoundByEmail.isPresent() || userFoundByUsername.isPresent()) {
            throw new ConstraintViolationException("User already exists!", null);
        }

        User userSaved = userRepository.save(user);

        return RegisterResponseDTO.builder()
                .status(HttpStatus.OK.value())
                .message("User created")
                .build();
    }

    public AuthenticationResponseDTO login(LoginRequestDTO loginRequestDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(), loginRequestDTO.getPassword())
        );
        User user = userRepository.findByUsername(loginRequestDTO.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        UserDetails userDetails = userService.loadUserByUsername(user.getUsername());
        String accessToken = jwtService.generateAccessToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        return AuthenticationResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(user.getId())
                .message("Login successfully")
                .status(HttpStatus.OK.value())
                .build();
    }

    public AuthenticationResponseDTO refreshToken(HttpServletRequest req, HttpServletResponse res) throws CustomException {
        String authHeader = req.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            return AuthenticationResponseDTO.builder()
                    .status(HttpStatus.UNAUTHORIZED.value())
                    .message("Unauthorized!")
                    .build();
        }

        String refreshToken = authHeader.substring(7);
        String userName = jwtService.extractUsername(refreshToken);
        log.info("Extracted username: {}", userName);
        if (userName == null || userName.isEmpty()) {
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Username is empty");
        }

        Optional<User> userFoundByUsername = userRepository.findByUsername(userName);
        if (userFoundByUsername.isEmpty()) {
            throw new UsernameNotFoundException(userName);
        }

        User user = userFoundByUsername.get();
        if (user.getRefreshToken() == null || user.getAccessToken().isEmpty()) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "Token of the user revoked");
        }

        String accessToken = jwtService.generateAccessToken(user);
        String newRefreshToken = jwtService.generateRefreshToken(user);
        user.setAccessToken(accessToken);
        user.setRefreshToken(newRefreshToken);
        userRepository.save(user);

        return AuthenticationResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(user.getId())
                .message("Refresh token successfully")
                .status(HttpStatus.OK.value())
                .build();
    }
}