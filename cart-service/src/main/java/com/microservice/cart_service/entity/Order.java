package com.microservice.cart_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // Mỗi đơn hàng thuộc về một người dùng
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Column(name = "status", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private StatusOrder statusOrder;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items; // Danh sách sản phẩm trong đơn hàng

    @Column(name = "total_price", nullable = false)
    private Double totalPrice; // Tổng giá trị đơn hàng

    @Column(name = "recipient_name", nullable = false)
    private String recipientName; // Tên người nhận

    @Column(name = "address", nullable = false)
    private String address; // Địa chỉ giao hàng

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber; // Số điện thoại người nhận

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
