package com.microservice.api_gateway.config;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;


@Configuration
@AllArgsConstructor
public class GatewayConfig {
    @Autowired
    AuthenticationFilter filter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth-service", r -> r.path("/api/v1/auth/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://auth-service"))
                .route("cart-service", r -> r.path("/api/v1/carts/**", "/api/v1/cart-items/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://cart-service"))
                .route("category-service", r -> r.path("/api/v1/categories/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://category-service"))
                .route("comment-service", r -> r.path("/api/v1/comments/**", "/api/v1/posts/{postId}/comments")
                        .filters(f -> f.filter(filter))
                        .uri("lb://comment-service"))
                .route("order-service", r -> r.path("/api/v1/orders/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://order-service"))
                .route("post-service", r -> r.path("/api/v1/posts/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://post-service"))
                .route("product-service", r -> r.path("/api/v1/products/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://product-service"))
                .route("user-service", r -> r.path("/api/v1/user/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://user-service"))
                .build();
    }

//

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOrigins(List.of("http://localhost:3001","http://localhost:3000"));
        corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        corsConfig.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        corsConfig.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return new CorsWebFilter(source);
    }
}
