package com.microservice.order_service.repository;

import com.microservice.order_service.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findByUserId(Long userId, Pageable pageable); // Tìm đơn hàng theo ID người dùng
    Page<Order> findByStatusOrder(String status, Pageable pageable); // Tìm đơn hàng theo trạng thái
}
