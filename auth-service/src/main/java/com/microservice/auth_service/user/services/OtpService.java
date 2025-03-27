package com.microservice.auth_service.user.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class OtpService {

    private final StringRedisTemplate redisTemplate;
    private final EmailService emailService;

    private static final long OTP_EXPIRATION_MINUTES = 5;

    public void sendOtp(String email) {
        String otp = generateOtp();
        redisTemplate.opsForValue().set(email, otp, OTP_EXPIRATION_MINUTES, TimeUnit.MINUTES);
        emailService.sendOtp(email, otp);
    }

    public boolean verifyOtp(String email, String otp) {
        String storedOtp = redisTemplate.opsForValue().get(email);
        return storedOtp != null && storedOtp.equals(otp);
    }

    private String generateOtp() {
        return String.format("%06d", new Random().nextInt(999999));
    }
}
