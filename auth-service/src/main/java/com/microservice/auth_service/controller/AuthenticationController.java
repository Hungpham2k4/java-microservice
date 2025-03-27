package com.microservice.auth_service.controller;

import com.microservice.auth_service.dto.request.LoginRequestDTO;
import com.microservice.auth_service.dto.request.RegisterRequestDTO;
import com.microservice.auth_service.dto.response.AuthenticationResponseDTO;
import com.microservice.auth_service.dto.response.RegisterResponseDTO;
import com.microservice.auth_service.exception.CustomException;
import com.microservice.auth_service.user.services.AuthenticationService;
import com.microservice.auth_service.user.services.OtpService;
import com.microservice.auth_service.user.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/auth")
@Validated
//@CrossOrigin(origins = "*")
public class AuthenticationController {
    public final AuthenticationService authenticationService;
    private final OtpService otpService;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequestDTO registerRequestDTO) throws Exception {
        RegisterResponseDTO registerResponseDTO = authenticationService.register(registerRequestDTO);
        return ResponseEntity.status(registerResponseDTO.getStatus()).body(registerResponseDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDTO loginRequestDTO) throws Exception {
        log.info("Password: {}", loginRequestDTO.getPassword());
        log.info("Username: {}", loginRequestDTO.getUsername());

        AuthenticationResponseDTO authenticationResponseDTO = authenticationService.login(loginRequestDTO);
        return ResponseEntity.status(authenticationResponseDTO.getStatus()).body(authenticationResponseDTO);
    }

    @PostMapping("/refresh-token")
    public AuthenticationResponseDTO refreshToken(HttpServletRequest req, HttpServletResponse res) throws CustomException {
        log.info("Request: {}", req);
        return authenticationService.refreshToken(req, res);
    }

    @PostMapping("/send-otp")
    public ResponseEntity<String> sendOtp(@RequestParam String email) {
        otpService.sendOtp(email);
        return ResponseEntity.ok("OTP sent successfully.");
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestParam String email, @RequestParam String otp) {
        boolean isValid = otpService.verifyOtp(email, otp);
        if (isValid) {
            return ResponseEntity.ok("OTP verified successfully.");
        }
        return ResponseEntity.badRequest().body("Invalid or expired OTP.");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String email, @RequestParam String newPassword) {
        userService.resetPassword(email, newPassword);
        return ResponseEntity.ok("Password reset successfully.");
    }
}