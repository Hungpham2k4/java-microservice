package com.microservice.cart_service.repository;

import com.microservice.cart_service.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUserId(Long userId); // Tìm giỏ hàng theo ID người dùng
}
