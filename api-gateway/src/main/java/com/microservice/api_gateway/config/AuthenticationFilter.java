package com.microservice.api_gateway.config;

import com.microservice.api_gateway.exception.CustomException;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthenticationFilter implements GatewayFilter {
    private final JwtUtil jwtUtil;

    // Các route không yêu cầu Authorization
    private static final List<String> EXCLUDED_PATHS = List
            .of("/api/v1/auth/login",
                    "/api/v1/auth/register",
                    "/api/v1/auth/refresh-token",
                    "/api/v1/auth/send-otp",
                    "/api/v1/auth/verify-otp",
                    "/api/v1/auth/reset-password",
                    "api/v1/user/checkmail/**",
                    "api/v1/user/checkmail");

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        // Bỏ qua kiểm tra token với các đường dẫn trong danh sách
        if (EXCLUDED_PATHS.contains(path)) {
            return chain.filter(exchange);
        }

        if (isAuthMissing(request)) {
            return onError(exchange, "Authorization header is missing in request", HttpStatus.UNAUTHORIZED);
        }

        final String authHeader = getAuthHeader(request);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return onError(exchange, "Authorization header method is incorrect", HttpStatus.UNAUTHORIZED);
        }

        String token = authHeader.substring(7); // Bỏ "Bearer " để lấy token thực
        if (!jwtUtil.verifyToken(token)) {
            return onError(exchange, "Invalid or expired token", HttpStatus.UNAUTHORIZED);
        }

        populateRequestWithHeader(exchange, token);
        return chain.filter(exchange);
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    private String getAuthHeader(ServerHttpRequest request) {
        return request.getHeaders().getFirst("Authorization");
    }

    private boolean isAuthMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey("Authorization");
    }

    private void populateRequestWithHeader(ServerWebExchange exchange, String token) {
        Claims claims = jwtUtil.getAllClaimsFromToken(token);
        exchange.getRequest().mutate()
                .header("id", String.valueOf(claims.get("id")))
                .header("role", String.valueOf(claims.get("role")))
                .build();
    }
}
